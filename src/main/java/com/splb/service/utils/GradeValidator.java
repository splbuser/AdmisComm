package com.splb.service.utils;

import com.splb.controller.pages.Messages;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GradeValidator {

    private static final Logger log = LogManager.getLogger(GradeValidator.class);

    private GradeValidator() {
    }

    public static boolean validateGrades(String[] grades) {
        for (String grade : grades) {
            try {
                int gradeInt = Integer.parseInt(grade);
                if (!(gradeInt >= 1 && gradeInt <= 12)) {
                    log.debug(Messages.INVALID_GRADE, gradeInt);
                    return false;
                }
            } catch (NumberFormatException e) {
                log.debug("could not validate {}", e.getMessage());
                return false;
            }
        }
        return true;
    }

}