package com.splb.service.utils;

import com.splb.controller.pages.Messages;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Objects.nonNull;

/**
 * utility class for verification input data from registration form
 */
public class DataValidator {

    private static final Logger log = LogManager.getLogger(DataValidator.class);
    private static final String NAME_REGEX = "[A-Z][a-z]{1,20}";
    private static final Pattern NAME_PATTERN = Pattern.compile(NAME_REGEX);

    // alphanumeric and underscore are allowed
    private static final String USERNAME_REGEX = "^[a-zA-Z0-9_]{2,7}$";

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
    private static final String[] ERROR_MESSAGES;
    private static final int ERROR_LOGIN = 0;
    private static final int ERROR_PASSWORD = 1;
    private static final int ERROR_RE_ENTER_PASSWORD = 2;
    private static final int ERROR_EMAIL = 3;
    private static final int ERROR_FIRSTNAME = 4;
    private static final int ERROR_LASTNAME = 5;
    private static final int ERROR_CITY = 6;
    private static final int ERROR_REGION = 7;
    private static final int ERROR_EDUC_INST = 8;
    private static final int CAPACITY = 9;

    static {
        ERROR_MESSAGES = new String[CAPACITY];
        ERROR_MESSAGES[ERROR_LOGIN] = Messages.INVALID_LOGIN;
        ERROR_MESSAGES[ERROR_PASSWORD] = Messages.INVALID_PASSWORD;
        ERROR_MESSAGES[ERROR_RE_ENTER_PASSWORD] = Messages.INVALID_REPEAT_PASSWORD;
        ERROR_MESSAGES[ERROR_EMAIL] = Messages.INVALID_EMAIL;
        ERROR_MESSAGES[ERROR_LASTNAME] = Messages.INVALID_LASTNAME;
        ERROR_MESSAGES[ERROR_FIRSTNAME] = Messages.INVALID_FIRSTNAME;
        ERROR_MESSAGES[ERROR_CITY] = Messages.INVALID_CITY;
        ERROR_MESSAGES[ERROR_REGION] = Messages.INVALID_REGION;
        ERROR_MESSAGES[ERROR_EDUC_INST] = Messages.INVALID_EDUC_INST;
    }

    public static List<String> validateRegistrationForm(String userName, String password, String rePassword,
                                                        String email, String lastName, String firstName,
                                                        String city, String region, String educationalInstitution) {
        boolean[] validate = new boolean[CAPACITY];
        validate[ERROR_LOGIN] = DataValidator.validateUserName(userName);
        validate[ERROR_PASSWORD] = DataValidator.validatePassword(password);
        validate[ERROR_RE_ENTER_PASSWORD] = DataValidator.validateRePassword(password, rePassword);
        validate[ERROR_EMAIL] = DataValidator.validateEmail(email);
        validate[ERROR_LASTNAME] = DataValidator.validateName(lastName);
        validate[ERROR_FIRSTNAME] = DataValidator.validateName(firstName);
        validate[ERROR_CITY] = DataValidator.validateName(city);
        validate[ERROR_REGION] = DataValidator.validateName(region);
        validate[ERROR_EDUC_INST] = DataValidator.validateName(educationalInstitution);
        return createErrorList(validate);
    }

    private static List<String> createErrorList(boolean[] checks) {
        List<String> errors = new ArrayList<>(CAPACITY);
        int errorNumber = 0;
        for (int i = 0; i < CAPACITY; i++) {
            if (!checks[i]) {
                log.info(ERROR_MESSAGES[i]);
                errors.add(ERROR_MESSAGES[i]);
                errorNumber++;
            } else {
                errors.add(null);
            }
        }
        return errorNumber == 0 ? null : errors;
    }

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