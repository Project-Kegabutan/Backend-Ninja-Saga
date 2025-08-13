package co.id.beninjasaga.amf;
import java.io.*; import java.nio.charset.StandardCharsets; import java.util.*;
public class Amf0Input {
  private final DataInputStream in;
  public Amf0Input(byte[] data){ this.in = new DataInputStream(new ByteArrayInputStream(data)); }
  public static final class Packet { public int version; public final List<Message> messages=new ArrayList<>(); }
  public static final class Message { public String targetUri; public String responseUri; public Object body; }
  public Packet readPacket() throws IOException {
    Packet p = new Packet();
    p.version = Short.toUnsignedInt(in.readShort()); int headerCount = Short.toUnsignedInt(in.readShort());
    for(int i=0;i<headerCount;i++){ readUtf(); in.readBoolean(); int len=in.readInt(); in.skipNBytes(len); }
    int messageCount = Short.toUnsignedInt(in.readShort());
    for(int i=0;i<messageCount;i++){ Message m=new Message(); m.targetUri=readUtf(); m.responseUri=readUtf(); in.readInt(); m.body=readValue(); p.messages.add(m); }
    return p;
  }
  private Object readValue() throws IOException {
    int m = in.readUnsignedByte();
    return switch(m){
      case 0x00 -> in.readDouble();
      case 0x01 -> in.readBoolean();
      case 0x02 -> readUtf();
      case 0x03 -> readObject();
      case 0x05 -> null;
      case 0x08 -> readEcmaArray();
      case 0x0A -> readStrictArray();
      case 0x0B -> { double ms=in.readDouble(); in.readShort(); yield new java.util.Date((long)ms); }
      case 0x0C -> { int len=in.readInt(); byte[] b=in.readNBytes(len); yield new String(b, java.nio.charset.StandardCharsets.UTF_8); }
      case 0x11 -> new Amf3Input(in).readValue();
      default -> throw new IOException("Unsupported AMF0 marker: 0x"+Integer.toHexString(m));
    };
  }
  private Map<String,Object> readObject() throws IOException {
    Map<String,Object> map = new LinkedHashMap<>();
    while(true){
      String key = readUtf();
      int m = in.readUnsignedByte();
      if (m == 0x09) break;
      map.put(key, readValueByMarker(m));
    }
    return map;
  }
  private Object readValueByMarker(int m) throws IOException {
    return switch(m){
      case 0x00 -> in.readDouble();
      case 0x01 -> in.readBoolean();
      case 0x02 -> readUtf();
      case 0x03 -> readObject();
      case 0x05 -> null;
      case 0x08 -> readEcmaArray();
      case 0x0A -> readStrictArray();
      case 0x0B -> { double ms=in.readDouble(); in.readShort(); yield new java.util.Date((long)ms); }
      case 0x0C -> { int len=in.readInt(); byte[] b=in.readNBytes(len); yield new String(b, java.nio.charset.StandardCharsets.UTF_8); }
      case 0x11 -> new Amf3Input(in).readValue();
      default -> throw new IOException("Unsupported marker: 0x"+Integer.toHexString(m));
    };
  }
  private Map<String,Object> readEcmaArray() throws IOException {
    in.readInt(); Map<String,Object> map=new LinkedHashMap<>();
    while(true){ String k=readUtf(); int m=in.readUnsignedByte(); if(m==0x09) break; map.put(k, readValueByMarker(m)); }
    return map;
  }
  private java.util.List<Object> readStrictArray() throws IOException {
    int n=in.readInt(); java.util.List<Object> l=new java.util.ArrayList<>(n);
    for(int i=0;i<n;i++) l.add(readValue()); return l;
  }
  private String readUtf() throws IOException { int len=Short.toUnsignedInt(in.readShort()); byte[] b=in.readNBytes(len); return new String(b, StandardCharsets.UTF_8); }
}
