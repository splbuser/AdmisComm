package com.splb.controller.servlets.admin;

import com.splb.controller.pages.Pages;
import com.splb.model.dao.constant.Fields;
import com.splb.model.dao.implementation.FacultyDAOImpl;

import com.splb.model.dao.implementation.StatementDAOImpl;
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

/**
 * Pressing "Watch list" on faculty table shows applicants, registered for current faculty.
 * Inside that table you can add user to Statement.
 */
public class FacultyApplicationListServlet extends HttpServlet {

    private static final Logger log = LogManager.getLogger(FacultyApplicationListServlet.class);


    @Override
    public void init() throws ServletException {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        FacultyService fsrv = new FacultyService();
        StatementService ssrv = new StatementService();
        try {
            /* получаем ID факултета, для формирования списка зарегистрированых апликантов*/
            int id = Integer.parseInt(req.getParameter(Fields.ID));

            /* формируем список апликантов*/
            List<Applicant> applicantList = FacultyService
                    .getApplicantsForStatement(id);
            String facultyName = fsrv.getById(id).getName();

            /* цикл добавляет стейтментстатус аппликанта на расчитаный для данного факультета. значение из бд не учитвается*/
            for (Applicant a : applicantList
            ) {
                a.setStatementStatus(ssrv
                        .check(id, Math.toIntExact(a.getId())));
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
}
