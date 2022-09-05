package com.splb.model.dao;

import com.splb.model.dao.exception.StatementDAOException;
import com.splb.model.entity.Applicant;
import com.splb.model.entity.Faculty;
import com.splb.model.entity.StatementResult;

import java.sql.Connection;
import java.util.List;

public interface StatementDAO {
    /**
     * adds the result of the applicant's registration to the faculty
     * @param facultyId
     * @param userId
     * @return return true if user was added
     * @throws StatementDAOException
     */

    boolean addUserToFaculty(int facultyId, int userId, Connection con) throws StatementDAOException;

    /**
     * remove applicants result from statement
     * @param facultyId
     * @param userId
     * @return return true if user was removed
     * @throws StatementDAOException
     */
    boolean removeUserFromFaculty(int facultyId, int userId, Connection con) throws StatementDAOException;

    /**
     * checks the applicant's registration for the faculty in question
     * @param facultyId
     * @param userId
     * @return return true if result was added
     * @throws StatementDAOException
     */
    boolean checkUserFaculty(int facultyId, int userId, Connection con) throws StatementDAOException;

    /**
     * returns the list of results of the applicant that were added to the statement
     * @return list<Statement>
     * @throws StatementDAOException
     */
    List<com.splb.model.entity.Statement> getStatementList (Connection con) throws StatementDAOException;

    /**
     * returns list of applicants by the given faculty id, whose results have been added to the statement
     * @param facultyId
     * @return List<Applicant>
     * @throws StatementDAOException
     */
    List<Applicant> getFacultysApplicantsFromStatement(int facultyId, Connection con) throws StatementDAOException;

    /**
     * returns for applicants a list of faculties from Statement
     * @param applicantId
     * @return List<Faculty>
     * @throws StatementDAOException
     */
    List<Faculty> getFacultyFromStatementForApplicant(int applicantId, Connection con) throws StatementDAOException;

    /**
     * method returns the list of results of the applicant that were added to the statement
     * @param userid
     * @return List<StatementResult>
     * @throws StatementDAOException
     */
    List<StatementResult> getStatementResult(int userid, Connection con) throws StatementDAOException;

}
