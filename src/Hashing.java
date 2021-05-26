package src;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hashing {
   private static final MessageDigest SHA_512;

   static {
      MessageDigest hashingAlgorithm = null;
      try {
         hashingAlgorithm = MessageDigest.getInstance("SHA-512");
      } catch (NoSuchAlgorithmException err) {
         System.err.println("Hashing Algorithm not found : " + err.getMessage());
         err.printStackTrace();
         System.exit(0);
      }
      SHA_512 = hashingAlgorithm;
   }

   public static String bytesToHex(byte[] hash) {
      StringBuilder hexString = new StringBuilder();
      for (byte hashByte : hash)
         hexString.append(Integer.toHexString((hashByte & 0xff) + 0x100).substring(1));
      return hexString.toString();
   }

   public static byte[] encodeToHash(String message) {
      return SHA_512.digest(message.getBytes(StandardCharsets.UTF_8));
   }

   public static String apply(String message) {
      return bytesToHex(encodeToHash(message));
   }
   public static String apply(char[] message) {
      return apply(new String(message));
   }
}
