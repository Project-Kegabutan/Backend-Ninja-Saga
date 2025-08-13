package co.id.beninjasaga.amf;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class NSRemotingInput {
    private final DataInputStream in;
    public NSRemotingInput(byte[] data){ this.in = new DataInputStream(new ByteArrayInputStream(data)); }

    public String targetUri;
    public String responseUri;
    public Object body;

    public void read() throws IOException {
        int version = Short.toUnsignedInt(in.readShort()); // 3 expected
        int msgCount = in.readInt(); // 1
        if (msgCount < 1) throw new IOException("No message");
        targetUri = readUtf();
        responseUri = readUtf();
        in.readInt(); // -1
        int marker = in.readUnsignedByte();
        if (marker != 0x11) throw new IOException("Expected AMF3 marker 0x11");
        body = new Amf3Input(in).readValue();
    }

    private String readUtf() throws IOException {
        int len = Short.toUnsignedInt(in.readShort());
        byte[] buf = in.readNBytes(len);
        return new String(buf, StandardCharsets.UTF_8);
    }
}
