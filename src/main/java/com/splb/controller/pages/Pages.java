package com.splb.controller.pages;

/**
 * utility class for store pages and links
 */

public abstract class Pages {

    public static final String MANAGE_FACULTY = "/jsp/admin-manage-faculty.jsp";
    public static final String MANAGE_USERS = "/jsp/admin-manage-users.jsp";
    public static final String REGISTER = "/register-page.jsp";
    public static final String LOGIN = "/Login";
    public static final String USER_INFO = "/jsp/user-info.jsp";
    public static final String USER_INFO_SHOW = "/Userinfo";
    public static final String EDITINFO = "/Editinfo";
    public static final String USER_INFO_EDIT = "/jsp/user-info-edit.jsp";
    public static final String MANAGE_APPLICANTS = "/jsp/admin-manage-facultys_applicants.jsp";
    public static final String ERROR = "/error-page.jsp";
    public static final String LOGIN_PAGE = "/login-page.jsp";
    public static final String INDEX = "/index.jsp";
    public static final String USER_INDEX = "/user-index.jsp";
    public static final String FACULTY_CREATE = "/jsp/faculty-create.jsp";
    public static final String EDIT_FACULTY = "/jsp/faculty-edit.jsp";
    public static final String STATEMENT_PAGE = "/jsp/statement-page.jsp";
    public static final String ENTER_RESULT = "/jsp/enter-result-page.jsp";
    public static final String REGISTER_FOR_FACULTY = "/jsp/register-for-faculty-page.jsp";
    public static final String ENROLLMENT_PAGE = "/jsp/enrollment-page.jsp";
    public static final String FORGOT_PASSWORD = "/forgot-pass-page.jsp";
    public static final String ENTER_OTP = "/enter-otp-page.jsp";
    public static final String NEW_PASSWORD = "/reset-pass-page.jsp";
    public static final String FACULTY_TABLE = "/DisplayFaculty";
    public static final String APPLICANT_TABLE = "/DisplayApplicants";
    public static final String REGISTER_FOR_FACULTY_SRV = "/Reristerforfaculty";
    public static final String ENROLLMENT = "/Enrollment";
    public static final String EDIT_ID = "/edit?id=";
    public static final String CREATE = "/create";
    public static final String WATCHLIST_ID = "/watchlist?id=";
    public static final String FORGOT_PASSWORD_SRV = "/forgotPassword";

    private Pages() {
        throw new IllegalStateException("Utility class");
    }

}
