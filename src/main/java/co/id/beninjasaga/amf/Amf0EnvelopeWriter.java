package co.id.beninjasaga.amf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*; import java.nio.charset.StandardCharsets;
public class Amf0EnvelopeWriter {
  private static final Logger log = LoggerFactory.getLogger(Amf0EnvelopeWriter.class);

  public static byte[] writeResponseAmf3Body(String targetUri, Object amf3Body) throws IOException {
    log.info("[AMF3] - writeResponseAmf3Body");
    ByteArrayOutputStream baos = new ByteArrayOutputStream(); DataOutputStream out = new DataOutputStream(baos);
    out.writeShort(3); out.writeShort(0); out.writeShort(1);
    writeUtf(out, targetUri); writeUtf(out, ""); out.writeInt(-1);
    out.writeByte(0x11); Amf3Output a3 = new Amf3Output(); a3.writeValue(amf3Body); out.write(a3.toByteArray());
    out.flush(); return baos.toByteArray();
  }
  public static byte[] writeResponseAmf0Body(String targetUri, Object amf0Body) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream(); DataOutputStream out = new DataOutputStream(baos);
    out.writeShort(3); out.writeShort(0); out.writeShort(1);
    writeUtf(out, targetUri); writeUtf(out, ""); out.writeInt(-1);
    new Amf0ValueWriter(out).writeValue(amf0Body);
    out.flush(); return baos.toByteArray();
  }
  private static void writeUtf(DataOutputStream out, String s) throws IOException {
    byte[] b = (s==null?"":s).getBytes(StandardCharsets.UTF_8); out.writeShort(b.length); out.write(b);
  }
}
