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
     * @return
     * @throws UserDAOException
     */
    boolean addApplicant(Applicant applicant, Connection con) throws UserDAOException;

    /**
     * delete applicant by username
     * @param userName
     * @return
     * @throws UserDAOException
     */
    boolean delete(String userName, Connection con) throws UserDAOException;

    /**
     * update applicant's enroll status after statement finalization
     * @param applicant
     * @return
     * @throws UserDAOException
     */
    boolean updateEnrollStatus(Applicant applicant, Connection con) throws UserDAOException;


    /**
     * search applicant by username
     * @param login
     * @return
     * @throws UserDAOException
     */
    boolean findApplicantByName(String login, Connection con) throws UserDAOException;

    /**
     * check if such applicant exists in database
     * @param name
     * @return
     * @throws UserDAOException
     */
    boolean checkApplicant(String name, Connection con) throws UserDAOException;


    /**
     * returns list of applicants with limit and offset for pagination
     * @param limit
     * @param offset
     * @return
     * @throws UserDAOException
     */
    List<Applicant> findAllApplicants(int limit, int offset, Connection con) throws UserDAOException;

    /**
     * returns user after login
     * @param username
     * @param password
     * @return
     * @throws UserDAOException
     */
    Applicant getUser(String username, String password, Connection con) throws UserDAOException;

    Applicant getApplicantById(int applicantId, Connection con) throws UserDAOException;

    List<Applicant> getApplicantForSearch(String name, Connection con) throws UserDAOException;

    List<Applicant> getNotEnrollApplicants(Connection con) throws UserDAOException;

    boolean blockUserById(int userId, Connection con) throws UserDAOException;

    boolean unblockUserById(int userId, Connection con) throws UserDAOException;

    boolean isBlockedUserCheck(int userId, Connection con) throws UserDAOException;

    boolean upload(int userId, String filename, Connection con) throws UserDAOException;

    /**
     * method returns sorted list of faculties where the applicant registered
     *
     * @param id
     * @return List<Faculty>
     * @throws UserDAOException
     */
    List<Faculty> getApplicantsFacultyList(int id, Connection con) throws UserDAOException;

    int[] getApplicantsFacultyResult(int userId, int facultyID, Connection con) throws UserDAOException;

    int getLength(Connection con) throws UserDAOException;

}
