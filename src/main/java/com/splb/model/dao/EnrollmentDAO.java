package com.splb.model.dao;

import com.splb.model.dao.exception.EnrollmentDAOException;
import com.splb.model.entity.EnrollStatus;
import com.splb.model.entity.Enrollment;

import java.sql.Connection;
import java.util.List;

public interface EnrollmentDAO {

    /**
     * add user into enrollment table with current enroll status
     * @param facultyId
     * @param applicantId
     * @param status
     * @return
     * @throws EnrollmentDAOException
     */
    boolean add(int facultyId, int applicantId, int status) throws EnrollmentDAOException;


    void deleteApplicant(Connection conn, int applicantId) throws EnrollmentDAOException;

    void deleteFaculty(Connection conn, int facultyId) throws EnrollmentDAOException;

    List<Enrollment> getEnrollment() throws EnrollmentDAOException;

    void changeEnrollStatus(Connection conn, int applicantID, int status) throws EnrollmentDAOException;

    Enrollment getApplicantEnrollStatus(int userID) throws EnrollmentDAOException;

}
