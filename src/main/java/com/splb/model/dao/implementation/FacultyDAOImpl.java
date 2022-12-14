package com.splb.model.dao.implementation;

import com.splb.model.dao.AbstractDAO;
import com.splb.model.dao.FacultyDAO;
import com.splb.model.dao.UserDAO;
import com.splb.model.dao.constant.Fields;
import com.splb.model.dao.constant.SQLQuery;
import com.splb.model.dao.exception.FacultyDAOException;
import com.splb.model.dao.exception.UserDAOException;
import com.splb.model.entity.Applicant;
import com.splb.model.entity.Faculty;
import org.apache.logging.log4j.LogManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FacultyDAOImpl extends AbstractDAO implements FacultyDAO {

    private static FacultyDAOImpl facultyDAOImpl = null;

    private FacultyDAOImpl() {
        log = LogManager.getLogger(getClass().getName());
    }

    public static synchronized FacultyDAOImpl getInstance() {
        if (facultyDAOImpl == null) {
            facultyDAOImpl = new FacultyDAOImpl();
        }
        return facultyDAOImpl;
    }

    @Override
    public boolean addFaculty(Faculty faculty, Connection con) throws FacultyDAOException {
        try (PreparedStatement ps = con.prepareStatement(SQLQuery.CREATE_FACULTY);
        ) {
            ps.setString(1, faculty.getName());
            ps.setInt(2, faculty.getBudgetPlaces());
            ps.setInt(3, faculty.getTotalPlaces());
            ps.setString(4, faculty.getSubjOne());
            ps.setString(5, faculty.getSubjTwo());
            ps.execute();
            return true;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new FacultyDAOException("could not add faculty: " + e.getMessage());
        }
    }


    @Override
    public boolean deleteFacultyByID(int id, Connection con) throws FacultyDAOException {
        if (!checkFacultyById(id, con)) {
            return false;
        } else {
            try (PreparedStatement ps = con.prepareStatement(SQLQuery.DELETE_FACULTY_BY_ID);
            ) {
                ps.setInt(1, id);
                ps.execute();
                return true;
            } catch (SQLException e) {
                log.error(e.getMessage());
                throw new FacultyDAOException("could not delete faculty: " + e.getMessage());
            }
        }
    }

    @Override
    public boolean checkFacultyByName(String name, Connection con) throws FacultyDAOException {
        try (PreparedStatement ps = con.prepareStatement(SQLQuery.FIND_FACULTY_BY_NAME);
        ) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            return (rs.next());
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new FacultyDAOException("could not check faculty by name: " + e.getMessage());
        }
    }

    @Override
    public boolean checkFacultyById(int id, Connection con) throws FacultyDAOException {
        try (PreparedStatement ps = con.prepareStatement(SQLQuery.FIND_FACULTY_BY_ID);
        ) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return (rs.next());
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new FacultyDAOException("could not check faculty by ID: " + e.getMessage());
        }
    }

    @Override
    public List<Faculty> getFacultyList(Connection con) throws FacultyDAOException {
        List<Faculty> faculties = new ArrayList<>();
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(SQLQuery.FIND_ALL_FACULTY);
        ) {
            while (rs.next()) {
                Faculty faculty = new Faculty();
                faculty.setId(rs.getInt(Fields.ID));
                faculty.setName(rs.getString(Fields.FACULTY_NAME));
                faculty.setBudgetPlaces(rs.getInt(Fields.FACULTY_BUDGET_PLACES));
                faculty.setTotalPlaces(rs.getInt(Fields.FACULTY_TOTAL_PLACES));
                faculty.setSubjOne(rs.getString(Fields.SUBJECT_ONE));
                faculty.setSubjTwo(rs.getString(Fields.SUBJECT_TWO));
                faculties.add(faculty);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new FacultyDAOException("could not get faculty list: " + e.getMessage());
        }
        return faculties;
    }


    // ?????????? ???????????????????? ???????????? ??????????????????????, ???????????????????????????? ???? ???????????? ??????????????????
    @Override
    public List<Applicant> getApplicantsForFaculty(int facultyId, Connection con) throws FacultyDAOException {
        List<Applicant> applicants = new ArrayList<>();
        UserDAO udao = UserDAOImpl.getInstance();
        try (PreparedStatement ps = con.prepareStatement(SQLQuery.GET_APP_FOR_FACULTY);
        ) {
            ps.setInt(1, facultyId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                applicants.add(udao.getApplicantById(rs.getInt(1), con));
            }
        } catch (SQLException | UserDAOException e) {
            log.error(e.getMessage());
            throw new FacultyDAOException("could not get faculty's applicant list: " + e.getMessage());
        }
        return applicants;
    }

    @Override
    public Faculty getFacultyById(int facultyId, Connection con) throws FacultyDAOException {
        Faculty faculty = new Faculty();
        try (PreparedStatement ps = con.prepareStatement(SQLQuery.FIND_FACULTY_BY_ID);
        ) {
            ps.setInt(1, facultyId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                faculty.setId(rs.getInt(1));
                faculty.setName(rs.getString(2));
                faculty.setBudgetPlaces(rs.getInt(3));
                faculty.setTotalPlaces(rs.getInt(4));
                faculty.setSubjOne(rs.getString(5));
                faculty.setSubjTwo(rs.getString(6));
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new FacultyDAOException("could not get faculty: " + e.getMessage());
        }
        return faculty;
    }

    @Override
    public Faculty getFacultyByName(String name, Connection con) throws FacultyDAOException {
        Faculty faculty = new Faculty();
        try (PreparedStatement ps = con.prepareStatement(SQLQuery.FIND_FACULTY_BY_NAME);
        ) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                faculty.setId(rs.getInt(1));
                faculty.setName(rs.getString(2));
                faculty.setBudgetPlaces(rs.getInt(3));
                faculty.setTotalPlaces(rs.getInt(4));
                faculty.setSubjOne(rs.getString(5));
                faculty.setSubjTwo(rs.getString(6));
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new FacultyDAOException("could not get faculty: " + e.getMessage());
        }
        return faculty;
    }

    @Override
    public boolean updateFaculty(Faculty faculty, Connection con) throws FacultyDAOException {
        try (PreparedStatement ps = con.prepareStatement(SQLQuery.UPDATE_FACULTY)
        ) {
            ps.setString(1, faculty.getName());
            ps.setInt(2, faculty.getBudgetPlaces());
            ps.setInt(3, faculty.getTotalPlaces());
            ps.setString(4, faculty.getSubjOne());
            ps.setString(5, faculty.getSubjTwo());
            ps.setInt(6, Math.toIntExact(faculty.getId()));
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new FacultyDAOException("could not update faculty: " + e.getMessage());
        }
    }

    // ?????????? ???????????????????? ?????????? ???????????? ?????? ?????????????? ???????????????????? ???? ???????????? ????????????????????
    @Override
    public int getSum(int userId, int facultyId, Connection con) throws FacultyDAOException {
        int sum = 0;
        try (PreparedStatement ps = con.prepareStatement(SQLQuery.GET_RESUL_SUM)) {
            ps.setInt(1, userId);
            ps.setInt(2, facultyId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                sum = rs.getInt(Fields.TOTAL_SCORE);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new FacultyDAOException("could not get applicant's result sum for current faculty: " + e.getMessage());
        }
        return sum;
    }
}