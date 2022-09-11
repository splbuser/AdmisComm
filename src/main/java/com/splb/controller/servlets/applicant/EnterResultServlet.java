package com.splb.controller.servlets.applicant;

import com.splb.controller.pages.Messages;
import com.splb.controller.pages.Pages;
import com.splb.model.dao.exception.ApplicantResultDAOException;
import com.splb.model.dao.implementation.ApplicantResultDAOImpl;
import com.splb.model.dao.constant.Fields;
import com.splb.model.entity.ApplicantResult;
import com.splb.service.ApplicantResultService;
import com.splb.service.exceptions.ApplicantResultServiceException;
import com.splb.service.utils.GradeValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class EnterResultServlet extends HttpServlet {

    private static final Logger log = LogManager.getLogger(EnterResultServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ApplicantResultService srv = new ApplicantResultService();
        HttpSession session = req.getSession();
        if (GradeValidator.validateGrades(new String[]
                {
                        req.getParameter(Fields.RESULT_ALGEBRA),
                        req.getParameter(Fields.RESULT_BIOLOGY),
                        req.getParameter(Fields.RESULT_CHEMISTRY),
                        req.getParameter(Fields.RESULT_ENGLISH),
                        req.getParameter(Fields.RESULT_LITERATURE),
                        req.getParameter(Fields.RESULT_HISTORY)
                })
        ) {
            int id = (Integer) req.getSession().getAttribute(Fields.ID);
            int alg = Integer.parseInt(req.getParameter(Fields.RESULT_ALGEBRA));
            int bio = Integer.parseInt(req.getParameter(Fields.RESULT_BIOLOGY));
            int che = Integer.parseInt(req.getParameter(Fields.RESULT_CHEMISTRY));
            int eng = Integer.parseInt(req.getParameter(Fields.RESULT_ENGLISH));
            int lit = Integer.parseInt(req.getParameter(Fields.RESULT_LITERATURE));
            int his = Integer.parseInt(req.getParameter(Fields.RESULT_HISTORY));
            ApplicantResult applicantResult = new ApplicantResult(id, alg, bio, che, eng, lit, his);
            try {
                if (srv.insert(applicantResult)) {
                    session.setAttribute(Messages.MESSAGE, Messages.BEEN_APPLIED);
                    session.setAttribute("resultCheck", true);
                    resp.sendRedirect(req.getContextPath() + Pages.USER_INDEX);
                } else {
                    resp.sendRedirect(getServletContext().getContextPath() + Pages.ERROR);
                    session.setAttribute(Messages.MESSAGE, "Results was not added");
                }
            } catch (ApplicantResultServiceException e) {
                log.error("could not add result: {}", e.getMessage());
                resp.sendRedirect(getServletContext().getContextPath() + Pages.ERROR);
            }
        } else {
            session.setAttribute(Messages.MESSAGE, Messages.VALIDATION_FAIL);
            resp.sendRedirect(getServletContext().getContextPath() + Pages.ERROR);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        boolean check = (Boolean) session.getAttribute(Fields.RESULT_CHECK);
        if (!check) {
           req .getRequestDispatcher(Pages.ENTER_RESULT)
                    .forward(req, resp);
        } else {
            req.getRequestDispatcher(Pages.USER_INDEX)
                    .forward(req, resp);
        }
    }
}