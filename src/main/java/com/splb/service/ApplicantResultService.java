package com.splb.service;

import com.splb.model.dao.exception.ApplicantResultDAOException;
import com.splb.model.entity.ApplicantResult;
import com.splb.service.exceptions.ApplicantResultServiceException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ApplicantResultService extends Service {

    public boolean insert(ApplicantResult applicantResult)
            throws ApplicantResultServiceException {
        try {
            return adao.insert(applicantResult);
        } catch (ApplicantResultDAOException e) {
            log.error(e.getMessage());
            throw new ApplicantResultServiceException(e.getMessage());
        }
    }

    public boolean addFResult(HttpServletRequest req, HttpServletResponse resp,
                              int userId, int subjOne, int subjTwo, int facultyId) throws ApplicantResultServiceException {
        try {
            return adao.addFacultySubjectResult(req, resp, userId, subjOne, subjTwo, facultyId);
        } catch (ApplicantResultDAOException e) {
            log.error(e.getMessage());
            throw new ApplicantResultServiceException(e.getMessage());
        }
    }

    public ApplicantResult get(int id) throws ApplicantResultServiceException {
        try {
            return adao.getApplicantResult(id);
        } catch (ApplicantResultDAOException e) {
            log.error(e.getMessage());
            throw new ApplicantResultServiceException(e.getMessage());
        }
    }

    public boolean checkSub(int id)
            throws ApplicantResultServiceException {
        try {
            return adao.isSubmittedResult(id);
        } catch (ApplicantResultDAOException e) {
            log.error(e.getMessage());
            throw new ApplicantResultServiceException(e.getMessage());
        }
    }


}
