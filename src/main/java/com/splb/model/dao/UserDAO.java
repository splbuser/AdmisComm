package com.splb.model.dao;

import com.splb.model.dao.exception.UserDAOException;
import com.splb.model.entity.Applicant;
import com.splb.model.entity.Faculty;

import java.sql.Connection;
import java.util.List;


public interface UserDAO {

    /**
     * add applicant to database
     * @param applicant
     * @return true if applicant was added
     * @throws UserDAOException
     */
    boolean addApplicant(Applicant applicant, Connection con) throws UserDAOException;


    /**
     * update user info
     * @param applicant
     * @param con
     * @return
     * @throws UserDAOException
     */
    boolean update(Applicant applicant, Connection con) throws UserDAOException;

    /**
     * delete applicant by username
     * @param userName
     * @return true if applicant was deleted
     * @throws UserDAOException
     */
    boolean delete(String userName, Connection con) throws UserDAOException;

    /**
     * update applicant's enroll status after statement finalization
     * @param applicant
     * @return true if update eas successful
     * @throws UserDAOException
     */
    boolean updateEnrollStatus(Applicant applicant, Connection con) throws UserDAOException;


    /**
     * search applicant by username
     * @param login
     * @return true if applicant was found
     * @throws UserDAOException
     */
    boolean findApplicantByName(String login, Connection con) throws UserDAOException;

    /**
     * check if such applicant exists in database
     * @param name
     * @return true if applicant exists
     * @throws UserDAOException
     */
    boolean checkApplicant(String name, Connection con) throws UserDAOException;


    /**
     * returns list of applicants with limit and offset for pagination
     * @param limit
     * @param offset
     * @return List<Applicant>
     * @throws UserDAOException
     */
    List<Applicant> findAllApplicants(int limit, int offset, Connection con) throws UserDAOException;

    /**
     * returns user after login
     * @param username
     * @param password
     * @return Applicant
     * @throws UserDAOException
     */
    Applicant getUser(String username, String password, Connection con) throws UserDAOException;

    /**
     * returns applicant by ID
     * @param applicantId
     * @param con
     * @return Applicant
     * @throws UserDAOException
     */
    Applicant getApplicantById(int applicantId, Connection con) throws UserDAOException;

    /**
     * returns applicant by username, last name or first name
     * @param name
     * @param con
     * @return List<Applicant>
     * @throws UserDAOException
     */
    List<Applicant> getApplicantForSearch(String name, Connection con) throws UserDAOException;

    /**
     * returns no enrolled applicants
     * @param con
     * @return List<Applicant>
     * @throws UserDAOException
     */
    List<Applicant> getNotEnrollApplicants(Connection con) throws UserDAOException;

    /**
     * block user by ID
     * @param userId
     * @param con
     * @return true if user was blocked
     * @throws UserDAOException
     */
    boolean blockUserById(int userId, Connection con) throws UserDAOException;

    /**
     * unblock user by ID
     * @param userId
     * @param con
     * @return true if user was unblocked
     * @throws UserDAOException
     */
    boolean unblockUserById(int userId, Connection con) throws UserDAOException;

    /**
     * check user block status
     * @param userId
     * @param con
     * @return true if required user is blocked
     * @throws UserDAOException
     */
    boolean isBlockedUserCheck(int userId, Connection con) throws UserDAOException;

    /**
     * add uploaded filename to userinfo
     * @param userId
     * @param filename
     * @param con
     * @return true if success
     * @throws UserDAOException
     */
    boolean upload(int userId, String filename, Connection con) throws UserDAOException;

    /**
     * method returns sorted list of faculties where the applicant registered
     * @param id
     * @return List<Faculty>
     * @throws UserDAOException
     */
    List<Faculty> getApplicantsFacultyList(int id, Connection con) throws UserDAOException;

    /**
     * returns applicants result from faculty registration
     * @param userId
     * @param facultyID
     * @param con
     * @return int[]
     * @throws UserDAOException
     */
    int[] getApplicantsFacultyResult(int userId, int facultyID, Connection con) throws UserDAOException;

    /**
     * returns user list length, exclude admin users
     * @param con
     * @return int
     * @throws UserDAOException
     */
    int getLength(Connection con) throws UserDAOException;

}
