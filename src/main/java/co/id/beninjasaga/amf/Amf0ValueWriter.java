package co.id.beninjasaga.amf;
import java.io.*; import java.nio.charset.StandardCharsets; import java.util.*;
public class Amf0ValueWriter {
  private final DataOutputStream out;
  public Amf0ValueWriter(OutputStream os){ this.out = new DataOutputStream(os); }
  public void writeValue(Object v) throws IOException {
    if (v == null) { out.writeByte(0x05); return; }
    if (v instanceof Boolean b){ out.writeByte(0x01); out.writeBoolean(b); return; }
    if (v instanceof Number n){ out.writeByte(0x00); out.writeDouble(n.doubleValue()); return; }
    if (v instanceof String s){ writeString(s); return; }
    if (v instanceof List<?> l){ writeArray(l); return; }
    if (v instanceof Map<?,?> m){ writeObject(m); return; }
    writeString(String.valueOf(v));
  }
  private void writeString(String s) throws IOException {
    byte[] b = s.getBytes(StandardCharsets.UTF_8);
    if (b.length <= 65535){ out.writeByte(0x02); out.writeShort(b.length); out.write(b); }
    else { out.writeByte(0x0C); out.writeInt(b.length); out.write(b); }
  }
  private void writeArray(List<?> l) throws IOException {
    out.writeByte(0x0A); out.writeInt(l.size()); for(Object o: l) writeValue(o);
  }
  private void writeObject(Map<?,?> m) throws IOException {
    out.writeByte(0x03);
    for (var e: m.entrySet()){
      byte[] k = String.valueOf(e.getKey()).getBytes(StandardCharsets.UTF_8);
      out.writeShort(k.length); out.write(k); writeValue(e.getValue());
    }
    out.writeShort(0); out.writeByte(0x09);
  }
}
