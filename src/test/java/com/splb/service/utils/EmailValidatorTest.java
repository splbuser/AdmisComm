package com.splb.service.utils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class EmailValidatorTest {

    @ParameterizedTest(name = "{index} \"{0}\" is not a valid email")
    @ValueSource(strings = {
            "william@@gmail.com",
            "william@gmail..com",
            "william@epam.com.",
            "william.shakespeare@epam.",
            "william..shakespeareepam.com",
            ".williamshakespeareepam.com",
            "william-shakespeare",
            "shakespeare123@",
            "@epam.com",
            "",
            "@epam.com@epam.com",
            "william.shakespeare@epam@com",
            "william.shakespeare@gmail",
    })

    void validateTest(String input) {
        assertFalse(DataValidator.validateEmail(input));
    }

}