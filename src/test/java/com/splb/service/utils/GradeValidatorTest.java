package com.splb.service.utils;


import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;


public class GradeValidatorTest {

    private String[] validGrades;
    private String[] invalidGrades;

    @BeforeClass
    public void setUp() {
        validGrades = new String[]{"1", "3", "8", "12"};
        invalidGrades = new String[]{"f,!,0,50"};
    }

    @Test
    public void testValidateGradesPositive() {
        assertTrue(GradeValidator.validateGrades(validGrades));
    }

    @Test
    public void testValidateGradesNegative() {

        assertFalse(GradeValidator.validateGrades(invalidGrades));
    }

}