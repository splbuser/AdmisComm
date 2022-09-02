package com.splb.service;

import com.splb.model.dao.connection.PoolConnectionBuilder;
import com.splb.model.dao.exception.DAOException;
import com.splb.model.dao.exception.StatementDAOException;

import com.splb.model.entity.Applicant;
import com.splb.model.entity.Faculty;
import com.splb.model.entity.Statement;
import com.splb.model.entity.StatementResult;
import com.splb.service.exceptions.StatementServiceException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class StatementService extends Service {

    public StatementService() {
        setConnectionBuilder(new PoolConnectionBuilder());
    }

    public boolean add(int facultyId, int userId) throws StatementServiceException {
        try (Connection con = getConnection()) {
            return sdao.addUserToFaculty(facultyId, userId, con);
        } catch (StatementDAOException | SQLException e) {
            log.error(e.getMessage());
            throw new StatementServiceException(e.getMessage());
        }
    }

    public void addEmAll(int facultyId) throws StatementServiceException {
        try (Connection con = getConnection()) {
            con.setAutoCommit(false);
            try {
                List<Applicant> list = fdao.getApplicantsForFaculty(facultyId, con);
                for (Applicant a : list
                ) {
                    int userId = a.getId();
                    if (!sdao.checkUserFaculty(facultyId, userId, con)) {
                        sdao.addUserToFaculty(facultyId, userId, con);
                    }
                }
                con.commit();
            } catch (DAOException | SQLException e) {
                con.rollback();
                throw new StatementServiceException(e.getMessage());
            } finally {
                con.setAutoCommit(true);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new StatementServiceException(e.getMessage());
        }
    }

    public boolean remove(int facultyId, int userId) throws StatementServiceException {
        try (Connection con = getConnection()) {
            return sdao.removeUserFromFaculty(facultyId, userId, con);
        } catch (StatementDAOException | SQLException e) {
            log.error(e.getMessage());
            throw new StatementServiceException(e.getMessage());
        }
    }

    public boolean check(int facultyId, int userId) throws StatementServiceException {
        try (Connection con = getConnection()) {
            return sdao.checkUserFaculty(facultyId, userId, con);
        } catch (StatementDAOException | SQLException e) {
            log.error(e.getMessage());
            throw new StatementServiceException(e.getMessage());
        }
    }

    public List<StatementResult> get(int id) throws StatementServiceException {
        try (Connection con = getConnection()) {
            return sdao.getStatementResult(id, con);
        } catch (StatementDAOException | SQLException e) {
            log.error(e.getMessage());
            throw new StatementServiceException(e.getMessage());
        }
    }

    public List<Statement> getList() throws StatementServiceException {
        try (Connection con = getConnection()) {
            return sdao.getStatementList(con);
        } catch (StatementDAOException | SQLException e) {
            log.error(e.getMessage());
            throw new StatementServiceException(e.getMessage());
        }
    }

    public List<Faculty> getApplicantsList(int applicantId) throws StatementServiceException {
        try (Connection con = getConnection()) {
            return sdao.getFacultyFromStatementForApplicant(applicantId, con);
        } catch (StatementDAOException | SQLException e) {
            log.error(e.getMessage());
            throw new StatementServiceException(e.getMessage());
        }
    }

    public void finalizeStatement() throws StatementServiceException {
        List<Statement> statementList;
        Applicant applicant;
        try (Connection con = getConnection()) {
            con.setAutoCommit(false);
            try {
                statementList = sdao.getStatementList(con);
                for (Statement s : statementList
                ) {
                    int userId = s.getApplicant().getId();
                    applicant = udao.getApplicantById(userId, con);
                    List<Faculty> faculties = getApplicantsList(userId);
                    for (Faculty f : faculties
                    ) {
                        int facultyId = f.getId();
                        int status = udao.getApplicantById(userId, con).getEnrollStatus();
                        int budget = fdao.getFacultyById(facultyId, con).getBudgetPlaces();
                        int total = fdao.getFacultyById(facultyId, con).getTotalPlaces();
                        if (budget > 0 && status == 3) {
                            applicant.setEnrollStatus(2);
                            udao.updateEnrollStatus(applicant, con);
                            edao.add(facultyId, userId, 2, con);
                            log.info("{} applicant added to {} faculty for BUDGET",
                                    applicant.getLastName(), s.getFaculty().getName());
                            budget--;
                            total--;
                            f.setBudgetPlaces(budget);
                            f.setTotalPlaces(total);
                            fdao.updateFaculty(f, con);
                        } else if (budget == 0 && total > 0 && status == 3) {
                            applicant.setEnrollStatus(1);
                            udao.updateEnrollStatus(applicant, con);
                            edao.add(facultyId, userId, 1, con);
                            log.info("{} applicant added to {} faculty for CONTRACT",
                                    s.getApplicant().getLastName(), s.getFaculty().getName());
                            total--;
                            f.setTotalPlaces(total);
                            fdao.updateFaculty(f, con);
                        } else if (budget == 0 && total == 0 && status == 3) {
                            log.info("{} applicant no enrolled", s.getApplicant().getLastName());
                            applicant.setEnrollStatus(0);
                            udao.updateEnrollStatus(applicant, con);
                        }
                    }
                }
                con.commit();
            } catch (DAOException e) {
                con.rollback();
                throw new StatementServiceException(e.getMessage());
            } finally {
                con.setAutoCommit(true);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new StatementServiceException(e.getMessage());
        }
    }
}