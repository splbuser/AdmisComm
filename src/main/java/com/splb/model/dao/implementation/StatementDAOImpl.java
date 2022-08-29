package com.splb.model.dao.implementation;

import com.splb.model.dao.AbstractDAO;
import com.splb.model.dao.StatementDAO;
import com.splb.model.dao.connection.DirectConnectionBuilder;
import com.splb.model.dao.connection.PoolConnectionBuilder;
import com.splb.model.dao.constant.Fields;
import com.splb.model.dao.constant.SQLQuery;
import com.splb.model.dao.exception.*;
import com.splb.model.entity.Applicant;
import com.splb.model.entity.ApplicantResult;
import com.splb.model.entity.Faculty;
import com.splb.model.entity.StatementResult;
import org.apache.logging.log4j.LogManager;

import java.sql.*;
import java.util.*;

public class StatementDAOImpl extends AbstractDAO implements StatementDAO {

    private static StatementDAOImpl statementDAOImpl = null;

    private StatementDAOImpl() {
        setConnectionBuilder(new PoolConnectionBuilder());
        log = LogManager.getLogger(getClass().getName());
    }

    public static synchronized StatementDAOImpl getInstance() {
        if (statementDAOImpl == null) {
            statementDAOImpl = new StatementDAOImpl();
        }
        return statementDAOImpl;
    }

    @Override
    public boolean addUserToFaculty(int facultyId, int userId) throws StatementDAOException {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(SQLQuery.ADD_USER_TO_STATEMENT);
        ) {
            ps.setInt(1, facultyId);
            ps.setInt(2, userId);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new StatementDAOException("could not add user to faculty: " + e.getMessage());
        }
    }

    @Override
    public boolean removeUserFromFaculty(int facultyId, int userId) throws StatementDAOException {

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(SQLQuery.REMOVE_USER_FROM_STATEMENT);
        ) {
            ps.setInt(1, facultyId);
            ps.setInt(2, userId);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new StatementDAOException("could not remove user from faculty: " + e.getMessage());
        }

    }


    @Override
    public boolean checkUserFaculty(int facultyId, int userId) throws StatementDAOException {

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(SQLQuery.FIND_USER_IN_STATEMENT);
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
    public List<com.splb.model.entity.Statement> getStatementList() throws StatementDAOException {

        List<com.splb.model.entity.Statement> list = new ArrayList<>();
        FacultyDAOImpl fdao = FacultyDAOImpl.getInstance();
        ApplicantResultDAOImpl adao = ApplicantResultDAOImpl.getInstance();
        UserDAOImpl udao = UserDAOImpl.getInstance();
        Faculty faculty;
        Applicant applicant;

        try (Connection connection = getConnection();
             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(SQLQuery.GET_STATEMENT_LIST);
        ) {
            while (rs.next()) {
                faculty = fdao.getFacultyById((rs.getInt(Fields.FACULTY__ID)));
                applicant = udao.getApplicantById((rs.getInt(Fields.APPLICANT_ID)));
                int certificate = adao.getApplicantResult((rs.getInt(Fields.APPLICANT_ID))).sum();
                int facultyResult = fdao.getSum((rs.getInt(Fields.APPLICANT_ID)), (rs.getInt(Fields.FACULTY__ID)));

                if (!applicant.isBlockStatus()) {
                    com.splb.model.entity.Statement statement = new
                            com.splb.model.entity.Statement(faculty, applicant, certificate + facultyResult);
                    list.add(statement);
                }
            }
            return list;
        } catch (SQLException | DAOException e) {
            throw new StatementDAOException("could not get current statement list: " + e.getMessage());
        }
    }

    // Метод вернет список апликантов по заданому id факультата, чьи результаты были добавлены в стейтмент
    @Override
    public List<Applicant> getFacultysApplicantsFromStatement(int facultyId) throws StatementDAOException {
        List<Applicant> list = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(SQLQuery.GET_APPL_FROM_STMNT_BT_FCLTY);
        ) {
            ps.setInt(1, facultyId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Applicant applicant = UserDAOImpl.getInstance().getApplicantById(rs.getInt(2));
                list.add(applicant);
            }
        } catch (SQLException | UserDAOException e) {
            log.error(e.getMessage());
            throw new StatementDAOException("could not get list from statement: " + e.getMessage());
        }
        return list;
    }

    // возвращает для applicant список факультетов из Statement
    @Override
    public List<Faculty> getFacultyFromStatementForApplicant(int applicantId) throws StatementDAOException {

        List<Faculty> list = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(SQLQuery.SELECT_FROM_STATEMENT_APP);

        ) {
            ps.setInt(1, applicantId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(FacultyDAOImpl.getInstance().getFacultyById(rs.getInt(1)));
            }
        } catch (SQLException | FacultyDAOException e) {
            log.error(e.getMessage());
            throw new StatementDAOException("could not get applicant's faculty list: " + e.getMessage());
        }
        return list;
    }

    // метод вернет список результатов аппликнта, которые добавленые в стейтмент
    @Override
    public List<StatementResult> getStatementResult(int userid) throws StatementDAOException {
        List<StatementResult> list = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(SQLQuery.SELECT_FROM_STATEMENT_APP)) {
            ps.setInt(1, userid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                StatementResult sr = new StatementResult();
                Faculty f = FacultyDAOImpl.getInstance().getFacultyById(rs.getInt(1));
                int[] result = UserDAOImpl.getInstance().getApplicantsFacultyResult(userid, rs.getInt(1));

                sr.setFaculty(f);
                sr.setApplicant(UserDAOImpl.getInstance().getApplicantById(userid));
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