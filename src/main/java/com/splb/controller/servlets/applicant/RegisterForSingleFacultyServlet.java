package com.splb.controller.servlets.applicant;

import com.splb.controller.pages.Pages;
import com.splb.model.dao.constant.Fields;
import com.splb.model.dao.exception.ApplicantResultDAOException;
import com.splb.model.dao.exception.FacultyDAOException;
import com.splb.model.dao.implementation.ApplicantResultDAOImpl;
import com.splb.model.dao.implementation.FacultyDAOImpl;
import com.splb.model.entity.Faculty;
import com.splb.service.ApplicantResultService;
import com.splb.service.FacultyService;
import com.splb.service.exceptions.ApplicantResultServiceException;
import com.splb.service.exceptions.FacultyServiceException;
import com.splb.service.utils.GradeValidator;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;


@WebServlet("/Reristerforthisfaculty")
public class RegisterForSingleFacultyServlet extends HttpServlet {

    private static final Logger log = LogManager.getLogger(RegisterForSingleFacultyServlet.class);


    @Override
    public void init() throws ServletException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        FacultyService srv = new FacultyService();
        try {
            int id = Integer.parseInt(request.getParameter(Fields.ID));
            Faculty faculty = srv.getById(id);
            request.setAttribute("faculty", faculty);

            getServletContext()
                    .getRequestDispatcher(Pages.REGISTER_FOR_FACULTY)
                    .forward(request, response);
        } catch (FacultyServiceException e) {
            log.error(e.getMessage());
            getServletContext()
                    .getRequestDispatcher(Pages.ERROR)
                    .forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ApplicantResultService srv = new ApplicantResultService();
        if (GradeValidator.validateGrades(new String[]{
                req.getParameter(Fields.SUBJ_ONE), req.getParameter(Fields.SUBJ_TWO)
        })) {
            int userId = (Integer) req.getSession().getAttribute(Fields.ID);
            int subjOne = Integer.parseInt(req.getParameter(Fields.SUBJ_ONE));
            int subjTwo = Integer.parseInt(req.getParameter(Fields.SUBJ_TWO));
            int facultyId = Integer.parseInt(req.getParameter(Fields.FACULTY_ID));

            if (userId != 0 && facultyId != 0) {
                try {
                    if (srv.addFResult(req, resp, userId, subjOne, subjTwo, facultyId)) {
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
