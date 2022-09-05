package com.splb.model.dao;

import com.splb.model.dao.exception.ApplicantResultDAOException;
import com.splb.model.entity.ApplicantResult;

import java.sql.Connection;

public interface ApplicantResultDAO {

    /**
     * method add new Applicant's result for specific user
     *
     * @param applicantResult
     * @return returns true if resets were added and false whether hot
     * @throws ApplicantResultDAOException
     */
    boolean insert(ApplicantResult applicantResult, Connection con) throws ApplicantResultDAOException;

    /**
     * @param userId
     * @return returns for specific user his Applicant's result by user ID
     * @throws ApplicantResultDAOException
     */
    ApplicantResult getApplicantResult(int userId, Connection con) throws ApplicantResultDAOException;

    /**
     * get sum of all subjects result
     * @param userId
     * @return
     * @throws ApplicantResultDAOException
     */
    int getResultSum(int userId, Connection con) throws ApplicantResultDAOException;

    /**
     * @param userId
     * @return returns true if Applicant's result were submitted to statement and false whether hot
     * @throws ApplicantResultDAOException
     */
    boolean isSubmittedResult(int userId, Connection con) throws ApplicantResultDAOException;

    /**
     * method for adding Applicant's result while his registration on specific faculty
     * @param userId
     * @param subjOne
     * @param subjTwo
     * @param facultyId
     * @return returns true if results were successfully added and 0 whether hot
     * @throws ApplicantResultDAOException
     */
    boolean addFacultySubjectResult(int userId, int subjOne, int subjTwo,
                                    int facultyId, Connection con)
            throws ApplicantResultDAOException;

    /**
     * method delete users results after enrollment finalization
     * @param conn
     * @param userId
     * @return
     * @throws ApplicantResultDAOException
     */
    boolean deleteResults(Connection conn, int userId) throws ApplicantResultDAOException;

}