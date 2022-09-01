package com.splb.model.dao;

import com.splb.model.dao.exception.FacultyDAOException;
import com.splb.model.entity.Applicant;
import com.splb.model.entity.Faculty;

import java.util.List;

public interface FacultyDAO {

    boolean addFaculty(Faculty faculty) throws FacultyDAOException;

    boolean deleteFacultyByID(int id) throws FacultyDAOException;

    boolean checkFacultyByName(String name) throws FacultyDAOException;

    boolean checkFacultyById(int id) throws FacultyDAOException;
    List<Faculty> getFacultyList() throws FacultyDAOException;

    List<Applicant> getApplicantsForFaculty(int facultyId) throws FacultyDAOException;

    Faculty getFacultyById(int facultyId) throws FacultyDAOException;

    Faculty getFacultyByName(String name) throws FacultyDAOException;

    boolean updateFaculty(Faculty faculty) throws FacultyDAOException;

    int getSum(int userId, int facultyId) throws FacultyDAOException;

}