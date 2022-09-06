package com.splb.service;

import com.splb.model.dao.connection.DirectConnectionBuilder;
import com.splb.model.dao.connection.PoolConnectionBuilder;
import com.splb.model.dao.constant.Fields;
import com.splb.model.dao.exception.EnrollmentDAOException;
import com.splb.model.entity.EnrollStatus;
import com.splb.model.entity.Enrollment;
import com.splb.service.exceptions.EnrollmentServiceException;
import com.splb.service.sorting.Sort;
import com.splb.service.sorting.SortEnrollmentImpl;
import com.splb.service.utils.filecreator.FileCreate;
import com.splb.service.utils.filecreator.exceptions.FileCreateException;
import com.splb.service.utils.filecreator.factory.FileFactory;
import com.splb.service.utils.filecreator.factory.FileType;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class EnrollmentService extends Service {

    /**
     * none parametrized constructor for ConnectionPool initialize
     */
    public EnrollmentService() {
        setConnectionBuilder(new PoolConnectionBuilder());
    }

    /**
     * parametrized constructor to create DirectConnection for testing
     * @param connectionBuilder
     */
    public EnrollmentService(DirectConnectionBuilder connectionBuilder) {
        this.connectionBuilder = connectionBuilder;
    }

    public Enrollment get(int id) throws EnrollmentServiceException {
        try (Connection con = getConnection()) {
            return edao.getApplicantEnrollStatus(id, con);
        } catch (EnrollmentDAOException | SQLException e) {
            log.error(e.getMessage());
            throw new EnrollmentServiceException(e.getMessage());
        }
    }

    /**
     * returns full Enrollment list
     * @return List<Enrollment>
     * @throws EnrollmentServiceException
     */
    public List<Enrollment> getList() throws EnrollmentServiceException {
        try (Connection con = getConnection()) {
            return edao.getEnrollment(con);
        } catch (EnrollmentDAOException | SQLException e) {
            log.error(e.getMessage());
            throw new EnrollmentServiceException(e.getMessage());
        }
    }

    /**
     * method returns applicant Enroll Status on demand
     */
    public static EnrollStatus getStatus(int i) {
        return switch (i) {
            case 0 -> EnrollStatus.NO_ENROLLED;
            case 1 -> EnrollStatus.CONTRACT;
            case 2 -> EnrollStatus.BUDGET;
            case 3 -> EnrollStatus.NO_PARTICIPATE;
            default -> null;
        };
    }

    /**
     * method for sorting enrolment list for displaying
     * @param session
     * @param type
     * @param sortBy
     * @return List<Enrollment>
     * @throws EnrollmentServiceException
     */
    public List<Enrollment> getEnrollmentsForRequest(HttpSession session, String type, String sortBy)
            throws EnrollmentServiceException {
        List<Enrollment> enrollment;
        Sort<Enrollment> se = new SortEnrollmentImpl();
        if (type == null || sortBy == null) {
            type = (String) session.getAttribute(Fields.TYPE);
            sortBy = (String) session.getAttribute(Fields.SORT_BY);
            if (type == null || sortBy == null) {
                enrollment = getList();
            } else {
                enrollment = se.getSortedList(type, sortBy, getList());
            }
        } else {
            enrollment = se.getSortedList(type, sortBy, getList());
            session.setAttribute(Fields.TYPE, type);
            session.setAttribute(Fields.SORT_BY, sortBy);
        }
        return enrollment;
    }

    /**
     * method generates a report whose format depends on the query
     * @param response
     * @param fileType
     * @throws EnrollmentServiceException
     */
    public void getReport(HttpServletResponse response, FileType fileType) throws EnrollmentServiceException {
        Sort<Enrollment> sorter = new SortEnrollmentImpl();
        List<Enrollment> list = sorter.getSortedList("ASC", "byFaculty", getList());
        FileCreate getReport = FileFactory.getFileCreate(fileType);
        getReport.setList(list);
        getReport.setResponse(response);
        try {
            getReport.createFile();
        } catch (FileCreateException e) {
            log.error(e.getMessage());
            throw new EnrollmentServiceException(e.getMessage());
        }
    }
}
