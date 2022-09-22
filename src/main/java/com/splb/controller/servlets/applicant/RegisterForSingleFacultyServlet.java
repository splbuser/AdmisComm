package com.splb.controller.servlets.applicant;

import com.splb.controller.pages.Pages;
import com.splb.model.dao.constant.Fields;
import com.splb.model.entity.Faculty;
import com.splb.service.ApplicantResultService;
import com.splb.service.FacultyService;
import com.splb.service.exceptions.ApplicantResultServiceException;
import com.splb.service.exceptions.FacultyServiceException;
import com.splb.service.utils.GradeValidator;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;


public class RegisterForSingleFacultyServlet extends HttpServlet {

    private static final Logger log = LogManager.getLogger(RegisterForSingleFacultyServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter(Fields.ID));
            Faculty faculty = new FacultyService().getById(id);
            request.setAttribute(Fields.FACULTY, faculty);
            request.getRequestDispatcher(Pages.REGISTER_FOR_FACULTY)
                    .forward(request, response);
        } catch (FacultyServiceException e) {
            log.error(e.getMessage());
            request.getRequestDispatcher(Pages.ERROR)
                    .forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (GradeValidator.validateGrades(new String[]{
                req.getParameter(Fields.SUBJ_ONE), req.getParameter(Fields.SUBJ_TWO)
        })) {
            int userId = (Integer) req.getSession().getAttribute(Fields.ID);
            int subjOne = Integer.parseInt(req.getParameter(Fields.SUBJ_ONE));
            int subjTwo = Integer.parseInt(req.getParameter(Fields.SUBJ_TWO));
            int facultyId = Integer.parseInt(req.getParameter(Fields.FACULTY_ID));
            if (userId != 0 && facultyId != 0) {
                try {
                    boolean addFResult = new ApplicantResultService().addFResult(userId, subjOne, subjTwo, facultyId);
                    if (addFResult) {
                        resp.sendRedirect(req.getContextPath() + Pages.REGISTER_FOR_FACULTY_SRV);
                    } else {
                        resp.sendRedirect(req.getContextPath() + Pages.ERROR);
                    }
                } catch (ApplicantResultServiceException e) {
                    log.error(e.getMessage());
                    resp.sendRedirect(req.getContextPath() + Pages.ERROR);
                }
            } else {
                resp.sendRedirect(req.getContextPath() + Pages.ERROR);
            }
        } else {
            resp.sendRedirect(req.getContextPath() + Pages.ERROR);
        }
    }
}
