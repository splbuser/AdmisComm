package com.splb.service;

import com.splb.model.dao.connection.DirectConnectionBuilder;
import com.splb.model.dao.connection.PoolConnectionBuilder;
import com.splb.model.dao.exception.ApplicantResultDAOException;
import com.splb.model.dao.exception.FacultyDAOException;
import com.splb.model.dao.exception.UserDAOException;
import com.splb.model.dao.implementation.ApplicantResultDAOImpl;
import com.splb.model.dao.implementation.FacultyDAOImpl;
import com.splb.model.entity.Applicant;
import com.splb.model.entity.Faculty;
import com.splb.service.exceptions.ApplicantResultServiceException;
import com.splb.service.exceptions.FacultyServiceException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FacultyService extends Service {

    /**
     * none parametrized constructor for ConnectionPool initialize
     */
    public FacultyService() {
        setConnectionBuilder(new PoolConnectionBuilder());
    }

    /**
     * parametrized constructor to create DirectConnection for testing
     * @param connectionBuilder
     */
    public FacultyService(DirectConnectionBuilder connectionBuilder) {
        this.connectionBuilder = connectionBuilder;
    }

    /**
     * Method takes Applicant's id, and returns list of faculty on which applicant did not register
     * @param id
     * @return List<Faculty>
     * @throws FacultyServiceException
     */
    public List<Faculty> getListForRegister(int id) throws FacultyServiceException {
        List<Faculty> faculties = new ArrayList<>();
        try (Connection con = getConnection()) {
            List<Faculty> fullList = fdao.getFacultyList(con);
            List<Faculty> extList = udao.getApplicantsFacultyList(id, con);
            for (Faculty f : fullList
            ) {
                if (!extList.contains(f) && f.getTotalPlaces() > 0) {
                    faculties.add(f);
                }
            }
        } catch (FacultyDAOException | UserDAOException | SQLException e) {
            throw new FacultyServiceException(e.getMessage());
        }
        return faculties;
    }

    /**
     * method removes blocked applicants from the list of registered applicants for a given faculty
     * @param facultyId
     * @return List<Applicant>
     * @throws FacultyDAOException
     */
    public List<Applicant> getApplicantsForStatement(int facultyId) throws FacultyServiceException {
        try (Connection con = getConnection()) {
            List<Applicant> applicants = fdao.getApplicantsForFaculty(facultyId, con);
            applicants.removeIf(Applicant::isBlockStatus);
            applicants.removeIf(a -> a.getEnrollStatus() == 1);
            applicants.removeIf(a -> a.getEnrollStatus() == 2);
            for (Applicant a : applicants) {
                int sum = ApplicantResultDAOImpl.getInstance().getResultSum(a.getId(), con);
                int sum1 = FacultyDAOImpl.getInstance().getSum(a.getId(), facultyId, con);
                a.setScore(sum + sum1);
            }
            return applicants;
        } catch (SQLException | FacultyDAOException | ApplicantResultDAOException e) {
            log.error(e.getMessage());
            throw new FacultyServiceException(e.getMessage());
        }

    }

    /**
     * method add new faculty to made by admin
     * @param faculty
     * @return true or false
     * @throws FacultyServiceException
     */
    public boolean add(Faculty faculty) throws FacultyServiceException {
        try (Connection con = getConnection()) {
            return fdao.addFaculty(faculty, con);
        } catch (FacultyDAOException | SQLException e) {
            log.error(e.getMessage());
            throw new FacultyServiceException(e.getMessage());
        }
    }

    /**
     * method delete faculty
     * @param id
     * @return true or false
     * @throws FacultyServiceException
     */
    public boolean delete(int id) throws FacultyServiceException {
        try (Connection con = getConnection()) {
            return fdao.deleteFacultyByID(id, con);
        } catch (FacultyDAOException | SQLException e) {
            log.error(e.getMessage());
            throw new FacultyServiceException(e.getMessage());
        }
    }

    /**
     * method returns faculty by its name if it exists
     * @param name
     * @return Faculty
     * @throws FacultyServiceException
     */
    public Faculty getByName(String name) throws FacultyServiceException {
        try (Connection con = getConnection()) {
            return fdao.getFacultyByName(name, con);
        } catch (FacultyDAOException | SQLException e) {
            log.error(e.getMessage());
            throw new FacultyServiceException(e.getMessage());
        }
    }

    /**
     * method returns faculty by its id if it exists
     * @param id
     * @return Faculty
     * @throws FacultyServiceException
     */
    public Faculty getById(int id) throws FacultyServiceException {
        try (Connection con = getConnection()) {
            return fdao.getFacultyById(id, con);
        } catch (FacultyDAOException | SQLException e) {
            log.error(e.getMessage());
            throw new FacultyServiceException(e.getMessage());
        }
    }


    /**
     * method check if exists faculty with required name
     * @param name
     * @return true or false
     * @throws FacultyServiceException
     */
    public boolean checkByName(String name) throws FacultyServiceException {
        try (Connection con = getConnection()) {
            return fdao.checkFacultyByName(name, con);
        } catch (FacultyDAOException | SQLException e) {
            log.error(e.getMessage());
            throw new FacultyServiceException(e.getMessage());
        }
    }

    /**
     * method updates faculty info
     * @param faculty
     * @return true or false
     * @throws FacultyServiceException
     */
    public boolean update(Faculty faculty) throws FacultyServiceException {
        try (Connection con = getConnection()) {
            return fdao.updateFaculty(faculty, con);
        } catch (FacultyDAOException | SQLException e) {
            log.error(e.getMessage());
            throw new FacultyServiceException(e.getMessage());
        }
    }

    /**
     * method returns current faculty list
     * @return List<Faculty>
     * @throws FacultyServiceException
     */
    public List<Faculty> getList() throws FacultyServiceException {
        try (Connection con = getConnection()) {
            return fdao.getFacultyList(con);
        } catch (FacultyDAOException | SQLException e) {
            log.error(e.getMessage());
            throw new FacultyServiceException(e.getMessage());
        }
    }
}
