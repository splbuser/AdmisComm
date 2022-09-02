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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer id = (Integer) session.getAttribute(Fields.ID);
        Applicant currentUser = null;
        ApplicantResult result = null;
        List<StatementResult> statement = null;
        List<Faculty> faculties = null;
        Enrollment enrollment = null;
        ApplicantService usrv = new ApplicantService();
        ApplicantResultService asrv = new ApplicantResultService();
        StatementService ssrv = new StatementService();
        EnrollmentService esrv = new EnrollmentService();
        if (nonNull(id)) {
            boolean resultCheck = false;
            try {
                currentUser = usrv.get(id);
                resultCheck = asrv.checkSub(id);
                result = asrv.get(id);
                statement = ssrv.get(id);
                faculties = usrv.getCustomList(id);
                enrollment = esrv.get(id);
                if (statement.isEmpty()) {
                    request.setAttribute("empty_list_msg", Messages.ANY_RESULTS_STATEMENT);
                }
                if (enrollment == null) {
                    request.setAttribute("empty_enroll", Messages.NO_ENROLL);
                }
            } catch (ServiceException e) {
                log.error(e.getMessage());
                getServletContext().getRequestDispatcher(Pages.ERROR)
                        .forward(request, response);
            }
            request.setAttribute("currentUser", currentUser);
            request.setAttribute("resultCheck", resultCheck);
            request.setAttribute("enrollment", enrollment);
            request.setAttribute("faculties", faculties);
            request.setAttribute("statement", statement);
            request.setAttribute("result", result);
            getServletContext().getRequestDispatcher(Pages.USER_INFO)
                    .forward(request, response);
        } else {
            getServletContext().getRequestDispatcher(Pages.ERROR)
                    .forward(request, response);
        }
    }
}
