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
import com.splb.service.utils.filecreator.PDFReportCreate;
import com.splb.service.utils.filecreator.exceptions.FileCreateException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class EnrollmentService extends Service {

    public EnrollmentService() {
        setConnectionBuilder(new PoolConnectionBuilder());
    }

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

    public List<Enrollment> getList() throws EnrollmentServiceException {
        try (Connection con = getConnection()) {
            return edao.getEnrollment(con);
        } catch (EnrollmentDAOException | SQLException e) {
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

    public void getReport(HttpServletResponse response) throws EnrollmentServiceException {
        Sort<Enrollment> sorter = new SortEnrollmentImpl();
        List<Enrollment> list = sorter.getSortedList("ASC", "byFaculty", getList());
        PDFReportCreate report = new PDFReportCreate();
        report.setList(list);
        report.setResponse(response);
        try {
            report.createFile();
        } catch (FileCreateException e) {
            log.error(e.getMessage());
            throw new EnrollmentServiceException(e.getMessage());
        }
    }
}
