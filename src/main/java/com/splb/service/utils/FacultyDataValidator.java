package com.splb.service.utils;

import com.splb.controller.pages.Messages;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Objects.nonNull;

/**
 * utility class for verification input data for faculty
 */
public class FacultyDataValidator {

    private static final Logger log = LogManager.getLogger(FacultyDataValidator.class);
    private static final String FACULTY_DATA_PATTERN = "[A-ZА-я][a-zа-яё]{1,20}(\\s?[A-Za-z][a-z]{1,20})*";

    private static final String[] ERROR_MESSAGES;
    public static final int ERROR_NAME = 0;
    public static final int ERROR_PLACES = 1;
    public static final int ERROR_SECOND_SUBJ = 2;
    public static final int ERROR_FIRST_SUBJ = 3;
    private static final int CAPACITY = 4;

    static {
        ERROR_MESSAGES = new String[CAPACITY];
        ERROR_MESSAGES[ERROR_NAME] = Messages.INVALID_FNAME;
        ERROR_MESSAGES[ERROR_PLACES] = Messages.INVALID_PLACES;
        ERROR_MESSAGES[ERROR_FIRST_SUBJ] = Messages.INVALID_FSUBJ;
        ERROR_MESSAGES[ERROR_SECOND_SUBJ] = Messages.INVALID_SSUBG;
    }

    public static List<String> validateForm(String name, int bplaces, int tplaces, String fsubj, String ssubj){
        boolean[] validate = new boolean[CAPACITY];
        validate[ERROR_NAME] = FacultyDataValidator.validateName(name);
        validate[ERROR_PLACES] = FacultyDataValidator.validateCapacity(bplaces, tplaces);
        validate[ERROR_FIRST_SUBJ] = FacultyDataValidator.validateName(fsubj);
        validate[ERROR_SECOND_SUBJ] = FacultyDataValidator.validateName(ssubj);
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

    public static List<String> getValidatedValues(String[] input, List<String> errors) {
        for (int i = 0; i < input.length; i++) {
            if (nonNull(input[i])) {
                if (nonNull(errors.get(i))) {
                    input[i] = null;
                }
            }
        }
        return Arrays.asList(input);
    }

    public static boolean validateName(String string) {
        if (string != null) {
            return string.matches(FACULTY_DATA_PATTERN);
        } else {
            return false;
        }
    }

    public static boolean validateCapacity(int budget, int total) {
        try {
            return budget >= 0 && total >= 2 && total <= 100 && total >= budget;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private FacultyDataValidator () {}
}