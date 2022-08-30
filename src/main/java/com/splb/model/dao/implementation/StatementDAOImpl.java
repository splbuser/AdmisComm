package com.splb.model.dao.implementation;

import com.splb.model.dao.AbstractDAO;
import com.splb.model.dao.ApplicantResultDAO;
import com.splb.model.dao.FacultyDAO;
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
import com.splb.service.sorting.Sort;
import com.splb.service.sorting.SortFacultyImpl;
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
        ApplicantResultDAO adao = ApplicantResultDAOImpl.getInstance();
        FacultyDAO fdao = FacultyDAOImpl.getInstance();


        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(SQLQuery.ADD_USER_TO_STATEMENT)
        ) {
            int certificate = adao.getApplicantResult(userId).sum();
            int facultyResult = fdao.getSum(userId, facultyId);

            ps.setInt(1, facultyId);
            ps.setInt(2, userId);
            ps.setInt(3, certificate + facultyResult);

            return ps.executeUpdate() == 1;
        } catch (SQLException | DAOException e) {
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
            return ps.executeUpdate() == 1;
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
        UserDAOImpl udao = UserDAOImpl.getInstance();
        Faculty faculty;
        Applicant applicant;
        int totalScore;

        try (Connection connection = getConnection();
             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(SQLQuery.GET_STATEMENT_LIST);
        ) {
            while (rs.next()) {
                faculty = fdao.getFacultyById((rs.getInt(Fields.FACULTY__ID)));
                applicant = udao.getApplicantById((rs.getInt(Fields.APPLICANT_ID)));
                totalScore = rs.getInt("total_score");

                if (!applicant.isBlockStatus()) {
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

    public Map<Faculty, TreeSet<Applicant>> getFinalizeList() throws StatementDAOException {
        FacultyDAO fdao = FacultyDAOImpl.getInstance();

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQLQuery.GET_STATEMENT_LIST);

            Map<Faculty, TreeSet<Applicant>> applicants = new HashMap<>();
            Faculty faculty;
            Applicant applicant;
            while (resultSet.next()) {
                faculty = new Faculty();
                faculty.setId(resultSet.getInt(Fields.FACULTY__ID));
                faculty.setTotalPlaces(fdao.getFacultyById(resultSet.getInt(Fields.FACULTY__ID)).getTotalPlaces());

                applicant = new Applicant();
                applicant.setId(resultSet.getInt(Fields.APPLICANT_ID));

                if (applicants.containsKey(faculty)) {
                    applicants.get(faculty).add(applicant);
                } else {
                    applicants.put(faculty, new TreeSet<>());
                    applicants.get(faculty).add(applicant);
                }
            }
            return applicants;
        } catch (SQLException | FacultyDAOException e) {
            log.error(e.getMessage());
            throw new StatementDAOException("could not get current enrollment applicants: " + e.getMessage());
        }
    }


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

    @Override
    public List<Faculty> getFacultyFromStatementForApplicant(int applicantId) throws StatementDAOException {
        List<Faculty> list = new ArrayList<>();
        SortFacultyImpl sortedList = new SortFacultyImpl();

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
        return sortedList.getSortedList("DSC", "byBudget", list);
    }

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