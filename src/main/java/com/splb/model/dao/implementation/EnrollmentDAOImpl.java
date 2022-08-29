package com.splb.model.dao.implementation;

import com.splb.model.dao.AbstractDAO;
import com.splb.model.dao.ApplicantResultDAO;
import com.splb.model.dao.EnrollmentDAO;
import com.splb.model.dao.RegisterDAO;
import com.splb.model.dao.connection.DirectConnectionBuilder;
import com.splb.model.dao.connection.PoolConnectionBuilder;
import com.splb.model.dao.constant.SQLQuery;
import com.splb.model.dao.exception.*;
import com.splb.model.entity.EnrollStatus;
import com.splb.model.entity.Enrollment;
import com.splb.model.entity.Faculty;
import com.splb.service.ApplicantService;
import org.apache.logging.log4j.LogManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.splb.service.EnrollmentService.getStatus;

public class EnrollmentDAOImpl extends AbstractDAO implements EnrollmentDAO {

    private static EnrollmentDAOImpl enrollmentDAOImpl = null;

    private EnrollmentDAOImpl() {
        setConnectionBuilder(new PoolConnectionBuilder());
        log = LogManager.getLogger(getClass().getName());
    }

    public static synchronized EnrollmentDAOImpl getInstance() {
        if (enrollmentDAOImpl == null) {
            enrollmentDAOImpl = new EnrollmentDAOImpl();
        }
        return enrollmentDAOImpl;
    }

    // метод добавляет в енроллмент на факультет аплликанта, уаляя его из стейтмента и результаты регистрации на факультет
    @Override
    public boolean add(int facultyId, int applicantId, int status) throws EnrollmentDAOException {
        ApplicantResultDAO dao = ApplicantResultDAOImpl.getInstance();
        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement ps = conn.
                    prepareStatement(SQLQuery.ADD_APPL_INTO_ENROLLMET)) {
                ps.setInt(1, facultyId);
                ps.setInt(2, applicantId);
                ps.setInt(3, status);
                if (ps.executeUpdate() == 1) {
                    deleteApplicant(conn, applicantId);
                    changeEnrollStatus(conn, applicantId, status);
                    decreaseFacultyPlaces(conn, facultyId);
                    dao.deleteResults(conn, applicantId);
                    conn.commit();
                    return true;
                } else {
                    return false;
                }
            } catch (SQLException | ApplicantResultDAOException e) {
                log.error(e.getMessage());
                throw new EnrollmentDAOException(e.getMessage());
            } finally {
                conn.rollback();
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new EnrollmentDAOException("could not add applicant to enrollment: " + e.getMessage());

        }
    }

    @Override
    public void decreaseFacultyPlaces(Connection conn, int facultyId) throws EnrollmentDAOException {
        try {
            Faculty faculty = FacultyDAOImpl.getInstance().getFacultyById(facultyId);
            int budgetPlaces = faculty.getBudgetPlaces();
            int totalPlaces = faculty.getTotalPlaces();
            if (budgetPlaces > 0 && totalPlaces > 0) {
                budgetPlaces--;
                totalPlaces--;
            }
            if (budgetPlaces == 0 && totalPlaces > 0) {
                totalPlaces--;
            }
            if (budgetPlaces == 0 && totalPlaces == 0) {
                deleteFaculty(conn, facultyId);
            }
            try (PreparedStatement ps = conn
                    .prepareStatement(SQLQuery.DECR_FACULTY_PLCS)) {
                ps.setInt(1, budgetPlaces);
                ps.setInt(2, totalPlaces);
                ps.setInt(3, facultyId);
                if (ps.executeUpdate() == 1) {
                    log.info("Faculty {} updated: new budget {}, total {}", faculty.getName(), budgetPlaces, totalPlaces);
                }
            }
        } catch (SQLException | FacultyDAOException e) {
            log.error(e.getMessage());
            throw new EnrollmentDAOException("could not decrease vacant faculty places: {}" + e.getMessage());
        }
    }


    // метод удалет аппликанта из стейтмента
    @Override
    public void deleteApplicant(Connection conn, int applicantId) throws EnrollmentDAOException {
        try (PreparedStatement ps = conn
                .prepareStatement(SQLQuery.DELETE_APPL_FROM_STATEMENT)) {
            ps.setInt(1, applicantId);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new EnrollmentDAOException("could not delete applicant from statement: " + e.getMessage());
        }
    }

    // метод удалет факультет из стейтмента, когда кончаются свободные места
    @Override
    public void deleteFaculty(Connection conn, int facultyId) throws EnrollmentDAOException {
        try (PreparedStatement ps = conn
                .prepareStatement(SQLQuery.DELETE_FACULTY_FROM_STATEMENT)) {
            ps.setInt(1, facultyId);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new EnrollmentDAOException("could not delete faculty from statement: " + e.getMessage());
        }
    }

    @Override
    public List<Enrollment> getEnrollment() throws EnrollmentDAOException {
        List<Enrollment> list = new ArrayList<>();
        FacultyDAOImpl fdao = FacultyDAOImpl.getInstance();
        UserDAOImpl udao = UserDAOImpl.getInstance();

        try (Connection conn = getConnection();
             PreparedStatement ps = conn
                     .prepareStatement(SQLQuery.GET_ENROLLMENT)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Enrollment e = new Enrollment();
                e.setFaculty(fdao.getFacultyById(rs.getInt(1)));
                e.setApplicant(udao.getApplicantById(rs.getInt(2)));
                e.setStatus(getStatus(rs.getInt(3)));
                list.add(e);
            }
        } catch (SQLException | DAOException e) {
            log.error(e.getMessage());
            throw new EnrollmentDAOException("could not get enrollment list: " + e.getMessage());
        }
        return list;
    }

    @Override
    public void changeEnrollStatus(Connection conn, int applicantID, int status) throws EnrollmentDAOException {
        try (PreparedStatement ps = conn.prepareStatement(SQLQuery.CHANGE_ENROLL_STATUS)) {
            ps.setInt(1, status);
            ps.setInt(2, applicantID);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new EnrollmentDAOException("could not change enroll status: " + e.getMessage());
        }
    }

    @Override
    public Enrollment getApplicantEnrollStatus(int userID) throws EnrollmentDAOException {
        Enrollment enrollment = new Enrollment();
        UserDAOImpl userDAO = UserDAOImpl.getInstance();
        FacultyDAOImpl facultyDAO = FacultyDAOImpl.getInstance();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(SQLQuery.GET_APPL_ENROLL_STATUS)) {
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                enrollment.setApplicant(userDAO.getApplicantById(rs.getInt(2)));
                enrollment.setFaculty(facultyDAO.getFacultyById(rs.getInt(1)));
                enrollment.setStatus(getStatus(rs.getInt(3)));
            }
        } catch (SQLException | DAOException e) {
            log.error(e.getMessage());
            throw new EnrollmentDAOException("could not get enroll status: " + e.getMessage());
        }
        return enrollment;
    }
}