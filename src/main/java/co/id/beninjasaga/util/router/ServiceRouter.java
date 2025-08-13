package co.id.beninjasaga.util.router;

import co.id.beninjasaga.amf.AmfBinders;
import co.id.beninjasaga.amf.AmfBinders.MethodSig;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.*;

@Slf4j
public class ServiceRouter {
  private final Map<String,Object>   reg  = new HashMap<>();
  private final Map<String,MethodSig> sigs = new HashMap<>();

  /* ========== Registration ========== */

  public ServiceRouter register(String name, Object svc){
    reg.put(name.toLowerCase(Locale.ROOT), svc);
    return this;
  }

  public ServiceRouter registerSignature(String methodFullName, MethodSig sig){
    String key = methodFullName.toLowerCase(Locale.ROOT);
    sigs.put(key, sig);
    return this;
  }

  /* ========== Introspection helpers (optional, bisa dipanggil dari Controller) ========== */

  public boolean hasService(String target){
    String[] sm = split(target);
    return reg.containsKey(sm[0].toLowerCase(Locale.ROOT));
  }

  public boolean hasMethod(String target, Object body){
    String[] sm = split(target);
    Object svc = reg.get(sm[0].toLowerCase(Locale.ROOT));
    if (svc == null) return false;

    int argc = argCount(body);
    for (Method m: svc.getClass().getMethods()){
      if (m.getName().equalsIgnoreCase(sm[1]) && m.getParameterCount()==argc) return true;
    }
    return false;
  }

  /* ========== Core invoke ========== */

  public Object invokeFlexible(String target, Object body) throws Exception {
    String[] sm = split(target);
    String service = sm[0], method = sm[1];

    Object svc = reg.get(service.toLowerCase(Locale.ROOT));
    if (svc==null) {
      log.info("[AMF] Service not found: {}", service);
      throw new NoSuchElementException("Service not found: " + service);
    }

    MethodSig sig = sigs.get((service + "." + method).toLowerCase(Locale.ROOT));
    Object[] params;

    if (sig != null) {
      params = AmfBinders.bind(body, sig);
    } else {
      List<Object> args = new ArrayList<>();
      if (body instanceof List<?> l) args.addAll(l);
      else if (body != null) args.add(body);
      params = args.toArray();
      log.info("[AMF] No signature for {}.{}, inferred argc={}", service, method, params.length);
    }

    Method chosen = null;
    outer:
    for (Method m : svc.getClass().getMethods()) {
      if (!m.getName().equalsIgnoreCase(method)) continue;
      if (m.getParameterCount() != params.length) continue;
      Class<?>[] pts = m.getParameterTypes();
      for (int i=0;i<pts.length;i++){
        if (params[i]==null) continue; // null is assignable to any reference type
        if (!wrap(pts[i]).isAssignableFrom(params[i].getClass())) continue outer;
      }
      chosen = m; break;
    }

    if (chosen==null) {
      log.info("[AMF] Method not found / arity or type mismatch: {}.{}(argc={})",
              service, method, params.length);
      throw new NoSuchMethodException(method);
    }
    return chosen.invoke(svc, params);
  }

  /* ========== utils ========== */

  private static String[] split(String target){
    if (target == null || target.isEmpty())
      throw new IllegalArgumentException("Invalid target: " + target);

    String service, method;
    if (target.contains(".")) {
      String[] p = target.split("\\.",2);
      service=p[0]; method=p[1];
    } else {
      String[] p = target.split("/");
      if (p.length<2) throw new IllegalArgumentException("Invalid target: " + target);
      service=p[p.length-2]; method=p[p.length-1];
    }
    return new String[]{ service, method };
  }

  private static int argCount(Object body){
    if (body == null) return 0;
    if (body instanceof List<?> l) return l.size();
    if (body.getClass().isArray()) return java.lang.reflect.Array.getLength(body);
    return 1;
  }

  private static Class<?> wrap(Class<?> c){
    if (!c.isPrimitive()) return c;
    if (c==int.class) return Integer.class;
    if (c==long.class) return Long.class;
    if (c==double.class) return Double.class;
    if (c==boolean.class) return Boolean.class;
    return c;
  }
}
