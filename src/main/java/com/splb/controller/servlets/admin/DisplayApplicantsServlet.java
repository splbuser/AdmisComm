package com.splb.controller.servlets.admin;

import com.splb.controller.pages.Pages;
import com.splb.model.dao.constant.Fields;
import com.splb.model.entity.Applicant;
import com.splb.service.ApplicantService;
import com.splb.service.exceptions.UserServiceException;
import com.splb.service.sorting.SortApplicantImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DisplayApplicantsServlet extends HttpServlet {

    private static final Logger log = LogManager.getLogger(DisplayApplicantsServlet.class);


    @Override
    public void init() throws ServletException {
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ApplicantService srv = new ApplicantService();
        HttpSession session = request.getSession();
        List<Applicant> applicants = new ArrayList<>();
        int listLength = 0;
        int page = 1;
        int limit = 10;

        try {
            listLength = srv.length();
        } catch (UserServiceException e) {
            log.error(e.getMessage());
        }

        if (session.getAttribute(Fields.LIMIT) == null) {
            session.setAttribute(Fields.LIMIT, limit);
        } else if ((Integer) session.getAttribute(Fields.LIMIT) > listLength) {
            session.setAttribute(Fields.LIMIT, listLength);
        }

        if (request.getParameter(Fields.PAGE) != null) {
            page = Integer.parseInt(
                    request.getParameter(Fields.PAGE));
        }

        limit = (Integer) session.getAttribute(Fields.LIMIT);
        String type = request.getParameter(Fields.TYPE);
        String sortBy = request.getParameter(Fields.SORT_BY);


        try {
            if (type == null || sortBy == null) {
                applicants = srv.getAll(limit, (page - 1) * limit);
            } else {
                SortApplicantImpl sf = new SortApplicantImpl();
                applicants = sf.getSortedList(
                        type, sortBy,
                        srv.getAll(limit, (page - 1) * limit));
            }
        } catch (UserServiceException e) {
            log.error("could not get Applicants list: {}", e.getMessage());
            getServletContext().getRequestDispatcher(Pages.ERROR)
                    .forward(request, response);
        }

        int noOfPages = (int) Math.ceil(listLength * 1.0 / limit);

        request.setAttribute("applicants", applicants);
        request.setAttribute("listLength", listLength);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);

        request.getRequestDispatcher(Pages.MANAGE_USERS)
                .forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // number of user to show put on session attribute
        int limit = Integer.parseInt(req.getParameter(Fields.LIMIT));
        HttpSession session = req.getSession();
        session.setAttribute(Fields.LIMIT, limit);
        resp.sendRedirect(req.getContextPath() + "/DisplayApplicants");
    }
}