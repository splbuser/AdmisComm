package com.splb.service.utils;

/**
 * utility class for verification input data for faculty
 */
public class FacultyDataValidator {
    private static final String FACULTY_DATA_PATTERN = "[A-Z][a-z]{1,20}(\\s?[A-Za-z][a-z]{1,20})*";

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