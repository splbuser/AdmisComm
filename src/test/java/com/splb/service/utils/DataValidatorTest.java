package com.splb.service.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testng.annotations.BeforeClass;

import static org.junit.jupiter.api.Assertions.*;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

class DataValidatorTest {

    private String validLogin;
    private String validPassword;
    private String validRepeatPassword;
    private String validLastName;
    private String validFirstName;

    private String invalidLogin;
    private String invalidPassword;
    private String invalidRepeatPassword;
    private String invalidLastName;
    private String invalidFirstName;


    @BeforeEach
    public void setUp() {
        validLogin = "login9";
        validPassword = "PASSword99";
        validRepeatPassword = "PASSword99";
        validLastName = "Userlastname";
        validFirstName = "Userfirstname";

        invalidLogin = "_&user";
        invalidPassword = "pass";
        invalidRepeatPassword = "pAss";
        invalidLastName = "123456789";
        invalidFirstName = " ";
    }

    @Test
    void validateUserName() {
        assertTrue(DataValidator.validateUserName(validLogin));
        assertFalse(DataValidator.validateUserName(invalidLogin));
    }

    @Test
    void validateName() {
        assertTrue(DataValidator.validateName(validLastName));
        assertTrue(DataValidator.validateName(validFirstName));
        assertFalse(DataValidator.validateName(invalidLastName));
        assertFalse(DataValidator.validateName(invalidFirstName));
    }

    @Test
    void validatePassword() {
        assertTrue(DataValidator.validatePassword(validPassword));
        assertFalse(DataValidator.validatePassword(invalidPassword));
    }

    @Test
    void validateRePassword() {
        assertTrue(DataValidator.validateRePassword(validPassword, validRepeatPassword));
        assertFalse(DataValidator.validateRePassword(invalidPassword, invalidRepeatPassword));
    }
}