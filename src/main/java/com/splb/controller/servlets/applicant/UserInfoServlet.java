package com.splb.controller.servlets.applicant;

import com.splb.controller.pages.Messages;
import com.splb.controller.pages.Pages;
import com.splb.model.dao.constant.Fields;
import com.splb.model.entity.*;
import com.splb.service.ApplicantResultService;
import com.splb.service.ApplicantService;
import com.splb.service.EnrollmentService;
import com.splb.service.StatementService;
import com.splb.service.exceptions.ServiceException;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;

import static java.util.Objects.nonNull;

public class UserInfoServlet extends HttpServlet {

    private static final Logger log = LogManager.getLogger(UserInfoServlet.class);
    public static final String EMPTY_LIST_MSG = "empty_list_msg";
    public static final String EMPTY_ENROLL = "empty_enroll";
    public static final String CURRENT_USER = "currentUser";
    public static final String RESULT_CHECK = "resultCheck";
    public static final String ENROLLMENT = "enrollment";
    public static final String FACULTIES = "faculties";
    public static final String STATEMENT = "statement";
    public static final String RESULT = "result";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer id = (Integer) session.getAttribute(Fields.ID);
        if (nonNull(id)) {
            try {
                Applicant currentUser = new ApplicantService().get(id);
                boolean resultCheck = new ApplicantResultService().checkSub(id);
                ApplicantResult result = new ApplicantResultService().get(id);
                List<Faculty> faculties = new ApplicantService().getCustomList(id);
                List<StatementResult> statement = new StatementService().get(id);
                if (statement.isEmpty()) {
                    request.setAttribute(EMPTY_LIST_MSG, Messages.ANY_RESULTS_STATEMENT);
                }
                Enrollment enrollment = new EnrollmentService().get(id);
                if (enrollment == null) {
                    request.setAttribute(EMPTY_ENROLL, Messages.NO_ENROLL);
                }
                request.setAttribute(CURRENT_USER, currentUser);
                request.setAttribute(RESULT_CHECK, resultCheck);
                request.setAttribute(ENROLLMENT, enrollment);
                request.setAttribute(FACULTIES, faculties);
                request.setAttribute(STATEMENT, statement);
                request.setAttribute(RESULT, result);
                request.getRequestDispatcher(Pages.USER_INFO)
                        .forward(request, response);
            } catch (ServiceException e) {
                log.error(e.getMessage());
                request.getRequestDispatcher(Pages.ERROR)
                        .forward(request, response);
            }
        } else {
            request.getRequestDispatcher(Pages.ERROR)
                    .forward(request, response);
        }
    }
}