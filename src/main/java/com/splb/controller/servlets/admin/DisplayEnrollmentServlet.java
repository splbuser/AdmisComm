package com.splb.controller.servlets.admin;

import com.splb.controller.pages.Pages;
import com.splb.model.dao.constant.Fields;
import com.splb.model.entity.Applicant;
import com.splb.model.entity.Enrollment;
import com.splb.service.ApplicantService;
import com.splb.service.EnrollmentService;
import com.splb.service.exceptions.EnrollmentServiceException;
import com.splb.service.exceptions.UserServiceException;
import com.splb.service.utils.filecreator.factory.FileType;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

public class DisplayEnrollmentServlet extends HttpServlet {

    private static final Logger log = LogManager.getLogger(DisplayEnrollmentServlet.class);
    public static final String ENROLLMENT = "enrollment";
    public static final String NOT_ENROLL = "not_enroll";
    public static final String NOTIFY = "notify";
    public static final String GET_DOC = "getDOC";
    public static final String GET_PDF = "getPDF";
    public static final String GET_XLS = "getXLS";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String type = request.getParameter(Fields.TYPE);
        String sortBy = request.getParameter(Fields.SORT_BY);
        try {
            List<Enrollment> enrollment = new EnrollmentService().getEnrollmentsForRequest(session, type, sortBy);
            List<Applicant>  notEnroll = new ApplicantService().getNotEnroll();
            request.setAttribute(ENROLLMENT, enrollment);
            request.setAttribute(NOT_ENROLL, notEnroll);
            request.getRequestDispatcher(Pages.ENROLLMENT_PAGE)
                    .forward(request, response);
        } catch (EnrollmentServiceException | UserServiceException e) {
            log.error(e.getMessage());
            request.getRequestDispatcher(Pages.ERROR)
                    .forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter(Fields.ACTION);
        EnrollmentService srv = new EnrollmentService();
        try {
            if (nonNull(action)) {
                switch (action) {
                    case NOTIFY -> new ApplicantService().notifyUsers();
                    case GET_DOC -> srv.getReport(resp, FileType.CREATE_DOC);
                    case GET_PDF -> srv.getReport(resp, FileType.CREATE_PDF);
                    case GET_XLS -> srv.getReport(resp, FileType.CREATE_XLS);
                    default -> resp.sendRedirect(req.getContextPath() + Pages.ENROLLMENT);
                }
            }
        } catch (UserServiceException | EnrollmentServiceException e) {
            log.error(e.getMessage());
            resp.sendRedirect(req.getContextPath() + Pages.ERROR);
        }
        resp.sendRedirect(req.getContextPath() + Pages.ENROLLMENT);
    }
}