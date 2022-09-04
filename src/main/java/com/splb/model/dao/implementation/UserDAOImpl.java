package com.splb.model.dao.implementation;

import com.splb.model.dao.AbstractDAO;
import com.splb.model.dao.UserDAO;
import com.splb.model.dao.constant.Fields;
import com.splb.model.dao.constant.SQLQuery;
import com.splb.model.dao.exception.*;
import com.splb.model.entity.Applicant;
import com.splb.model.entity.Faculty;
import com.splb.service.sorting.SortFacultyImpl;
import org.apache.logging.log4j.LogManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDAOImpl extends AbstractDAO implements UserDAO {

    private static UserDAOImpl userDAOImpl = null;

    private UserDAOImpl() {
        log = LogManager.getLogger(getClass().getName());
    }

    public static synchronized UserDAOImpl getInstance() {
        if (userDAOImpl == null) {
            userDAOImpl = new UserDAOImpl();
        }
        return userDAOImpl;
    }


    @Override
    public boolean addApplicant(Applicant applicant, Connection con) throws UserDAOException {
        if (findApplicantByName(applicant.getUserName(), con)) {
            return false;
        }
        try (PreparedStatement ps = con.prepareStatement(SQLQuery.CREATE_USER)) {
            int i = 1;
            ps.setString(i++, applicant.getUserName());
            ps.setString(i++, applicant.getPassword());
            ps.setString(i++, applicant.getFirstName());
            ps.setString(i++, applicant.getLastName());
            ps.setString(i++, applicant.getEmail());
            ps.setString(i++, applicant.getCity());
            ps.setString(i++, applicant.getRegion());
            ps.setString(i++, applicant.getEducationalInstitution());
            ps.execute();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new UserDAOException("could not add new applicant: " + e.getMessage());
        }
        return true;
    }

    @Override
    public boolean delete(String userName, Connection con) throws UserDAOException {
        try (PreparedStatement ps = con.prepareStatement(SQLQuery.DELETE_USER)) {
            ps.setString(1, userName);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new UserDAOException("could not add new applicant: " + e.getMessage());
        }
    }

    @Override
    public boolean updateEnrollStatus(Applicant applicant, Connection con) throws UserDAOException {
        try (PreparedStatement ps = con.prepareStatement(SQLQuery.UPD_USER_ENROLL_STATUS)
        ) {
            ps.setInt(1, applicant.getEnrollStatus());
            ps.setInt(2, applicant.getId());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new UserDAOException("could not update enroll status: " + e.getMessage());
        }
    }

    @Override
    public Applicant getUser(String username, String password, Connection con) throws UserDAOException {
        Applicant user = null;
        try (PreparedStatement ps = con.prepareStatement(SQLQuery.GET_USER_BY_LOGIN);
        ) {
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                user = new Applicant();
                user.setId(rs.getInt(Fields.ID));
                user.setUserName(rs.getString(Fields.APPLICANT_USER_NAME));
                user.setBlockStatus(rs.getBoolean(Fields.APPLICANT_BLOCK_STATUS));
                user.setAdminStatus(rs.getBoolean(Fields.APPLICANT_ADMIN_STATUS));
                user.setFirstName(rs.getString(Fields.APPLICANT_FIRST_NAME));
                user.setLastName(rs.getString(Fields.APPLICANT_LAST_NAME));
                user.setUploaded(rs.getString(Fields.APPLICANT_UPLOAD_STATUS));
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new UserDAOException("could not get user: " + e.getMessage());
        }
        return user;
    }

    @Override
    public boolean findApplicantByName(String login, Connection con) throws UserDAOException {
        try (PreparedStatement ps = con.prepareStatement(SQLQuery.FIND_APPLICANT_BY_USERNAME)
        ) {
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new UserDAOException("could not find applicant: " + e.getMessage());
        }
    }

    @Override
    public boolean checkApplicant(String name, Connection con) throws UserDAOException {
        try (PreparedStatement ps = con.prepareStatement(SQLQuery.SEARCH_APPLICANT);
        ) {
            ps.setString(1, name);
            ps.setString(2, name);
            ps.setString(3, name);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new UserDAOException("could not check applicant: " + e.getMessage());
        }
    }

    @Override
    public List<Applicant> findAllApplicants(int limit, int offset, Connection con) throws UserDAOException {
        List<Applicant> applicants = new ArrayList<>();
        ApplicantResultDAOImpl dao = ApplicantResultDAOImpl.getInstance();
        try (PreparedStatement ps = con.prepareStatement(SQLQuery.FIND_ALL_APPLICANTS);
        ) {
            ps.setInt(1, limit);
            ps.setInt(2, offset);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Applicant applicant = new Applicant();
                applicant.setId(rs.getInt(Fields.ID));
                applicant.setUserName(rs.getString(Fields.APPLICANT_USER_NAME));
                applicant.setFirstName(rs.getString(Fields.APPLICANT_FIRST_NAME));
                applicant.setLastName(rs.getString(Fields.APPLICANT_LAST_NAME));
                applicant.setEmail(rs.getString(Fields.APPLICANT_EMAIL));
                applicant.setCity(rs.getString(Fields.APPLICANT_CITY));
                applicant.setRegion(rs.getString(Fields.APPLICANT_REGION));
                applicant.setEducationalInstitution(rs.getString(Fields.APPLICANT_EDUC_INST));
                applicant.setBlockStatus(rs.getBoolean(Fields.APPLICANT_BLOCK_STATUS));
                applicant.setEnrollStatus(rs.getInt(Fields.APPLICANT_ENROLL_STATUS));
                applicant.setUploaded(rs.getString(Fields.APPLICANT_UPLOAD_STATUS));
                applicant.setScore(dao.getResultSum(rs.getInt(Fields.ID), con));
                applicants.add(applicant);
            }
        } catch (SQLException | ApplicantResultDAOException e) {
            log.error(e.getMessage());
            throw new UserDAOException("could not get applicant's list: " + e.getMessage());
        }
        return applicants;
    }

    @Override
    public Applicant getApplicantById(int applicantId, Connection con) throws UserDAOException {
        Applicant applicant = null;
        try (PreparedStatement ps = con.prepareStatement(SQLQuery.FIND_APPLICANT_BT_ID)
        ) {
            ps.setInt(1, applicantId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                applicant = new Applicant();
                applicant.setId(rs.getInt(Fields.ID));
                applicant.setUserName(rs.getString(Fields.APPLICANT_USER_NAME));
                applicant.setAdminStatus(rs.getBoolean(Fields.APPLICANT_ADMIN_STATUS));
                applicant.setFirstName(rs.getString(Fields.APPLICANT_FIRST_NAME));
                applicant.setLastName(rs.getString(Fields.APPLICANT_LAST_NAME));
                applicant.setEmail(rs.getString(Fields.APPLICANT_EMAIL));
                applicant.setCity(rs.getString(Fields.APPLICANT_CITY));
                applicant.setRegion(rs.getString(Fields.APPLICANT_REGION));
                applicant.setEducationalInstitution(rs.getString(Fields.APPLICANT_EDUC_INST));
                applicant.setBlockStatus(rs.getBoolean(Fields.APPLICANT_BLOCK_STATUS));
                applicant.setEnrollStatus(rs.getInt(Fields.APPLICANT_ENROLL_STATUS));
                applicant.setUploaded(rs.getString(Fields.APPLICANT_UPLOAD_STATUS));
                applicant.setList(StatementDAOImpl.getInstance()
                        .getFacultyFromStatementForApplicant(rs.getInt(1), con));
            }
        } catch (SQLException | StatementDAOException e) {
            log.error(e.getMessage());
            throw new UserDAOException("could not find applicant by ID: " + e.getMessage());
        }
        return applicant;
    }

    @Override
    public List<Applicant> getApplicantForSearch(String name, Connection con) throws UserDAOException {
        List<Applicant> getApplicant = new ArrayList<>();
        try (PreparedStatement ps = con.prepareStatement(SQLQuery.SEARCH_APPLICANT);
        ) {
            ps.setString(1, name);
            ps.setString(2, name);
            ps.setString(3, name);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Applicant applicant = new Applicant();
                applicant.setId(rs.getInt(1));
                applicant.setUserName(rs.getString(2));
                applicant.setAdminStatus(rs.getBoolean(4));
                applicant.setFirstName(rs.getString(5));
                applicant.setLastName(rs.getString(6));
                applicant.setEmail(rs.getString(7));
                applicant.setCity(rs.getString(8));
                applicant.setRegion(rs.getString(9));
                applicant.setEducationalInstitution(rs.getString(10));
                applicant.setBlockStatus(rs.getBoolean(11));
                applicant.setEnrollStatus(rs.getInt(12));
                applicant.setUploaded(rs.getString(13));
                getApplicant.add(applicant);
            }
            return getApplicant;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new UserDAOException("could not find applicant: " + e.getMessage());
        }
    }

    @Override
    public List<Applicant> getNotEnrollApplicants(Connection con) throws UserDAOException {
        List<Applicant> list = new ArrayList<>();
        try (Statement s = con.createStatement()) {
            ResultSet rs = s.executeQuery(SQLQuery.GET_NOT_ENROLLMENT);
            while (rs.next()) {
                Applicant a = getApplicantById(rs.getInt(Fields.ID), con);
                list.add(a);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new UserDAOException("could not get list: " + e.getMessage());
        }
        return list;
    }

    @Override
    public boolean blockUserById(int userId, Connection con) throws UserDAOException {
        if (!isBlockedUserCheck(userId, con)) {
            try (PreparedStatement ps = con.prepareStatement(SQLQuery.BLOCK_USER_BY_ID)
            ) {
                ps.setInt(1, 1);
                ps.setInt(2, userId);
                ps.executeUpdate();
                return true;
            } catch (SQLException e) {
                log.error(e.getMessage());
                throw new UserDAOException("could not block user: " + e.getMessage());
            }
        }
        return false;
    }

    @Override
    public boolean unblockUserById(int userId, Connection con) throws UserDAOException {
        if (isBlockedUserCheck(userId, con)) {
            try (PreparedStatement ps = con.prepareStatement(SQLQuery.BLOCK_USER_BY_ID)
            ) {
                ps.setInt(1, 0);
                ps.setInt(2, userId);
                ps.executeUpdate();
                return true;
            } catch (SQLException e) {
                log.error(e.getMessage());
                throw new UserDAOException("could not unblock user: " + e.getMessage());
            }
        }
        return false;
    }

    @Override
    public boolean isBlockedUserCheck(int userId, Connection con) throws UserDAOException {
        try (PreparedStatement ps = con.prepareStatement(SQLQuery.BLOCK_CHECK_USER)
        ) {
            ps.setInt(1, userId);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                int resp = resultSet.getInt(1);
                return resp == 1;
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new UserDAOException("could not check user's block status: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean upload(int userId, String filename, Connection con) throws UserDAOException {
        try (PreparedStatement ps = con.prepareStatement(SQLQuery.APP_UPLOADED)
        ) {
            ps.setString(1, filename);
            ps.setInt(2, userId);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new UserDAOException("could not update upload status: " + e.getMessage());
        }
    }


    @Override
    public List<Faculty> getApplicantsFacultyList(int id, Connection con) throws UserDAOException {
        List<Faculty> list = new ArrayList<>();
        SortFacultyImpl sortedList = new SortFacultyImpl();
        try (PreparedStatement ps = con.prepareStatement(SQLQuery.APPLICANT_HAS_FACULTY)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Faculty faculty = FacultyDAOImpl.getInstance()
                        .getFacultyById(rs.getInt(2), con);
                list.add(faculty);
            }
        } catch (SQLException | FacultyDAOException e) {
            log.error(e.getMessage());
            throw new UserDAOException("could not get list: " + e.getMessage());
        }
        return sortedList.getSortedList("DSC", "byBudget", list);
    }

    @Override
    public int[] getApplicantsFacultyResult(int userId, int facultyID, Connection con) throws UserDAOException {
        int[] list = new int[2];
        try (PreparedStatement ps = con.prepareStatement(SQLQuery.GET_APPL_FACULTY_RES)) {
            ps.setInt(1, userId);
            ps.setInt(2, facultyID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                list[0] = rs.getInt(Fields.FIRST_SUBJ_RESULT);
                list[1] = rs.getInt("second_subj_result");
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new UserDAOException("could not get applicant's result: " + e.getMessage());
        }
        return list;
    }

    @Override
    public int getLength(Connection con) throws UserDAOException {
        int length = 0;
        try (Statement st = con.createStatement();
        ) {
            ResultSet rs = st.executeQuery(SQLQuery.SELECT_FROM_APPLICANT);
            while (rs.next()) {
                length++;
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new UserDAOException("could not get applicant's list length: " + e.getMessage());
        }
        return length;
    }
}