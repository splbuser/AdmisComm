package com.splb.service;

import com.splb.model.dao.exception.DAOException;
import com.splb.model.dao.exception.StatementDAOException;

import com.splb.model.entity.Applicant;
import com.splb.model.entity.Faculty;
import com.splb.model.entity.Statement;
import com.splb.model.entity.StatementResult;
import com.splb.service.exceptions.StatementServiceException;

import java.util.List;

public class StatementService extends Service {


    public boolean add(int facultyId, int userId) throws StatementServiceException {
        try {
            return sdao.addUserToFaculty(facultyId, userId);
        } catch (StatementDAOException e) {
            log.error(e.getMessage());
            throw new StatementServiceException(e.getMessage());
        }
    }

    public void addEmAll(int facultyId) throws StatementServiceException {
        try {
            List<Applicant> list = fdao.getApplicantsForFaculty(facultyId);
            for (Applicant a : list
            ) {
                int userId = a.getId();
                if (!sdao.checkUserFaculty(facultyId, userId)) {
                    sdao.addUserToFaculty(facultyId, userId);
                }
            }
        } catch (DAOException e) {
            log.error(e.getMessage());
            throw new StatementServiceException(e.getMessage());
        }
    }

    public boolean remove(int facultyId, int userId) throws StatementServiceException {
        try {
            return sdao.removeUserFromFaculty(facultyId, userId);
        } catch (StatementDAOException e) {
            log.error(e.getMessage());
            throw new StatementServiceException(e.getMessage());
        }
    }

    public boolean check(int facultyId, int userId) throws StatementServiceException {
        try {
            return sdao.checkUserFaculty(facultyId, userId);
        } catch (StatementDAOException e) {
            log.error(e.getMessage());
            throw new StatementServiceException(e.getMessage());
        }
    }

    public List<StatementResult> get(int id) throws StatementServiceException {
        try {
            return sdao.getStatementResult(id);
        } catch (StatementDAOException e) {
            log.error(e.getMessage());
            throw new StatementServiceException(e.getMessage());
        }
    }

    public List<Statement> getList() throws StatementServiceException {
        try {
            return sdao.getStatementList();
        } catch (StatementDAOException e) {
            log.error(e.getMessage());
            throw new StatementServiceException(e.getMessage());
        }
    }

    public List<Faculty> getApplicantsList(int applicantId) throws StatementServiceException {
        try {
            return sdao.getFacultyFromStatementForApplicant(applicantId);
        } catch (StatementDAOException e) {
            log.error(e.getMessage());
            throw new StatementServiceException(e.getMessage());
        }
    }

    public void finalizeStatement() throws StatementServiceException {
        List<Statement> statementList;
        Applicant applicant;

        try {
            statementList = sdao.getStatementList();
        } catch (StatementDAOException e) {
            log.error(e.getMessage());
            throw new StatementServiceException(e.getMessage());
        }

        try {
            for (Statement s : statementList
            ) {
                int userId = s.getApplicant().getId();
                applicant = udao.getApplicantById(userId);

                List<Faculty> faculties = getApplicantsList(userId);

                for (Faculty f : faculties
                ) {
                    int facultyId = f.getId();
                    int status = udao.getApplicantById(userId).getEnrollStatus();
                    int budget = fdao.getFacultyById(facultyId).getBudgetPlaces();
                    int total = fdao.getFacultyById(facultyId).getTotalPlaces();

                    if (budget > 0 && status == 3) {
                        applicant.setEnrollStatus(2);
                        udao.updateEnrollStatus(applicant);

                        edao.add(facultyId, userId, 2);

                        log.info("{} applicant added to {} faculty for BUDGET",
                                applicant.getLastName(), s.getFaculty().getName());

                        budget--;
                        total--;
                        f.setBudgetPlaces(budget);
                        f.setTotalPlaces(total);
                        fdao.updateFaculty(f);

                    } else if (budget == 0 && total > 0 && status == 3) {

                        applicant.setEnrollStatus(1);
                        udao.updateEnrollStatus(applicant);
                        edao.add(facultyId, userId, 1);

                        log.info("{} applicant added to {} faculty for CONTRACT",
                                s.getApplicant().getLastName(), s.getFaculty().getName());
                        total--;
                        f.setTotalPlaces(total);
                        fdao.updateFaculty(f);

                    } else if (budget == 0 && total == 0 && status == 3) {
                        log.info("{} applicant no enrolled", s.getApplicant().getLastName());
                        applicant.setEnrollStatus(0);
                        udao.updateEnrollStatus(applicant);
                    }
                }
            }
        } catch (DAOException e) {
            log.error(e.getMessage());
            throw new StatementServiceException(e.getMessage());
        }
    }
}