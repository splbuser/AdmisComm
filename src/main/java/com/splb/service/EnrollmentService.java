package com.splb.service;

import com.splb.model.dao.constant.Fields;
import com.splb.model.dao.exception.EnrollmentDAOException;
import com.splb.model.entity.EnrollStatus;
import com.splb.model.entity.Enrollment;
import com.splb.service.exceptions.EnrollmentServiceException;
import com.splb.service.sorting.SortEnrollmentImpl;
import jakarta.servlet.http.HttpSession;

import java.util.*;

public class EnrollmentService extends Service {

    public Enrollment get(int id) throws EnrollmentServiceException {
        try {
            return edao.getApplicantEnrollStatus(id);
        } catch (EnrollmentDAOException e) {
            log.error(e.getMessage());
            throw new EnrollmentServiceException(e.getMessage());
        }
    }

    public List<Enrollment> getList() throws EnrollmentServiceException {
        try {
            return edao.getEnrollment();
        } catch (EnrollmentDAOException e) {
            log.error(e.getMessage());
            throw new EnrollmentServiceException(e.getMessage());
        }
    }

    public static EnrollStatus getStatus(int i) {
        return switch (i) {
            case 0 -> EnrollStatus.NO_ENROLLED;
            case 1 -> EnrollStatus.CONTRACT;
            case 2 -> EnrollStatus.BUDGET;
            case 3 -> EnrollStatus.NO_PARTICIPATE;
            default -> null;
        };
    }

    public List<Enrollment> getEnrollmentsForRequest(HttpSession session, String type, String sortBy)
            throws EnrollmentServiceException {

        List<Enrollment> enrollment;
        if (type == null || sortBy == null) {
            type = (String) session.getAttribute(Fields.TYPE);
            sortBy = (String) session.getAttribute(Fields.SORT_BY);
            if (type == null || sortBy == null) {

                enrollment = getList();
            } else {
                SortEnrollmentImpl se = new SortEnrollmentImpl();
                enrollment = se.getSortedList(
                        type,
                        sortBy,
                        getList());
            }
        } else {
            SortEnrollmentImpl se = new SortEnrollmentImpl();
            enrollment = se.getSortedList(
                    type,
                    sortBy,
                    getList());
            session.setAttribute(Fields.TYPE, type);
            session.setAttribute(Fields.SORT_BY, sortBy);
        }
        return enrollment;
    }
}
