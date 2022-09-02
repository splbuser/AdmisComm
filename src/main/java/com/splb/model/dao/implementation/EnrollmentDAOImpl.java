package com.splb.model.dao.implementation;

import com.splb.model.dao.AbstractDAO;
import com.splb.model.dao.ApplicantResultDAO;
import com.splb.model.dao.EnrollmentDAO;
import com.splb.model.dao.constant.SQLQuery;
import com.splb.model.dao.exception.*;
import com.splb.model.entity.Enrollment;
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
        log = LogManager.getLogger(getClass().getName());
    }

    public static synchronized EnrollmentDAOImpl getInstance() {
        if (enrollmentDAOImpl == null) {
            enrollmentDAOImpl = new EnrollmentDAOImpl();
        }
        return enrollmentDAOImpl;
    }

    @Override
    public boolean add(int facultyId, int applicantId, int status, Connection con) throws EnrollmentDAOException {
        ApplicantResultDAO dao = ApplicantResultDAOImpl.getInstance();
        try (PreparedStatement ps = con.
                prepareStatement(SQLQuery.ADD_APPL_INTO_ENROLLMET)) {
            ps.setInt(1, facultyId);
            ps.setInt(2, applicantId);
            ps.setInt(3, status);
            if (ps.executeUpdate() == 1) {
                deleteApplicant(con, applicantId);
                changeEnrollStatus(con, applicantId, status);
                dao.deleteResults(con, applicantId);
                return true;
            } else {
                return false;
            }
        } catch (SQLException | ApplicantResultDAOException e) {
            log.error(e.getMessage());
            throw new EnrollmentDAOException("could not add applicant to enrollment: " + e.getMessage());
        }
    }

    @Override
    public void deleteApplicant(Connection con, int applicantId) throws EnrollmentDAOException {
        try (PreparedStatement ps = con
                .prepareStatement(SQLQuery.DELETE_APPL_FROM_STATEMENT)) {
            ps.setInt(1, applicantId);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new EnrollmentDAOException("could not delete applicant from statement: " + e.getMessage());
        }
    }

    @Override
    public void deleteFaculty(Connection con, int facultyId) throws EnrollmentDAOException {
        try (PreparedStatement ps = con
                .prepareStatement(SQLQuery.DELETE_FACULTY_FROM_STATEMENT)) {
            ps.setInt(1, facultyId);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new EnrollmentDAOException("could not delete faculty from statement: " + e.getMessage());
        }
    }

    @Override
    public List<Enrollment> getEnrollment(Connection con) throws EnrollmentDAOException {
        List<Enrollment> list = new ArrayList<>();
        FacultyDAOImpl fdao = FacultyDAOImpl.getInstance();
        UserDAOImpl udao = UserDAOImpl.getInstance();
        try (PreparedStatement ps = con
                .prepareStatement(SQLQuery.GET_ENROLLMENT)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Enrollment en = new Enrollment();
                en.setFaculty(fdao.getFacultyById(rs.getInt(1), con));
                en.setApplicant(udao.getApplicantById(rs.getInt(2), con));
                en.setStatus(getStatus(rs.getInt(3)));
                list.add(en);
            }
        } catch (SQLException | DAOException e) {
            log.error(e.getMessage());
            throw new EnrollmentDAOException("could not get enrollment list: " + e.getMessage());
        }
        return list;
    }

    @Override
    public void changeEnrollStatus(Connection con, int applicantID, int status) throws EnrollmentDAOException {
        try (PreparedStatement ps = con.prepareStatement(SQLQuery.CHANGE_ENROLL_STATUS)) {
            ps.setInt(1, status);
            ps.setInt(2, applicantID);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new EnrollmentDAOException("could not change enroll status: " + e.getMessage());
        }
    }

    @Override
    public Enrollment getApplicantEnrollStatus(int userID, Connection con) throws EnrollmentDAOException {
        Enrollment enrollment = new Enrollment();
        UserDAOImpl userDAO = UserDAOImpl.getInstance();
        FacultyDAOImpl facultyDAO = FacultyDAOImpl.getInstance();
        try (PreparedStatement ps = con.prepareStatement(SQLQuery.GET_APPL_ENROLL_STATUS)) {
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                enrollment.setApplicant(userDAO.getApplicantById(rs.getInt(2), con));
                enrollment.setFaculty(facultyDAO.getFacultyById(rs.getInt(1), con));
                enrollment.setStatus(getStatus(rs.getInt(3)));
            }
        } catch (SQLException | DAOException e) {
            log.error(e.getMessage());
            throw new EnrollmentDAOException("could not get enroll status: " + e.getMessage());
        }
        return enrollment;
    }
}