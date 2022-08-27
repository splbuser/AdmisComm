package com.splb.model.dao;

import com.splb.model.dao.exception.StatementDAOException;
import com.splb.model.entity.Applicant;
import com.splb.model.entity.Faculty;
import com.splb.model.entity.Statement;
import com.splb.model.entity.StatementResult;

import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public interface StatementDAO {
    /**
     * add user's faculty result to statement
     * @param facultyId
     * @param userId
     * @return return true if user was added
     * @throws StatementDAOException
     */

    boolean addUserToFaculty(int facultyId, int userId) throws StatementDAOException;

    /**
     * remove user's faculty result from statement
     * @param facultyId
     * @param userId
     * @return return true if user was removed
     * @throws StatementDAOException
     */
    boolean removeUserFromFaculty(int facultyId, int userId) throws StatementDAOException;

    /**
     *  method check if user's faculty result was added to statement before
     * @param facultyId
     * @param userId
     * @return return true if result was added
     * @throws StatementDAOException
     */
    boolean checkUserFaculty(int facultyId, int userId) throws StatementDAOException;

    /**
     * method for getting Statement list with added applicants result
     * @return list<Statement>
     * @throws StatementDAOException
     */
    List<com.splb.model.entity.Statement> getStatementList () throws StatementDAOException;

//    Map<String, List<String>> getStatement() throws StatementDAOException;

    List<Applicant> getFacultysApplicantsFromStatement(int facultyId) throws StatementDAOException;

    List<Faculty> getFacultyFromStatementForApplicant(int applicantId) throws StatementDAOException;

    List<StatementResult> getStatementResult(int userid) throws StatementDAOException;

}
