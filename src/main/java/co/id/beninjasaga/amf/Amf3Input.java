package co.id.beninjasaga.amf;
import java.io.*; import java.nio.charset.StandardCharsets; import java.util.*;
public class Amf3Input {
  private final DataInputStream in; private final java.util.List<String> srefs=new java.util.ArrayList<>();
  public Amf3Input(InputStream is){ this.in=new DataInputStream(is); }
  public Object readValue() throws IOException {
    int m = in.readUnsignedByte();
    return switch(m){
      case 0x00 -> new Object(); // undefined placeholder
      case 0x01 -> null;
      case 0x02 -> Boolean.FALSE;
      case 0x03 -> Boolean.TRUE;
      case 0x04 -> readU29();
      case 0x05 -> in.readDouble();
      case 0x06 -> readString();
      case 0x09 -> readArray();
      case 0x0A -> readObject();
      default -> throw new IOException("Unsupported AMF3 marker 0x"+Integer.toHexString(m));
    };
  }
  private int readU29() throws IOException {
    int v=0,b; for(int i=0;i<4;i++){ b=in.readUnsignedByte(); if(i<3){ v=(v<<7)|(b&0x7F); if(b<128) return v; } else { v=(v<<8)|b; return v; } } return v;
  }
  private String readString() throws IOException {
    int u29=readU29(); if((u29&1)==0) return srefs.get(u29>>1); int len=u29>>1; if(len==0) return "";
    byte[] buf=in.readNBytes(len); String s=new String(buf, StandardCharsets.UTF_8); srefs.add(s); return s;
  }
  private java.util.List<Object> readArray() throws IOException {
    int u29=readU29(); int dense=u29>>1; while(true){ String k=readString(); if(k.isEmpty()) break; readValue(); }
    java.util.List<Object> arr=new java.util.ArrayList<>(dense); for(int i=0;i<dense;i++) arr.add(readValue()); return arr;
  }
  private java.util.Map<String,Object> readObject() throws IOException {
    int u29=readU29(); boolean inline=(u29&1)==1; if(!inline) throw new IOException("class ref not supported");
    boolean dynamic=((u29>>2)&1)==1; int propCount=u29>>3; String className=readString();
    java.util.Map<String,Object> m=new java.util.LinkedHashMap<>(); for(int i=0;i<propCount;i++){ String p=readString(); m.put(p, readValue()); }
    if(dynamic){ while(true){ String k=readString(); if(k.isEmpty()) break; m.put(k, readValue()); } }
    return m;
  }
}
