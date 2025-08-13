package co.id.beninjasaga.amf;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

/** AMF0 envelope dengan body AMF3 (marker 0x11) ala amfphp. */
public class AmfSerializerLikePHP {
    private final File logFile;

    public AmfSerializerLikePHP(File logDir) {
        if (logDir != null) logDir.mkdirs();
        this.logFile = (logDir == null) ? null : new File(logDir, "amf_debug.log");
    }

    public byte[] writeResponse(String targetUri, String responseUri, Object body) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(baos);

        // AMF packet header: version=3, headerCount=0, messageCount=1
        writeU16(out, 3);
        writeU16(out, 0);
        writeU16(out, 1);

        writeUtf(out, targetUri != null ? targetUri : "");
        writeUtf(out, responseUri != null ? responseUri : "");
        out.writeInt(-1);   // contentLength = -1

        // Body: 0x11 + AMF3 payload
        out.writeByte(0x11);
        Amf3Output a3 = new Amf3Output();
        a3.writeValue(body);
        out.write(a3.toByteArray());
        out.flush();

        // logging (opsional)
        if (logFile != null) {
            byte[] full = baos.toByteArray();
            int idx = 0;
            idx += 2 + 2 + 2; // ver + hdr + msg
            idx += 2 + (targetUri == null ? 0 : targetUri.getBytes(StandardCharsets.UTF_8).length);
            idx += 2 + (responseUri == null ? 0 : responseUri.getBytes(StandardCharsets.UTF_8).length);
            idx += 4; // contentLength
            int first = (idx < full.length) ? (full[idx] & 0xFF) : -1;

            try (FileWriter fw = new FileWriter(logFile, true)) {
                String ts = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                fw.write("[" + ts + "] === RESP START ===\n");
                fw.write("version(short)=3 (AMF3)\n");
                fw.write("headerCount(short)=0\n");
                fw.write("messageCount(short)=1\n");
                fw.write("client.readInt() should see combined(header<<16 | message) = 1\n");
                fw.write("msg[0].targetUri=" + (targetUri == null ? "" : targetUri) + "\n");
                fw.write("msg[0].responseUri=" + (responseUri == null ? "" : responseUri) + "\n");
                fw.write("msg[0].contentLength=-1 (FFFFFFFF)\n");
                fw.write("msg[0].body.firstByte=" + (first == -1 ? "(none)" : String.format("%02X", first)) + "\n");

                // dump first 32 bytes body
                StringBuilder hex = new StringBuilder();
                int max = Math.min(32, full.length - idx);
                for (int i = 0; i < max; i++) {
                    hex.append(String.format("%02X", full[idx + i]));
                    if (i < max - 1) hex.append(' ');
                }
                fw.write("msg[0].body.first32=" + hex + "\n");
                fw.write("msg[0].body.len=" + (full.length - idx) + "\n");
                fw.write("=== RESP END ===\n");
            } catch (IOException ignored) {}
        }

        return baos.toByteArray();
    }

    private static void writeU16(DataOutputStream out, int v) throws IOException {
        out.writeShort(v & 0xFFFF);
    }
    private static void writeUtf(DataOutputStream out, String s) throws IOException {
        byte[] b = s.getBytes(StandardCharsets.UTF_8);
        writeU16(out, b.length);
        out.write(b);
    }
}
