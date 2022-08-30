package com.splb.service.utils.notifier;

public enum MailText {

    REG_SUBJ("Registration successful!"),
    ENROLL_SUBJ("Enrollment committed"),
    REG_BODY("Log in into site and follow applicant's guide"),
    ENROLL_BODY("Hello. We are pleased to inform you that following the results of enrollment, " +
            "your results helped you get a %s place at the %s faculty."),
    NO_ENROLL_BODY("Hello. Unfortunately, your results were not enough to be enrolled in any faculty.");

    private final String text;

    MailText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
