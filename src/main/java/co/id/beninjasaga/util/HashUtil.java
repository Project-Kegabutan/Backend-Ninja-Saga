package co.id.beninjasaga.util;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class HashUtil {
    private static final String S = "Vmn34aAciYK00Hen26nT01";
    // generateHash(sessionKey, input) -> sha1(input) lowercase, take 12 chars starting at hex-dec(second char of sessionKey)
    public static String generateHash(String sessionKey, String input) {
        int startIndex = Integer.parseInt(sessionKey.substring(1, 2), 16);
        String sha1 = sha1HexLower(input);
        return safeSub(sha1, startIndex, 12);
    }
    public static String getHash(String data, String sessionKey) {
        String raw = data + S + sessionKey;
        return generateHash(sessionKey, raw);
    }
    public static String getArrayHash(java.util.List<?> data, String sessionKey) {
        // drop last (assumed sessionKey)
        int n = Math.max(0, data.size() - 1);
        StringBuilder joined = new StringBuilder();
        for (int i = 0; i < n; i++) {
            if (i > 0) joined.append('|');
            joined.append(String.valueOf(data.get(i)));
        }
        String raw = joined.toString() + S + sessionKey;
        int startIndex = Integer.parseInt(sessionKey.substring(1, 2), 16);
        String sha1 = sha1HexLower(raw);
        return safeSub(sha1, startIndex, 12);
    }
    private static String safeSub(String s, int start, int len){
        if (start < 0) start = 0;
        if (start > s.length()) start = s.length();
        int end = Math.min(s.length(), start + len);
        return s.substring(start, end);
    }
    private static String sha1HexLower(String input){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] bytes = md.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e){ throw new RuntimeException(e); }
    }
}
