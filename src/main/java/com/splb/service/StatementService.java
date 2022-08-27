package com.splb.service;

import com.splb.model.dao.EnrollmentDAO;
import com.splb.model.dao.StatementDAO;
import com.splb.model.dao.exception.DAOException;
import com.splb.model.dao.exception.StatementDAOException;
import com.splb.model.dao.implementation.EnrollmentDAOImpl;
import com.splb.model.dao.implementation.FacultyDAOImpl;
import com.splb.model.dao.implementation.StatementDAOImpl;
import com.splb.model.dao.implementation.UserDAOImpl;
import com.splb.model.entity.Applicant;
import com.splb.model.entity.Faculty;
import com.splb.model.entity.Statement;
import com.splb.model.entity.StatementResult;
import com.splb.service.exceptions.StatementServiceException;
import com.splb.service.sorting.SortStatementImpl;

import java.util.List;

public class StatementService extends Service {

    StatementDAO dao = StatementDAOImpl.getInstance();
    EnrollmentDAO edao = EnrollmentDAOImpl.getInstance();

    public void finalizeStatement() throws StatementServiceException {

        SortStatementImpl list = new SortStatementImpl();

        try {
            List<Statement> statementList =
                    list.getSortedList("byTotalScore", "DSC", dao.getStatementList());
            Applicant applicant;
            Faculty faculty;
            UserDAOImpl udao = UserDAOImpl.getInstance();
            FacultyDAOImpl fdao = FacultyDAOImpl.getInstance();
            FacultyDAOImpl facultyDAO = FacultyDAOImpl.getInstance();

            for (Statement s : statementList
            ) {
                applicant = udao.getApplicantById(s.getApplicant().getId());
                faculty = fdao.getFacultyById(s.getFaculty().getId());

                int userId = applicant.getId();
                int facultyId = faculty.getId();
                int budget = faculty.getBudgetPlaces();
                int total = faculty.getTotalPlaces();

                if (budget > 0) {
                    if (applicant.getEnrollStatus() == 0) {
                        edao.add(facultyId, userId, 2);

                        log.info("{} applicant added to {} faculty for BUDGET", s.getApplicant().getLastName(),
                                s.getFaculty().getName());
                        budget--;
                        total--;
                        faculty.setBudgetPlaces(budget);
                        faculty.setTotalPlaces(total);
                        facultyDAO.updateFaculty(faculty);
                    }
                } else if (budget == 0 && total > 0) {
                    if (applicant.getEnrollStatus() == 0) {
                        edao.add(facultyId, userId, 1);

                        log.info("{} applicant added to {} faculty for CONTRACT",
                                s.getApplicant().getLastName(), s.getFaculty().getName());
                        total--;
                        faculty.setTotalPlaces(total);
                        facultyDAO.updateFaculty(faculty);
                    }
                } else if (budget == 0 && total == 0) {
                    applicant.setEnrollStatus(0);
                }
            }
        } catch (DAOException e) {
            log.error(e.getMessage());
            throw new StatementServiceException(e.getMessage());
        }
    }

    public boolean add(int facultyId, int userId) throws StatementServiceException {
        try {
            return dao.addUserToFaculty(facultyId, userId);
        } catch (StatementDAOException e) {
            log.error(e.getMessage());
            throw new StatementServiceException(e.getMessage());
        }
    }

    public boolean remove(int facultyId, int userId) throws StatementServiceException {
        try {
            return dao.removeUserFromFaculty(facultyId, userId);
        } catch (StatementDAOException e) {
            log.error(e.getMessage());
            throw new StatementServiceException(e.getMessage());
        }
    }

    public boolean check(int facultyId, int userId) throws StatementServiceException {
        try {
            return dao.checkUserFaculty(facultyId, userId);
        } catch (StatementDAOException e) {
            log.error(e.getMessage());
            throw new StatementServiceException(e.getMessage());
        }
    }

    public List<StatementResult> get(int id) throws StatementServiceException {
        try {
            return dao.getStatementResult(id);
        } catch (StatementDAOException e) {
            log.error(e.getMessage());
            throw new StatementServiceException(e.getMessage());
        }
    }


    public List<Statement> getList() throws StatementServiceException {
        try {
            return dao.getStatementList();
        } catch (StatementDAOException e) {
            log.error(e.getMessage());
            throw new StatementServiceException(e.getMessage());
        }
    }

}
