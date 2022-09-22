package com.splb.service.utils.notifier;

public enum MailText {

    REG_SUBJ("Registration successful!"),
    REG_BODY("Log in into site and follow applicant's guide."+
            "Your username: [%s] \t You password: [%s]"),
    ENROLL_SUBJ("Enrollment committed"),
    ENROLL_BODY("Hello. We are pleased to inform you that following the results of enrollment, " +
            "your results helped you get a %s place at the %s faculty."),
    NO_ENROLL_BODY("Hello. Unfortunately, your results were not enough to be enrolled in any faculty."),
    DEFAULT("Hello. You were registered in the admissions committee system, but you never made your choice."),

    RES_PASS_SUBJ("Password change request"),
    RES_PASS_SUC_SUBJ("Password changed"),
    RES_PASS_BODY("Here is your verification code %d. " +
            "Enter it in the form on our website to change your password."),
    RES_PASS_SUC_BODY("Password changed successfully. Your new password: %s");

    private final String text;

    MailText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
