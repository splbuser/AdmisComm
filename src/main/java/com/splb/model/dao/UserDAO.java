package com.splb.model.dao;

import com.splb.model.dao.exception.UserDAOException;
import com.splb.model.entity.Applicant;
import com.splb.model.entity.Faculty;

import java.util.List;


public interface UserDAO {

    /**
     * add applicant to database
     * @param applicant
     * @return
     * @throws UserDAOException
     */
    boolean addApplicant(Applicant applicant) throws UserDAOException;

    /**
     * delete applicant by username
     * @param userName
     * @return
     * @throws UserDAOException
     */
    boolean delete(String userName) throws UserDAOException;

    /**
     * update applicant's enroll status after statement finalization
     * @param applicant
     * @return
     * @throws UserDAOException
     */
    boolean updateEnrollStatus(Applicant applicant) throws UserDAOException;


    /**
     * search applicant by username
     * @param login
     * @return
     * @throws UserDAOException
     */
    boolean findApplicantByName(String login) throws UserDAOException;

    /**
     * check if such applicant exists in database
     * @param name
     * @return
     * @throws UserDAOException
     */
    boolean checkApplicant(String name) throws UserDAOException;


    /**
     * returns list of applicants with limit and offset for pagination
     * @param limit
     * @param offset
     * @return
     * @throws UserDAOException
     */
    List<Applicant> findAllApplicants(int limit, int offset) throws UserDAOException;

    /**
     * returns user after login
     * @param username
     * @param password
     * @return
     * @throws UserDAOException
     */
    Applicant getUser(String username, String password) throws UserDAOException;

    Applicant getApplicantById(int applicantId) throws UserDAOException;

    List<Applicant> getApplicantForSearch(String name) throws UserDAOException;

    List<Applicant> getNotEnrollApplicants() throws UserDAOException;

    boolean blockUserById(int userId) throws UserDAOException;

    boolean unblockUserById(int userId) throws UserDAOException;

    boolean isBlockedUserCheck(int userId) throws UserDAOException;

    boolean upload(int userId, String filename) throws UserDAOException;

    /**
     * method returns sorted list of faculties where the applicant registered
     *
     * @param id
     * @return List<Faculty>
     * @throws UserDAOException
     */
    List<Faculty> getApplicantsFacultyList(int id) throws UserDAOException;

    int[] getApplicantsFacultyResult(int userId, int facultyID) throws UserDAOException;

    int getLength() throws UserDAOException;

}
