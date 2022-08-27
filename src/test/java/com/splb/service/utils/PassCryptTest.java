package com.splb.service.utils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class PassCryptTest {

    static Stream<String> testCases() {
        return Stream.of(
                ("password"),
                ("123456789"),
                ("sfsdf!*@#&$#kfgdfk"),
                ("ReGuL_aR#    password")
        );
    }

    @ParameterizedTest
    @MethodSource("testCases")
    void testDecode(String in) {
        String encoded = PassCrypt.encodeWithoutPadding(in.getBytes());
        String decoded = new String(PassCrypt.decode(encoded));
        assertEquals(decoded, in);
    }
}