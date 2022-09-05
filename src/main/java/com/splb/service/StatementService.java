package com.splb.service;

import com.splb.model.dao.connection.DirectConnectionBuilder;
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

    /**
     * none parametrized constructor for ConnectionPool initialize
     */
    public StatementService() {
        setConnectionBuilder(new PoolConnectionBuilder());
    }

    /**
     * parametrized constructor to create DirectConnection for testing
     * @param connectionBuilder
     */
    public StatementService(DirectConnectionBuilder connectionBuilder) {
        this.connectionBuilder = connectionBuilder;
    }

    /**
     * adds the result of the applicant's registration to the faculty
     *
     * @param facultyId
     * @param userId
     * @return return true if user was added
     * @throws StatementServiceException
     */
    public boolean add(int facultyId, int userId) throws StatementServiceException {
        try (Connection con = getConnection()) {
            return sdao.addUserToFaculty(facultyId, userId, con);
        } catch (StatementDAOException | SQLException e) {
            log.error(e.getMessage());
            throw new StatementServiceException(e.getMessage());
        }
    }

    /**
     * adds the results of all registered applicants to the faculty
     *
     * @param facultyId
     * @throws StatementServiceException
     */
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

    /**
     * remove applicants result from statement
     *
     * @param facultyId
     * @param userId
     * @return return true if user was removed
     * @throws StatementServiceException
     */
    public boolean remove(int facultyId, int userId) throws StatementServiceException {
        try (Connection con = getConnection()) {
            return sdao.removeUserFromFaculty(facultyId, userId, con);
        } catch (StatementDAOException | SQLException e) {
            log.error(e.getMessage());
            throw new StatementServiceException(e.getMessage());
        }
    }

    /**
     * checks the applicant's registration for the faculty in question
     *
     * @param facultyId
     * @param userId
     * @return return true if result was added
     * @throws StatementServiceException
     */
    public boolean check(int facultyId, int userId) throws StatementServiceException {
        try (Connection con = getConnection()) {
            return sdao.checkUserFaculty(facultyId, userId, con);
        } catch (StatementDAOException | SQLException e) {
            log.error(e.getMessage());
            throw new StatementServiceException(e.getMessage());
        }
    }

    /**
     * returns the list of results of the applicant that were added to the statement
     *
     * @param id
     * @return List<StatementResult>
     * @throws StatementServiceException
     */
    public List<StatementResult> get(int id) throws StatementServiceException {
        try (Connection con = getConnection()) {
            return sdao.getStatementResult(id, con);
        } catch (StatementDAOException | SQLException e) {
            log.error(e.getMessage());
            throw new StatementServiceException(e.getMessage());
        }
    }

    /**
     * returns the list of results of the applicant that were added to the statement
     *
     * @return List<Statement>
     * @throws StatementServiceException
     */
    public List<Statement> getList() throws StatementServiceException {
        try (Connection con = getConnection()) {
            return sdao.getStatementList(con);
        } catch (StatementDAOException | SQLException e) {
            log.error(e.getMessage());
            throw new StatementServiceException(e.getMessage());
        }
    }

    /**
     * returns for applicants a list of faculties from Statement
     *
     * @param applicantId
     * @return List<Faculty>
     * @throws StatementServiceException
     */
    public List<Faculty> getApplicantsList(int applicantId) throws StatementServiceException {
        try (Connection con = getConnection()) {
            return sdao.getFacultyFromStatementForApplicant(applicantId, con);
        } catch (StatementDAOException | SQLException e) {
            log.error(e.getMessage());
            throw new StatementServiceException(e.getMessage());
        }
    }

    /**
     * finalization of statement and calculating users result for enrollment
     *
     * @throws StatementServiceException
     */
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
                    int counter = 0;
                    for (Faculty f : faculties
                    ) {
                        counter++;
                        int facultyId = f.getId();
                        int status = udao.getApplicantById(userId, con).getEnrollStatus();
                        int budget = fdao.getFacultyById(facultyId, con).getBudgetPlaces();
                        int total = fdao.getFacultyById(facultyId, con).getTotalPlaces();
                        if (budget > 0 && total > 0 && status == 3) {
                            applicant.setEnrollStatus(2);
                            udao.updateEnrollStatus(applicant, con);
                            edao.add(facultyId, userId, 2, con);
                            log.info("{} added to {} faculty for BUDGET",
                                    applicant.getLastName(), s.getFaculty().getName());
                            f.setBudgetPlaces(--budget);
                            f.setTotalPlaces(--total);
                            fdao.updateFaculty(f, con);
                        } else if (budget == 0 && total > 0 && status == 3) {
                            applicant.setEnrollStatus(1);
                            udao.updateEnrollStatus(applicant, con);
                            edao.add(facultyId, userId, 1, con);
                            log.info("{} added to {} faculty for CONTRACT",
                                    s.getApplicant().getLastName(), s.getFaculty().getName());
                            f.setTotalPlaces(--total);
                            f.setBudgetPlaces(0);
                            fdao.updateFaculty(f, con);
                        } else if (budget == 0 && total == 0 && status == 3
                                && counter == faculties.size()) {
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