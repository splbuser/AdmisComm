package com.splb.model.dao;

import com.splb.model.dao.exception.FacultyDAOException;
import com.splb.model.entity.Applicant;
import com.splb.model.entity.Faculty;

import java.sql.Connection;
import java.util.List;

public interface FacultyDAO {

    /**
     * add new faculty
     * @param faculty
     * @param con
     * @return true if faculty was added
     * @throws FacultyDAOException
     */
    boolean addFaculty(Faculty faculty, Connection con) throws FacultyDAOException;

    /**
     * delete faculty by ID
     * @param id
     * @param con
     * @return true if faculty was deleted
     * @throws FacultyDAOException
     */
    boolean deleteFacultyByID(int id, Connection con) throws FacultyDAOException;

    /**
     * check faculty's existence by name
     * @param name
     * @param con
     * @return true if faculty exists
     * @throws FacultyDAOException
     */
    boolean checkFacultyByName(String name, Connection con) throws FacultyDAOException;

    /**
     * check faculty's existence by ID
     * @param id
     * @param con
     * @return true if faculty exists
     * @throws FacultyDAOException
     */
    boolean checkFacultyById(int id, Connection con) throws FacultyDAOException;

    /**
     * method returns faculty's full list
     * @param con
     * @return List<Faculty>
     * @throws FacultyDAOException
     */
    List<Faculty> getFacultyList(Connection con) throws FacultyDAOException;

    /**
     * method returns actual applicant list, registered for curren faculty
     * @param facultyId
     * @param con
     * @return List<Applicant>
     * @throws FacultyDAOException
     */
    List<Applicant> getApplicantsForFaculty(int facultyId, Connection con) throws FacultyDAOException;

    /**
     * method returns faculty with required ID
     * @param facultyId
     * @param con
     * @return Faculty
     * @throws FacultyDAOException
     */
    Faculty getFacultyById(int facultyId, Connection con) throws FacultyDAOException;

    /**
     * method returns faculty with required name
     * @param name
     * @param con
     * @return Faculty
     * @throws FacultyDAOException
     */
    Faculty getFacultyByName(String name, Connection con) throws FacultyDAOException;

    /**
     * method updates faculty info
     * @param faculty
     * @param con
     * @return true if faculty info was updated
     * @throws FacultyDAOException
     */
    boolean updateFaculty(Faculty faculty, Connection con) throws FacultyDAOException;

    /**
     * returns grade sum for current applicant in required faculty
     * @param userId
     * @param facultyId
     * @param con
     * @return int
     * @throws FacultyDAOException
     */
    int getSum(int userId, int facultyId, Connection con) throws FacultyDAOException;

}