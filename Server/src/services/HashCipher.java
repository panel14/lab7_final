package services;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * class for encoding passwords with MD@ algorithm
 */
public class HashCipher {
    private static final String ALGO = "MD2";

    public static String encodeMD2(String data) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(ALGO);
            byte[] buffer = messageDigest.digest(data.getBytes(StandardCharsets.UTF_8));
            BigInteger value = new BigInteger(1, buffer);
            StringBuilder hash = new StringBuilder(value.toString(16));

            while (hash.length() < 32){
                hash.insert(0, "0");
            }
            return hash.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Ошибка хеширования: " + e.getMessage());
            return null;
        }
    }
}
