package com.splb.controller.servlets.admin;

import com.splb.controller.pages.Pages;
import com.splb.model.dao.constant.Fields;

import com.splb.model.entity.Applicant;
import com.splb.service.FacultyService;
import com.splb.service.StatementService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;

import static java.util.Objects.nonNull;

/**
 * Pressing "Watch list" on faculty table shows applicants, registered for current faculty.
 * Inside that table you can add user to Statement.
 */
public class FacultyApplicationListServlet extends HttpServlet {

    private static final Logger log = LogManager.getLogger(FacultyApplicationListServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        FacultyService fsrv = new FacultyService();
        StatementService ssrv = new StatementService();
        try {

            /* get faculty id to generate applicants list */

            int id = Integer.parseInt(req.getParameter(Fields.ID));
            List<Applicant> applicantList = FacultyService
                    .getApplicantsForStatement(id);
            String facultyName = fsrv.getById(id).getName();

            for (Applicant a : applicantList
            ) {
                a.setStatementStatus(ssrv.check(id, Math.toIntExact(a.getId())));
            }
            req.setAttribute("applicants", applicantList);
            req.setAttribute("faculty_id", id);
            req.setAttribute("faculty_name", facultyName);
            getServletContext()
                    .getRequestDispatcher(Pages.MANAGE_APPLICANTS)
                    .forward(req, resp);
        } catch (Exception e) {
            log.error(e.getMessage());
            getServletContext()
                    .getRequestDispatcher(Pages.ERROR)
                    .forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StatementService srv = new StatementService();
        String action = req.getParameter(Fields.ACTION);
        String facultyId = req.getParameter(Fields.FACULTY_ID);
        try {
            if (nonNull(action) && nonNull(facultyId) && action.equals(Fields.ADDALL)) {
                srv.addEmAll(Integer.parseInt(facultyId));
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            resp.sendRedirect(req.getContextPath() + Pages.ERROR);
        }
        resp.sendRedirect(req.getContextPath() + Fields.WATCHLIST_ID + facultyId);
    }
}
