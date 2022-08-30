package com.splb.model.dao.constant;

public abstract class SQLQuery {

    public static final String FIND_APPLICANT_BT_ID = "SELECT * FROM applicant WHERE id=?";
    public static final String FIND_APPLICANT_BY_USERNAME = "SELECT * FROM applicant WHERE user_name=?";
    public static final String SEARCH_APPLICANT = "SELECT * FROM applicant WHERE first_name=? or last_name=? or user_name=?";
    public static final String FIND_ALL_APPLICANTS = "SELECT * FROM applicant WHERE admin_status = 0 ORDER BY id  LIMIT ? OFFSET ?";
    public static final String FIND_ALL_FACULTY = "SELECT * FROM faculty";
    public static final String FIND_FACULTY_BY_NAME = "SELECT * FROM faculty WHERE name=?";
    public static final String FIND_FACULTY_BY_ID = "SELECT * FROM faculty WHERE id=?";
    public static final String BLOCK_CHECK_USER = "SELECT block_status FROM applicant WHERE id=?";
    public static final String GET_USER_BY_LOGIN = "SELECT * FROM applicant WHERE user_name = ? AND password = ?";
    public static final String GET_APP_FOR_FACULTY = "SELECT * FROM applicant_has_faculty where faculty_id =? ";
    public static final String FIND_USER_IN_STATEMENT = "SELECT * FROM statement_app WHERE faculty__id = ? AND applicant_id = ?";
    public static final String GET_RESULT_FOR_APPLICANT = "SELECT * FROM applicant_results where applicant_id = ?";
    public static final String GET_STATEMENT_LIST = "SELECT * FROM statement_app order by total_score DESC";
    public static final String GET_APPL_FROM_STMNT_BT_FCLTY = "SELECT * FROM statement_app WHERE faculty__id = ?";
    public static final String SELECT_FROM_STATEMENT_APP = "SELECT * FROM statement_app WHERE applicant_id = ?";
    public static final String CREATE_USER = "INSERT INTO  applicant (id, user_name, password, admin_status, first_name, last_name, email, city, region, educational_institution, block_status) VALUES (DEFAULT, ?, ?, DEFAULT, ?, ?, ?, ?, ?, ?, DEFAULT)";
    public static final String CREATE_FACULTY = "INSERT INTO faculty  (id, name, budget_places, total_places, first_subject, second_subject) VALUES (DEFAULT, ?, ?, ?, ?, ?)";
    public static final String INSERT_RESULT = "INSERT INTO applicant_results VALUES (?, ?, ?, ?, ?, ?, ?)";
    public static final String ADD_USER_TO_STATEMENT = "INSERT INTO statement_app VALUES (?, ?, ?)";
    public static final String REGISTER_FOR_SINGLE_FACULTY = "INSERT INTO applicant_has_faculty VALUES (?, ?, ?, ?)";
    public static final String UPDATE_FACULTY = "UPDATE faculty SET name = ?, budget_places = ?, total_places = ?, first_subject = ?, second_subject = ? WHERE id = ?";
    public static final String BLOCK_USER_BY_ID = "UPDATE applicant SET block_status = ? WHERE id = ?";
    public static final String UPD_USER_ENROLL_STATUS = "UPDATE applicant SET enroll_status = ? WHERE id = ?";
    public static final String DELETE_FACULTY_BY_ID = "DELETE from faculty WHERE id = ?";
    public static final String REMOVE_USER_FROM_STATEMENT = "DELETE from statement_app WHERE faculty__id = ? AND applicant_id = ?;";
    public static final String APPLICANT_HAS_FACULTY = "SELECT * FROM applicant_has_faculty WHERE applicant_id = ?";
    public static final String SELECT_FROM_APPLICANT = "SELECT * FROM applicant";
    public static final String GET_APPL_FACULTY_RES = "SELECT * FROM applicant_has_faculty WHERE applicant_id = ? AND faculty_id = ?";
    public static final String GET_RESUL_SUM = "SELECT * FROM applicant_has_faculty WHERE applicant_id = ? and faculty_id = ?";
    public static final String ADD_APPL_INTO_ENROLLMET = "insert into enrollment value (?, ?, ?)";
    public static final String DECR_FACULTY_PLCS = "UPDATE faculty SET budget_places = ?, total_places = ? WHERE id = ?";
    public static final String DELETE_APPL_FROM_STATEMENT = "DELETE FROM statement_app WHERE applicant_id=?";
    public static final String DELETE_FACULTY_FROM_STATEMENT = "DELETE FROM statement_app WHERE faculty__id=?";
    public static final String GET_ENROLLMENT = "SELECT * FROM enrollment";
    public static final String GET_NOT_ENROLLMENT = "SELECT * FROM applicant WHERE enroll_status=0";
    public static final String CHANGE_ENROLL_STATUS = "UPDATE applicant SET enroll_status = ? WHERE id = ?";
    public static final String GET_APPL_ENROLL_STATUS = "SELECT * FROM enrollment WHERE applicant_id = ?";
    public static final String DELETE_APPL_FACULTY_REGS = "DELETE FROM applicant_has_faculty WHERE applicant_id=?";
    public static final String DELETE_USER = "DELETE FROM applicant WHERE user_name=?";
    public static final String APP_UPLOADED = "UPDATE applicant SET upload_status = ? WHERE id = ?";


    private SQLQuery() {
        throw new IllegalStateException("Utility class");
    }
}
