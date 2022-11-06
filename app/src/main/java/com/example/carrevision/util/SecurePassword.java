package com.example.carrevision.util;

import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Arrays;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * Secure password class used to manage the technician's passwords
 */
public class SecurePassword {
    private static final int ITERATION_COUNT = 65536;
    private static final int KEY_LENGTH = 128;
    private static final String ALGORITHM = "PBKDF2WithHmacSHA1";

    /**
     * Generates a random salt
     * @return Random salt
     */
    public static byte[] generateSalt() {
        byte[] salt = new byte[16];
        (new SecureRandom()).nextBytes(salt);
        return salt;
    }

    /**
     * Generates the password's hash
     * @param password Plain text password
     * @param salt Salt
     * @return Password's hash
     */
    public static byte[] generateHash(String password, byte[] salt) {
        try {
            KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, ITERATION_COUNT, KEY_LENGTH);
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(ALGORITHM);
            return secretKeyFactory.generateSecret(keySpec).getEncoded();
        }
        catch (Exception ignored) {
            return new byte[0];
        }
    }

    /**
     * Verifies if the password matches
     * @param password Plain text password
     * @param salt Password's salt
     * @param hash Password's hash
     * @return True if the password is correct, false otherwise
     */
    public static boolean verifyPassword(String password, byte[] salt, byte[] hash) {
        try {
            KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, ITERATION_COUNT, KEY_LENGTH);
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(ALGORITHM);
            return Arrays.equals(secretKeyFactory.generateSecret(keySpec).getEncoded(), hash);
        }
        catch (Exception ignored) {
            return false;
        }
    }
}
