package com.splb.model.dao;

import com.splb.model.dao.exception.EnrollmentDAOException;
import com.splb.model.entity.Enrollment;

import java.sql.Connection;
import java.util.List;

public interface EnrollmentDAO {

    /**
     * add user into enrollment table with current enroll status
     * @param facultyId
     * @param applicantId
     * @param status
     * @return true if user was added
     * @throws EnrollmentDAOException
     */
    boolean add(int facultyId, int applicantId, int status, Connection con) throws EnrollmentDAOException;

    /**
     * delete applicant from statement after finalization
     * @param con
     * @param applicantId
     * @throws EnrollmentDAOException
     */
    void deleteApplicant(Connection con, int applicantId) throws EnrollmentDAOException;

    /**
     * delete faculty from statement after no more capacity left
     * @param con
     * @param facultyId
     * @throws EnrollmentDAOException
     */
    void deleteFaculty(Connection con, int facultyId) throws EnrollmentDAOException;

    /**
     * method returns list of enrollment
     * @param con
     * @return List<Enrollment>
     * @throws EnrollmentDAOException
     */
    List<Enrollment> getEnrollment(Connection con) throws EnrollmentDAOException;

    /**
     * method returns current applicant's enroll status
     * @param userID
     * @param con
     * @return Enrollment
     * @throws EnrollmentDAOException
     */
    Enrollment getApplicantEnrollStatus(int userID, Connection con) throws EnrollmentDAOException;

}
