package com.splb.service;

import com.splb.model.dao.FacultyDAO;
import com.splb.model.dao.UserDAO;
import com.splb.model.dao.exception.FacultyDAOException;
import com.splb.model.dao.exception.UserDAOException;
import com.splb.model.dao.implementation.FacultyDAOImpl;
import com.splb.model.dao.implementation.UserDAOImpl;
import com.splb.model.entity.Applicant;
import com.splb.model.entity.Faculty;
import com.splb.service.exceptions.FacultyServiceException;

import java.util.ArrayList;
import java.util.List;

public class FacultyService extends Service {
    private final FacultyDAO dao = FacultyDAOImpl.getInstance();
    private final UserDAO udao = UserDAOImpl.getInstance();

    /**
     * Method takes Applicant's id, and returns list of faculty on which applicant did not register
     */

    public List<Faculty> getListForRegister(int id) throws FacultyServiceException {
        List<Faculty> faculties = new ArrayList<>();
        try {
            List<Faculty> fullList = dao.getFacultyList();
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

    // метод удаляет из метод списока аппликантов, рарегестировах на данный факультет, заблокированых пользователей
    public static List<Applicant> getApplicantsForStatement(int facultyId) throws FacultyDAOException {
        List<Applicant> applicants = FacultyDAOImpl.getInstance().getApplicantsForFaculty(facultyId);
        applicants.removeIf(Applicant::isBlockStatus);
        return applicants;
    }

    public boolean add(Faculty faculty) throws FacultyServiceException {
        try {
            return dao.addFaculty(faculty);
        } catch (FacultyDAOException e) {
            log.error(e.getMessage());
            throw new FacultyServiceException(e.getMessage());
        }
    }

    public boolean delete(int id) throws FacultyServiceException {
        try {
            return dao.deleteFacultyByID(id);
        } catch (FacultyDAOException e) {
            log.error(e.getMessage());
            throw new FacultyServiceException(e.getMessage());
        }
    }

    public Faculty getByName(String name) throws FacultyServiceException{
        try {
            return dao.getFacultyByName(name);
        } catch (FacultyDAOException e) {
            log.error(e.getMessage());
            throw new FacultyServiceException(e.getMessage());
        }
    }

    public Faculty getById(int id) throws FacultyServiceException{
        try {
            return dao.getFacultyById(id);
        } catch (FacultyDAOException e) {
            log.error(e.getMessage());
            throw new FacultyServiceException(e.getMessage());
        }
    }


    public boolean checkByName(String name) throws FacultyServiceException{
        try {
            return dao.checkFacultyByName(name);
        } catch (FacultyDAOException e) {
            log.error(e.getMessage());
            throw new FacultyServiceException(e.getMessage());
        }
    }

    public boolean checkById(int id) throws FacultyServiceException{
        try {
            return dao.checkFacultyById(id);
        } catch (FacultyDAOException e) {
            log.error(e.getMessage());
            throw new FacultyServiceException(e.getMessage());
        }
    }

    public boolean update(Faculty faculty) throws FacultyServiceException{
        try {
            return dao.updateFaculty(faculty);
        } catch (FacultyDAOException e) {
            log.error(e.getMessage());
            throw new FacultyServiceException(e.getMessage());
        }
    }


    public List<Faculty> getList() throws FacultyServiceException{
        try {
            return dao.getFacultyList();
        } catch (FacultyDAOException e) {
            log.error(e.getMessage());
            throw new FacultyServiceException(e.getMessage());
        }
    }

}
