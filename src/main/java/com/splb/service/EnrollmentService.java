package com.splb.service;

import com.splb.model.dao.EnrollmentDAO;
import com.splb.model.dao.exception.EnrollmentDAOException;
import com.splb.model.dao.implementation.EnrollmentDAOImpl;
import com.splb.model.entity.EnrollStatus;
import com.splb.model.entity.Enrollment;
import com.splb.service.exceptions.EnrollmentServiceException;

import java.util.List;

public class EnrollmentService extends Service {

    EnrollmentDAO dao = EnrollmentDAOImpl.getInstance();
    public static EnrollStatus getStatus(int i) {
        if (i == 0) {
            return EnrollStatus.NO_ENROLLED;
        }
        return i == 1 ?
                EnrollStatus.CONTRACT : EnrollStatus.BUDGET;
    }

    public List<Enrollment> getList() throws EnrollmentServiceException {
        try {
            return dao.getEnrollment();
        } catch (EnrollmentDAOException e) {
            log.error(e.getMessage());
            throw new EnrollmentServiceException(e.getMessage());
        }
    }

    public Enrollment get(int id) throws EnrollmentServiceException{
        try {
            return dao.getApplicantEnrollStatus(id);
        } catch (EnrollmentDAOException e) {
            log.error(e.getMessage());
            throw new EnrollmentServiceException(e.getMessage());
        }
    }
}
