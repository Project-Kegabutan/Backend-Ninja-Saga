package co.id.beninjasaga.amf;

import java.lang.reflect.Method;
import java.util.*;

/** Mengikat raw AMF args (List/Map) ke parameter method (DTO/primitive). */
public final class AmfBinders {
    private AmfBinders(){}

    /** Deskripsi method target: tipe param dan, utk DTO-from-List, urutan field-nya. */
    public static final class MethodSig {
        public final Class<?>[] paramTypes;
        public final String[][] dtoFieldOrders;
        public MethodSig(Class<?>[] paramTypes, String[][] dtoFieldOrders) {
            this.paramTypes = paramTypes;
            this.dtoFieldOrders = dtoFieldOrders;
        }
    }

    /** Bind raw args → array parameter sesuai signature. */
    public static Object[] bind(Object rawArgs, MethodSig sig) throws Exception {
        List<?> list = toList(rawArgs);
        Object[] out = new Object[sig.paramTypes.length];

        for (int i = 0; i < sig.paramTypes.length; i++) {
            Class<?> pt = sig.paramTypes[i];

            // === FIX UTAMA ===
            // Jika: hanya 1 parameter DTO, rawArgs List, dan ada urutan field → isi DTO dari seluruh list.
            if (!isPrimitiveOrString(pt)
                    && sig.paramTypes.length == 1
                    && rawArgs instanceof List<?>
                    && sig.dtoFieldOrders != null
                    && sig.dtoFieldOrders.length >= 1
                    && sig.dtoFieldOrders[0] != null) {
                String[] order = sig.dtoFieldOrders[0];
                out[i] = fillDtoFromList(pt, rawArgs, order);
                continue;
            }
            // === END FIX ===

            // Default: ambil sumber param ke-i dari index i
            Object src = list.size() > i ? list.get(i) : null;

            if (isPrimitiveOrString(pt)) {
                out[i] = coerceScalar(src, pt);
            } else if (src instanceof Map<?,?> map) {
                out[i] = fillDtoFromMap(pt, map);
            } else {
                String[] order = (sig.dtoFieldOrders != null && i < sig.dtoFieldOrders.length) ? sig.dtoFieldOrders[i] : null;
                out[i] = fillDtoFromList(pt, src, order);
            }
        }
        return out;
    }

    // ---- helpers ----
    private static boolean isPrimitiveOrString(Class<?> c) {
        return c.isPrimitive()
                || c == String.class
                || c == Integer.class || c == Long.class || c == Double.class || c == Boolean.class;
    }

    private static Object coerceScalar(Object v, Class<?> to) {
        if (v == null) return defaultValue(to);
        if (to == String.class)  return String.valueOf(v);
        if (to == int.class || to == Integer.class)     return Integer.valueOf(String.valueOf(v));
        if (to == long.class || to == Long.class)       return Long.valueOf(String.valueOf(v));
        if (to == double.class || to == Double.class)   return Double.valueOf(String.valueOf(v));
        if (to == boolean.class || to == Boolean.class) return (v instanceof Boolean b) ? b : Boolean.valueOf(String.valueOf(v));
        return v;
    }

    /** DEFAULT VALUE PATCH: String → "" (hindari null) */
    private static Object defaultValue(Class<?> to) {
        if (to == String.class)  return "";
        if (to == boolean.class) return false;
        if (to == int.class)     return 0;
        if (to == long.class)    return 0L;
        if (to == double.class)  return 0d;
        return null;
    }

    private static Object fillDtoFromMap(Class<?> dto, Map<?,?> map) throws Exception {
        Object bean = dto.getDeclaredConstructor().newInstance();
        for (var e: map.entrySet()) set(bean, String.valueOf(e.getKey()), e.getValue());
        return bean;
    }

    private static Object fillDtoFromList(Class<?> dto, Object src, String[] order) throws Exception {
        List<?> l = toList(src);
        Object bean = dto.getDeclaredConstructor().newInstance();
        if (order != null) {
            for (int i = 0; i < order.length && i < l.size(); i++) {
                set(bean, order[i], l.get(i));
            }
        } else {
            for (int i = 0; i < l.size(); i++) set(bean, "value"+i, l.get(i));
        }
        return bean;
    }

    private static List<?> toList(Object raw) {
        if (raw instanceof List<?> l) return l;
        if (raw instanceof Object[] arr) return Arrays.asList(arr);
        if (raw == null) return List.of();
        return List.of(raw);
    }

    private static void set(Object bean, String name, Object value) throws Exception {
        String setter = "set" + Character.toUpperCase(name.charAt(0)) + name.substring(1);
        Method found = null;
        for (Method m : bean.getClass().getMethods()) {
            if (m.getName().equals(setter) && m.getParameterCount()==1) { found = m; break; }
        }
        if (found == null) return; // unknown field → diabaikan
        Class<?> pt = found.getParameterTypes()[0];
        Object coerced = isPrimitiveOrString(pt) ? coerceScalar(value, pt) : value;
        found.invoke(bean, coerced);
    }
}
