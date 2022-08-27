package com.splb.service;

import com.splb.model.dao.ApplicantResultDAO;
import com.splb.model.dao.exception.ApplicantResultDAOException;
import com.splb.model.dao.implementation.ApplicantResultDAOImpl;
import com.splb.model.entity.ApplicantResult;
import com.splb.service.exceptions.ApplicantResultServiceException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ApplicantResultService extends Service {

    ApplicantResultDAO dao = ApplicantResultDAOImpl.getInstance();

    public boolean insert(ApplicantResult applicantResult)
            throws ApplicantResultServiceException {
        try {
            return dao.insert(applicantResult);
        } catch (ApplicantResultDAOException e) {
            log.error(e.getMessage());
            throw new ApplicantResultServiceException(e.getMessage());
        }
    }

    public boolean addFResult(HttpServletRequest req, HttpServletResponse resp,
                              int userId, int subjOne, int subjTwo, int facultyId) throws ApplicantResultServiceException {
        try {
            return dao.addFacultySubjectResult(req, resp, userId, subjOne, subjTwo, facultyId);
        } catch (ApplicantResultDAOException e) {
            log.error(e.getMessage());
            throw new ApplicantResultServiceException(e.getMessage());
        }
    }

    public ApplicantResult get(int id) throws ApplicantResultServiceException {
        try {
            return dao.getApplicantResult(id);
        } catch (ApplicantResultDAOException e) {
            log.error(e.getMessage());
            throw new ApplicantResultServiceException(e.getMessage());
        }
    }

    public boolean checkSub(int id)
            throws ApplicantResultServiceException {
        try {
            return dao.isSubmittedResult(id);
        } catch (ApplicantResultDAOException e) {
            log.error(e.getMessage());
            throw new ApplicantResultServiceException(e.getMessage());
        }
    }


}
