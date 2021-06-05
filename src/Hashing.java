package src;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//* Application Hashing Algorithm used for Hasing user input passwords and comparision
//* Applies Late Initialization / Lazy Loading for startup performance
public class Hashing {
   //? By default uses SHA-512 as the Hashing Algorithm
   private static final MessageDigest SHA_512;
   //? Hashing Salt for the Algorithm
   private static final byte[] HASH_SALT;

   static {
      MessageDigest hashingAlgorithm = null;
      try {
         //? Store the Instance of the SHA-512 Algorithm
         hashingAlgorithm = MessageDigest.getInstance("SHA-512");
      } catch (NoSuchAlgorithmException err) {
         System.err.println("Hashing Algorithm not found : " + err.getMessage());
         err.printStackTrace();
         //* Exit through the interface
         System.exit(0);
      }
      SHA_512 = hashingAlgorithm;
      //? Should be randomly generated or provided as an Environment Variable Constant
      HASH_SALT = "Some Random Hash String".getBytes(StandardCharsets.UTF_8);
   }

   //* Converts a Byte to Int value in Hex range 
   private static int byteToHex(byte hashByte) {
      //? AND operation with byte, then Addition operation
      return (hashByte & 0xff) + 0x100;
   }

   //* Converts Hash Bytes to Hex String
   public static String bytesToHex(byte[] hash) {
      //? Build the Hex String
      StringBuilder hexString = new StringBuilder();
      for (byte hashByte : hash)
         //? Convert the byte to hex int, then to hex string
         //? Take substring to remove additional information
         hexString.append(Integer.toHexString(byteToHex(hashByte)).substring(1));
      return hexString.toString();
   }

   //* Encodes a Message to Hash Bytes
   public static byte[] encodeToHash(String message) {
      //? Set the Hash Salt
      //* Should be set every time a message is encoded
      SHA_512.update(HASH_SALT);
      //* Encode the UTF-8 bytes of the message
      return SHA_512.digest(message.getBytes(StandardCharsets.UTF_8));
   }

   //* Apply both operations in succession
   public static String apply(String message) {
      return bytesToHex(encodeToHash(message));
   }

   public static String apply(char[] message) {
      return apply(new String(message));
   }
}
