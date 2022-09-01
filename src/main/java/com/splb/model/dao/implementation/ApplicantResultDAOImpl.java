package com.splb.model.dao.implementation;

import com.splb.model.dao.AbstractDAO;
import com.splb.model.dao.ApplicantResultDAO;
import com.splb.model.dao.connection.PoolConnectionBuilder;
import com.splb.model.dao.constant.SQLQuery;
import com.splb.model.dao.exception.ApplicantResultDAOException;
import com.splb.model.entity.ApplicantResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ApplicantResultDAOImpl extends AbstractDAO implements ApplicantResultDAO {

    private static ApplicantResultDAOImpl applicantResultDAOImpl = null;

    private ApplicantResultDAOImpl() {
        setConnectionBuilder(new PoolConnectionBuilder());
        log = LogManager.getLogger(getClass().getName());
    }

    public static synchronized ApplicantResultDAOImpl getInstance() {
        if (applicantResultDAOImpl == null) {
            applicantResultDAOImpl = new ApplicantResultDAOImpl();
        }
        return applicantResultDAOImpl;
    }

    @Override
    public boolean insert(ApplicantResult applicantResult) throws ApplicantResultDAOException {

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(SQLQuery.INSERT_RESULT)
        ) {
            int i = 1;
            ps.setInt(i++, applicantResult.getUserId());
            ps.setInt(i++, applicantResult.getAlgebra());
            ps.setInt(i++, applicantResult.getBiology());
            ps.setInt(i++, applicantResult.getChemistry());
            ps.setInt(i++, applicantResult.getEnglish());
            ps.setInt(i++, applicantResult.getLiterature());
            ps.setInt(i++, applicantResult.getWorldHistory());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new ApplicantResultDAOException("could not add Applicant's result: " + e.getMessage());
        }
    }

    @Override
    public ApplicantResult getApplicantResult(int userId) throws ApplicantResultDAOException {
        ApplicantResult ar = new ApplicantResult();

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(SQLQuery.GET_RESULT_FOR_APPLICANT)
        ) {
            ps.setInt(1, userId);
            ps.execute();
            ResultSet resultSet = ps.getResultSet();
            while (resultSet.next()) {
                int i = 2;
                ar.setAlgebra(resultSet.getInt(i++));
                ar.setBiology(resultSet.getInt(i++));
                ar.setChemistry(resultSet.getInt(i++));
                ar.setEnglish(resultSet.getInt(i++));
                ar.setLiterature(resultSet.getInt(i++));
                ar.setWorldHistory(resultSet.getInt(i++));
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new ApplicantResultDAOException("could not add get Applicant's result: " + e.getMessage());
        }
        return ar;
    }

    @Override
    public int getResultSum(int userId) throws ApplicantResultDAOException {
        int sum = 0;
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(SQLQuery.GET_APPL_SUM)
        ) {
            ps.setInt(1, userId);
            ps.execute();
            ResultSet resultSet = ps.getResultSet();
            while (resultSet.next()) {
                sum = resultSet.getInt("sum");
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new ApplicantResultDAOException("could not get required sum: " + e.getMessage());
        }
        return sum;
    }

    @Override
    public boolean isSubmittedResult(int userId) throws ApplicantResultDAOException {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(SQLQuery.GET_RESULT_FOR_APPLICANT);
        ) {
            ps.setInt(1, userId);
            ps.execute();
            ResultSet resultSet = ps.getResultSet();
            return resultSet.next();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new ApplicantResultDAOException("could not add check submission of Applicant's result: " + e.getMessage());
        }
    }

    @Override
    public boolean addFacultySubjectResult(HttpServletRequest req, HttpServletResponse resp,
                                           int userId, int subjOne, int subjTwo, int facultyId) throws ApplicantResultDAOException {

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.
                     prepareStatement(SQLQuery.REGISTER_FOR_SINGLE_FACULTY)) {
            int i = 1;
            ps.setInt(i++, userId);
            ps.setInt(i++, facultyId);
            ps.setInt(i++, subjOne);
            ps.setInt(i++, subjTwo);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new ApplicantResultDAOException("could not add result for faculty's subject: " + e.getMessage());
        }

    }

    @Override
    public boolean deleteResults(Connection conn, int userId) throws ApplicantResultDAOException {
        try (PreparedStatement ps = conn.prepareStatement(SQLQuery.DELETE_APPL_FACULTY_REGS);) {
            ps.setInt(1, userId);
            ps.execute();
            return true;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new ApplicantResultDAOException("could not delete results: " + e.getMessage());
        }
    }

}
