package co.id.beninjasaga.amf;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Amf3Output {

  private final ByteArrayOutputStream baos = new ByteArrayOutputStream();
  private final DataOutputStream out = new DataOutputStream(baos);

  // string reference table (AMF3 string refs)
  private final Map<String,Integer> srefs = new HashMap<>();

  public byte[] toByteArray(){ return baos.toByteArray(); }

  /** Tulis value AMF3 lengkap dengan type-marker */
  public void writeValue(Object v) throws IOException {
    if (v == null) { out.writeByte(0x01); return; }                  // null
    if (v instanceof Boolean b) { out.writeByte(b ? 0x03 : 0x02); return; } // true / false

    if (v instanceof Integer i) {                                    // int (U29)
      out.writeByte(0x04);
      writeU29(i);
      return;
    }
    if (v instanceof Number n) {                                     // double
      out.writeByte(0x05);
      out.writeDouble(n.doubleValue());
      return;
    }
    if (v instanceof String s) {                                     // string
      out.writeByte(0x06);
      writeString(s);
      return;
    }
    if (v instanceof List<?> list) {                                 // array (dense, no assoc)
      out.writeByte(0x09);
      writeArray(list, null);
      return;
    }
    if (v instanceof Map<?,?> map) {                                 // object (dynamic)
      out.writeByte(0x0A);
      writeObject((Map<?,?>) map);
      return;
    }

    // fallback: stringify
    out.writeByte(0x06);
    writeString(String.valueOf(v));
  }

  /** U29 zigzag encoding 29-bit */
  private void writeU29(int v) throws IOException {
    v &= 0x1FFFFFFF;
    if (v < 0x80) {
      out.writeByte(v);
    } else if (v < 0x4000) {
      out.writeByte(((v >> 7) & 0x7F) | 0x80);
      out.writeByte(v & 0x7F);
    } else if (v < 0x200000) {
      out.writeByte(((v >> 14) & 0x7F) | 0x80);
      out.writeByte(((v >> 7) & 0x7F) | 0x80);
      out.writeByte(v & 0x7F);
    } else {
      out.writeByte(((v >> 22) & 0x7F) | 0x80);
      out.writeByte(((v >> 15) & 0x7F) | 0x80);
      out.writeByte(((v >> 8) & 0x7F) | 0x80);
      out.writeByte(v & 0xFF);
    }
  }

  /** AMF3 string (tanpa marker). Pakai ref table untuk pengulangan. */
  private void writeString(String s) throws IOException {
    if (s == null) s = "";
    if (s.isEmpty()) {
      // empty => U29S = 0x01
      out.writeByte(0x01);
      return;
    }
    Integer ref = srefs.get(s);
    if (ref != null) {
      // reference => (ref << 1)
      writeU29(ref << 1);
      return;
    }
    byte[] b = s.getBytes(StandardCharsets.UTF_8);
    // literal => (len << 1 | 1)
    writeU29((b.length << 1) | 1);
    out.write(b);
    srefs.put(s, srefs.size());
  }

  /** Array AMF3: (dense) + optional assoc=null. */
  private void writeArray(List<?> dense, Map<String,Object> assoc) throws IOException {
    // header dense
    writeU29((dense.size() << 1) | 1);

    // assoc part (kalau ada); kita null-kan untuk payload kamu
    if (assoc != null) {
      for (var e : assoc.entrySet()) {
        writeString(e.getKey());        // key (tanpa marker)
        writeValue(e.getValue());       // value (dengan marker)
      }
    }
    // terminator assoc
    writeString("");

    // dense values
    for (Object o : dense) writeValue(o);
  }

  /** Object AMF3 dynamic (traits 0x0B, className=""). */
  private void writeObject(Map<?,?> map) throws IOException {
    // traits: dynamic, 0 sealed members => 0x0B
    writeU29(0x0B);
    writeString(""); // className = ""

    for (var e : map.entrySet()) {
      // KEY harus pakai writeString (tanpa marker)
      writeString(String.valueOf(e.getKey()));
      // VALUE wajib pakai writeValue (dengan marker)
      writeValue(e.getValue());
    }

    // end-of-dynamic-members => empty string (U29S=0x01)
    writeString("");
  }
}
