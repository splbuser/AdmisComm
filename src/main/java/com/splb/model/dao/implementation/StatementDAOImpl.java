package com.splb.model.dao.implementation;

import com.splb.model.dao.*;
import com.splb.model.dao.constant.Fields;
import com.splb.model.dao.constant.SQLQuery;
import com.splb.model.dao.exception.*;
import com.splb.model.entity.Applicant;
import com.splb.model.entity.Faculty;
import com.splb.model.entity.StatementResult;
import com.splb.service.sorting.SortFacultyImpl;
import org.apache.logging.log4j.LogManager;

import java.sql.*;
import java.util.*;

public class StatementDAOImpl extends AbstractDAO implements StatementDAO {

    private static StatementDAOImpl statementDAOImpl = null;

    private StatementDAOImpl() {
        log = LogManager.getLogger(getClass().getName());
    }

    public static synchronized StatementDAOImpl getInstance() {
        if (statementDAOImpl == null) {
            statementDAOImpl = new StatementDAOImpl();
        }
        return statementDAOImpl;
    }

    @Override
    public boolean addUserToFaculty(int facultyId, int userId, Connection con) throws StatementDAOException {
        ApplicantResultDAO adao = ApplicantResultDAOImpl.getInstance();
        FacultyDAO fdao = FacultyDAOImpl.getInstance();
        try (PreparedStatement ps = con.prepareStatement(SQLQuery.ADD_USER_TO_STATEMENT)
        ) {
            int totalScore = adao.getResultSum(userId, con) + fdao.getSum(userId, facultyId, con);
            ps.setInt(1, facultyId);
            ps.setInt(2, userId);
            ps.setInt(3, totalScore);
            return ps.executeUpdate() == 1;
        } catch (SQLException | DAOException e) {
            log.error(e.getMessage());
            throw new StatementDAOException("could not add user to faculty: " + e.getMessage());
        }
    }

    @Override
    public boolean removeUserFromFaculty(int facultyId, int userId, Connection con) throws StatementDAOException {
        try (PreparedStatement ps = con.prepareStatement(SQLQuery.REMOVE_USER_FROM_STATEMENT);
        ) {
            ps.setInt(1, facultyId);
            ps.setInt(2, userId);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new StatementDAOException("could not remove user from faculty: " + e.getMessage());
        }
    }

    @Override
    public boolean checkUserFaculty(int facultyId, int userId, Connection con) throws StatementDAOException {
        try (PreparedStatement ps = con.prepareStatement(SQLQuery.FIND_USER_IN_STATEMENT);
        ) {
            ps.setInt(1, facultyId);
            ps.setInt(2, userId);
            ResultSet resultSet = ps.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new StatementDAOException("could not check user's faculty in statement: " + e.getMessage());
        }
    }

    @Override
    public List<com.splb.model.entity.Statement> getStatementList(Connection con) throws StatementDAOException {
        List<com.splb.model.entity.Statement> list = new ArrayList<>();
        FacultyDAOImpl fdao = FacultyDAOImpl.getInstance();
        UserDAOImpl udao = UserDAOImpl.getInstance();
        Faculty faculty;
        Applicant applicant;
        int totalScore;
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(SQLQuery.GET_STATEMENT_LIST)
        ) {
            while (rs.next()) {
                faculty = fdao.getFacultyById((rs.getInt(Fields.FACULTY__ID)), con);
                applicant = udao.getApplicantById((rs.getInt(Fields.APPLICANT_ID)), con);
                totalScore = rs.getInt(Fields.TOTAL_SCORE);
                if (!applicant.isBlockStatus() && applicant.getEnrollStatus() == 3) {
                    com.splb.model.entity.Statement statement = new
                            com.splb.model.entity.Statement(faculty, applicant, totalScore);
                    list.add(statement);
                }
            }
            return list;
        } catch (SQLException | DAOException e) {
            throw new StatementDAOException("could not get current statement list: " + e.getMessage());
        }
    }

    @Override
    public List<Applicant> getFacultysApplicantsFromStatement(int facultyId, Connection con) throws StatementDAOException {
        List<Applicant> list = new ArrayList<>();
        UserDAO udao = UserDAOImpl.getInstance();
        try (PreparedStatement ps = con.prepareStatement(SQLQuery.GET_APPL_FROM_STMNT_BT_FCLTY);
        ) {
            ps.setInt(1, facultyId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Applicant applicant = udao.getApplicantById(rs.getInt(2), con);
                list.add(applicant);
            }
        } catch (SQLException | UserDAOException e) {
            log.error(e.getMessage());
            throw new StatementDAOException("could not get list from statement: " + e.getMessage());
        }
        return list;
    }

    @Override
    public List<Faculty> getFacultyFromStatementForApplicant(int applicantId, Connection con) throws StatementDAOException {
        List<Faculty> list = new ArrayList<>();
        SortFacultyImpl sortedList = new SortFacultyImpl();
        FacultyDAO fdao = FacultyDAOImpl.getInstance();
        try (
                PreparedStatement ps = con.prepareStatement(SQLQuery.SELECT_FROM_STATEMENT_APP);
        ) {
            ps.setInt(1, applicantId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(fdao.getFacultyById(rs.getInt(1), con));
            }
        } catch (SQLException | FacultyDAOException e) {
            log.error(e.getMessage());
            throw new StatementDAOException("could not get applicant's faculty list: " + e.getMessage());
        }
        return sortedList.getSortedList("DSC", "byBudget", list);
    }

    @Override
    public List<StatementResult> getStatementResult(int userid, Connection con) throws StatementDAOException {
        List<StatementResult> list = new ArrayList<>();
        FacultyDAO fdao = FacultyDAOImpl.getInstance();
        UserDAO udao = UserDAOImpl.getInstance();
        try (PreparedStatement ps = con.prepareStatement(SQLQuery.SELECT_FROM_STATEMENT_APP)) {
            ps.setInt(1, userid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                StatementResult sr = new StatementResult();
                Faculty f = fdao.getFacultyById(rs.getInt(1), con);
                int[] result = udao.getApplicantsFacultyResult(userid, rs.getInt(1), con);
                sr.setFaculty(f);
                sr.setApplicant(udao.getApplicantById(userid, con));
                sr.setFirstSubject(result[0]);
                sr.setSecondSubject(result[1]);
                list.add(sr);
            }
        } catch (SQLException | DAOException e) {
            log.error(e.getMessage());
            throw new StatementDAOException("could not get result" + e.getMessage());
        }
        return list;
    }
}