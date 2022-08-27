package com.splb.service.utils;


import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * When adding a new user, call generateSalt(), then getEncryptedPassword() and save the encrypted
 * password and salt. Do not store a clear text password. Don't worry about storing the salt in a
 * separate table or location from the encrypted password; as discussed above, salt is not a secret.
 * When authenticating a user, pull the previously encrypted password and salt from the database,
 * then send them and the plaintext password they entered to authenticate(). If it returns true,
 * the authentication was successful. When a user changes their password, it is safe to reuse their
 * old salt; You can just call getEncryptedPassword() with the old salt.
 */
public class PassCrypt {

    public boolean authenticate(String attemptedPassword, byte[] encryptedPassword, byte[] salt)
            throws NoSuchAlgorithmException, InvalidKeySpecException {

        /* Encrypt the clear-text password using the same salt that was used to
         * encrypt the original password
         */

        byte[] encryptedAttemptedPassword = getEncryptedPassword(attemptedPassword, salt);

        /* Authentication succeeds if encrypted password that the user entered
         * is equal to the stored hash
         */

        return Arrays.equals(encryptedPassword, encryptedAttemptedPassword);
    }

    public byte[] getEncryptedPassword(String password, byte[] salt)
            throws NoSuchAlgorithmException, InvalidKeySpecException {

        /* PBKDF2 with SHA-1 as the hashing algorithm. Note that the NIST
         * specifically names SHA-1 as an acceptable hashing algorithm for PBKDF2
         */

        String algorithm = "PBKDF2WithHmacSHA1";

        /* SHA-1 generates 160 bit hashes, so that's what makes sense here
         * */

        int derivedKeyLength = 160;

        /* Pick an iteration count that works for you. The NIST recommends at
         * least 1,000 iterations.
         */

        int iterations = 20000;

        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, derivedKeyLength);

        SecretKeyFactory f = SecretKeyFactory.getInstance(algorithm);

        return f.generateSecret(spec).getEncoded();
    }

    public byte[] generateSalt() throws NoSuchAlgorithmException {

        /* VERY important to use SecureRandom instead of just Random
         */

        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");

        /* Generate a 8 byte (64 bit) salt as recommended by RSA PKCS5
         */

        byte[] salt = new byte[8];
        random.nextBytes(salt);

        return salt;
    }

    /**
     * Simple methods for encoding/decoding String password
     */

    public static String encode(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static String encodeWithoutPadding(byte[] bytes) {
        return Base64.getEncoder()
                .withoutPadding()
                .encodeToString(bytes);
    }

    public static byte[] decode(String s) {
        return Base64.getDecoder().decode(s);
    }


}
