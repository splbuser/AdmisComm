package com.splb.model.dao;

import com.splb.model.dao.exception.DBException;
import com.splb.model.dao.exception.UserDAOException;
import com.splb.model.entity.Applicant;
import com.splb.model.entity.Faculty;

import java.sql.ResultSet;
import java.util.List;


public interface UserDAO {

    boolean addApplicant(Applicant applicant) throws UserDAOException;

    Applicant getUser(String username, String password) throws UserDAOException;

    boolean findApplicantByName(String login) throws UserDAOException;

    List<Applicant> findAllApplicants(int limit, int offset) throws UserDAOException;

    Applicant getApplicantById(int applicantId) throws UserDAOException;

    boolean blockUserById(int userId) throws UserDAOException;

    boolean unblockUserById(int userId) throws UserDAOException;

    boolean isBlockedUserCheck(int userId) throws UserDAOException;
    // метод возвращает список факултетов по id, куда зарегистриовался аппликат
    List<Faculty> getApplicantsFacultyList(int id) throws UserDAOException;

    int[] getApplicantsFacultyResult (int userId, int facultyID) throws UserDAOException;

    int getLength() throws UserDAOException;;
}
