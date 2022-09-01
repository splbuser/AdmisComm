package com.splb.service.utils;

import java.util.Base64;


/**
 * Simple methods for encoding/decoding String password
 */
public class PassCrypt {

    private PassCrypt() {
    }

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