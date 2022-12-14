package com.splb.model.dao.constant;

/**
 * utility class for store table fields and so on
 */
public abstract class Fields {

    public static final String ID = "id";
    public static final String APPLICANT_USER_NAME = "user_name";
    public static final String APPLICANT_PASSWORD = "password";
    public static final String APPLICANT_ADMIN_STATUS = "admin_status";
    public static final String APPLICANT_FIRST_NAME = "first_name";
    public static final String APPLICANT_LAST_NAME = "last_name";
    public static final String APPLICANT_EMAIL = "email";
    public static final String APPLICANT_CITY = "city";
    public static final String APPLICANT_REGION = "region";
    public static final String APPLICANT_EDUC_INST = "educational_institution";
    public static final String APPLICANT_BLOCK_STATUS = "block_status";
    public static final String APPLICANT_ENROLL_STATUS = "enroll_status";
    public static final String APPLICANT_UPLOAD_STATUS = "upload_status";


    public static final String FACULTY_NAME = "name";
    public static final String FACULTY_BUDGET_PLACES = "budget_places";
    public static final String FACULTY_TOTAL_PLACES = "total_places";
    public static final String SUBJECT_ONE = "first_subject";
    public static final String SUBJECT_TWO = "second_subject";


    public static final String RESULT_ALGEBRA = "algebra";
    public static final String RESULT_BIOLOGY = "biology";
    public static final String RESULT_CHEMISTRY = "chemistry";
    public static final String RESULT_ENGLISH = "english";
    public static final String RESULT_LITERATURE = "literature";
    public static final String RESULT_HISTORY = "history";
    public static final String PASSWORD_REPEAT = "password_repeat";


    public static final String SORT_BY = "sortBy";
    public static final String TYPE = "type";
    public static final String LIMIT = "limit";
    public static final String PAGE = "page";
    public static final String ACTION = "action";
    public static final String SEARCH = "search";
    public static final String APPLICANT_ID = "applicant_id";
    public static final String FACULTY_ID = "faculty_id";
    public static final String FACULTY__ID = "faculty__id";
    public static final String SUBJ_ONE = "subjOne";
    public static final String SUBJ_TWO = "subjTwo";

    public static final String SITE_KEY ="6Lc9jLUhAAAAADD3kaZmCDm2jArFWMExCcUJZoBh";
    public static final String SECRET_KEY ="6Lc9jLUhAAAAAIlUAaps19E2SHaOH6Xa3pLXlvWf";
    public static final String SITE_VERIFY_URL = //
            "https://www.google.com/recaptcha/api/siteverify";
    public static final String TOTAL_SCORE = "total_score";
    public static final String FINALIZE = "finalize";
    public static final String ADDALL = "addall";
    public static final String WATCHLIST_ID = "/watchlist?id=";
    public static final String LOCALE = "cookieLocale";
    public static final String ASC = "ASC";
    public static final String FACULTY = "faculty";
    public static final String BUDGET = "budget";
    public static final String CONTRACT = "contract";
    public static final String FIRST_SUBJ_RESULT = "first_subj_result";
    public static final String SECOND_SUBJ_RESULT = "second_subj_result";
    public static final String RESULT_CHECK = "resultCheck";
    public static final String USER = "user";
    public static final String STATUS = "status";
    public static final String OTP = "otp";
    public static final String VALID_VALUES = "validValues";

    private Fields() {
        throw new IllegalStateException("Utility class");
    }

}
