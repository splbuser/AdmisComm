package com.splb.controller.servlets.admin;

import com.splb.controller.pages.Pages;
import com.splb.model.dao.constant.Fields;
import com.splb.model.entity.Enrollment;
import com.splb.service.ApplicantService;
import com.splb.service.EnrollmentService;
import com.splb.service.exceptions.EnrollmentServiceException;
import com.splb.service.exceptions.UserServiceException;
import com.splb.service.sorting.SortEnrollmentImpl;
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

    @Override
    public void init() throws ServletException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        String type = request.getParameter(Fields.TYPE);
        String sortBy = request.getParameter(Fields.SORT_BY);
        String notify = request.getParameter(Fields.ACTION);
        List<Enrollment> enrollment = new ArrayList<>();
        EnrollmentService srv = new EnrollmentService();
        ApplicantService asrv = new ApplicantService();

        try {
            if (type == null || sortBy == null) {
                type = (String) session.getAttribute(Fields.TYPE);
                sortBy = (String) session.getAttribute(Fields.SORT_BY);
                if (type == null || sortBy == null) {

                    enrollment = srv.getList();
                } else {
                    SortEnrollmentImpl se = new SortEnrollmentImpl();
                    enrollment = se.getSortedList(
                            type,
                            sortBy,
                            srv.getList());
                }
            } else {
                SortEnrollmentImpl se = new SortEnrollmentImpl();
                enrollment = se.getSortedList(
                        type,
                        sortBy,
                        srv.getList());
                session.setAttribute(Fields.TYPE, type);
                session.setAttribute(Fields.SORT_BY, sortBy);
            }

            if (nonNull(notify) && notify.equals("notify")) {
                asrv.notifyUsers();
            }

        } catch (EnrollmentServiceException | UserServiceException e) {
            log.error(e.getMessage());
            getServletContext().getRequestDispatcher(Pages.ERROR)
                    .forward(request, response);
        }
        request.setAttribute("enrollment", enrollment);
        request.getRequestDispatcher(Pages.ENROLLMENT_PAGE)
                .forward(request, response);
    }

}
