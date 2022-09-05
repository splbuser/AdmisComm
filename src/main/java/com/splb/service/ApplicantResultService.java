package com.splb.service;

import com.splb.model.dao.connection.DirectConnectionBuilder;
import com.splb.model.dao.connection.PoolConnectionBuilder;
import com.splb.model.dao.exception.ApplicantResultDAOException;
import com.splb.model.entity.ApplicantResult;
import com.splb.service.exceptions.ApplicantResultServiceException;

import java.sql.Connection;
import java.sql.SQLException;

public class ApplicantResultService extends Service {

    public ApplicantResultService() {
        setConnectionBuilder(new PoolConnectionBuilder());
    }
    public ApplicantResultService(DirectConnectionBuilder connectionBuilder) {
        this.connectionBuilder = connectionBuilder;
    }

    public boolean insert(ApplicantResult applicantResult)
            throws ApplicantResultServiceException {
        try (Connection con = getConnection()) {
            return adao.insert(applicantResult, con);
        } catch (ApplicantResultDAOException | SQLException e) {
            log.error(e.getMessage());
            throw new ApplicantResultServiceException(e.getMessage());
        }
    }

    public boolean addFResult(int userId, int subjOne, int subjTwo, int facultyId)
            throws ApplicantResultServiceException {
        try (Connection con = getConnection()) {
            return adao.addFacultySubjectResult(userId, subjOne, subjTwo, facultyId, con);
        } catch (ApplicantResultDAOException | SQLException e) {
            log.error(e.getMessage());
            throw new ApplicantResultServiceException(e.getMessage());
        }
    }

    public ApplicantResult get(int id) throws ApplicantResultServiceException {
        try (Connection con = getConnection()) {
            return adao.getApplicantResult(id, con);
        } catch (ApplicantResultDAOException | SQLException e) {
            log.error(e.getMessage());
            throw new ApplicantResultServiceException(e.getMessage());
        }
    }

    public boolean checkSub(int id)
            throws ApplicantResultServiceException {
        try (Connection con = getConnection()) {
            return adao.isSubmittedResult(id, con);
        } catch (ApplicantResultDAOException | SQLException e) {
            log.error(e.getMessage());
            throw new ApplicantResultServiceException(e.getMessage());
        }
    }
}
