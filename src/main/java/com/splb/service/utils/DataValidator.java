package com.splb.service.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Objects.nonNull;

/**
 * utility class for verification input data from registration form
 */
public class DataValidator {

    private static final String NAME_REGEX = "[A-Z][a-z]{1,20}";
    private static final Pattern NAME_PATTERN = Pattern.compile(NAME_REGEX);

    // alphanumeric and underscore are allowed
    private static final String USERNAME_REGEX = "^[a-zA-Z0-9_]+$";

    private static final Pattern USERNAME_PATTERN =
            Pattern.compile(USERNAME_REGEX);

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*" +
            "@" + "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);

    // 4-20 characters password with digits, lowercase or uppercase letter
    private static final String PASSWORD_REGEX =
            "(?=[a-zA-Z0-9]*[A-Z])[a-zA-Z0-9]{4,20}";

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile(PASSWORD_REGEX);

    public static boolean validateUserName(String value) {
        if (nonNull(value)) {
            Matcher matcher = USERNAME_PATTERN.matcher(value);
            return matcher.find();
        } else {
            return false;
        }
    }

    public static boolean validateEmail(String value) {
        if (nonNull(value)) {
            Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(value);
            return matcher.find();
        } else {
            return false;
        }
    }

    public static boolean validateName(String value) {
        if (nonNull(value)) {
            Matcher matcher = NAME_PATTERN.matcher(value);
            return matcher.find();
        } else {
            return false;
        }
    }

    public static boolean validatePassword(String value) {
        if (nonNull(value)) {
            Matcher matcher = PASSWORD_PATTERN.matcher(value);
            return matcher.find();
        } else {
            return false;
        }
    }

    public static boolean validateRePassword(String value, String reValue) {
        if (nonNull(value) && nonNull(reValue)) {
            return reValue.equals(value);
        } else {
            return false;
        }
    }

    private DataValidator() {
    }
}