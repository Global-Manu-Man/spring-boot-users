package spring_security_shield.util;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

public class PasswordUtil {

    private static final SecureRandom RANDOM = new SecureRandom();
    private static final int SALT_LENGTH = 16; // Puedes ajustar la longitud de la sal

    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        byte[] salt = new byte[SALT_LENGTH];
        RANDOM.nextBytes(salt);
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt);
        byte[] hashedPassword = md.digest(password.getBytes());
        return Hex.encodeHexString(salt) + ":" + Hex.encodeHexString(hashedPassword);
    }

    public static boolean verifyPassword(String password, String storedPassword) throws NoSuchAlgorithmException, DecoderException {
        String[] parts = storedPassword.split(":");
        if (parts.length != 2) {
            return false;
        }
        byte[] salt = Hex.decodeHex(parts[0]);
        byte[] storedHash = Hex.decodeHex(parts[1]);

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt);
        byte[] hashedPassword = md.digest(password.getBytes());
        return Arrays.equals(hashedPassword, storedHash);
    }
}