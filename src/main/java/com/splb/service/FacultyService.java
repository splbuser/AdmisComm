package com.splb.service;

import com.splb.model.dao.exception.FacultyDAOException;
import com.splb.model.dao.exception.UserDAOException;
import com.splb.model.dao.implementation.FacultyDAOImpl;
import com.splb.model.entity.Applicant;
import com.splb.model.entity.Faculty;
import com.splb.service.exceptions.FacultyServiceException;

import java.util.ArrayList;
import java.util.List;

public class FacultyService extends Service {

    /**
     * Method takes Applicant's id, and returns list of faculty on which applicant did not register
     * @param id
     * @return
     * @throws FacultyServiceException
     */
    public List<Faculty> getListForRegister(int id) throws FacultyServiceException {
        List<Faculty> faculties = new ArrayList<>();
        try {
            List<Faculty> fullList = fdao.getFacultyList();
            List<Faculty> extList = udao.getApplicantsFacultyList(id);
            for (Faculty f : fullList
            ) {
                if (!extList.contains(f)) {
                    faculties.add(f);
                }
            }
        } catch (FacultyDAOException | UserDAOException e) {
            throw new FacultyServiceException(e.getMessage());
        }
        return faculties;
    }

    /**
     * method removes blocked applicants from the list of registered applicants for a given faculty
     * @param facultyId
     * @return
     * @throws FacultyDAOException
     */
    public static List<Applicant> getApplicantsForStatement(int facultyId) throws FacultyDAOException {
        List<Applicant> applicants = FacultyDAOImpl.getInstance().getApplicantsForFaculty(facultyId);
        applicants.removeIf(Applicant::isBlockStatus);
        return applicants;
    }

    public boolean add(Faculty faculty) throws FacultyServiceException {
        try {
            return fdao.addFaculty(faculty);
        } catch (FacultyDAOException e) {
            log.error(e.getMessage());
            throw new FacultyServiceException(e.getMessage());
        }
    }

    public boolean delete(int id) throws FacultyServiceException {
        try {
            return fdao.deleteFacultyByID(id);
        } catch (FacultyDAOException e) {
            log.error(e.getMessage());
            throw new FacultyServiceException(e.getMessage());
        }
    }

    public Faculty getByName(String name) throws FacultyServiceException{
        try {
            return fdao.getFacultyByName(name);
        } catch (FacultyDAOException e) {
            log.error(e.getMessage());
            throw new FacultyServiceException(e.getMessage());
        }
    }

    public Faculty getById(int id) throws FacultyServiceException{
        try {
            return fdao.getFacultyById(id);
        } catch (FacultyDAOException e) {
            log.error(e.getMessage());
            throw new FacultyServiceException(e.getMessage());
        }
    }


    public boolean checkByName(String name) throws FacultyServiceException{
        try {
            return fdao.checkFacultyByName(name);
        } catch (FacultyDAOException e) {
            log.error(e.getMessage());
            throw new FacultyServiceException(e.getMessage());
        }
    }

    public boolean checkById(int id) throws FacultyServiceException{
        try {
            return fdao.checkFacultyById(id);
        } catch (FacultyDAOException e) {
            log.error(e.getMessage());
            throw new FacultyServiceException(e.getMessage());
        }
    }

    public boolean update(Faculty faculty) throws FacultyServiceException{
        try {
            return fdao.updateFaculty(faculty);
        } catch (FacultyDAOException e) {
            log.error(e.getMessage());
            throw new FacultyServiceException(e.getMessage());
        }
    }


    public List<Faculty> getList() throws FacultyServiceException{
        try {
            return fdao.getFacultyList();
        } catch (FacultyDAOException e) {
            log.error(e.getMessage());
            throw new FacultyServiceException(e.getMessage());
        }
    }

}
