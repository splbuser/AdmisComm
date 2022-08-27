package com.splb.controller.servlets.applicant;

import com.splb.controller.pages.Messages;
import com.splb.controller.pages.Pages;
import com.splb.model.dao.constant.Fields;
import com.splb.model.dao.implementation.FacultyDAOImpl;
import com.splb.model.entity.Applicant;
import com.splb.model.entity.Faculty;
import com.splb.service.FacultyService;
import com.splb.service.exceptions.FacultyServiceException;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;

import static java.util.Objects.nonNull;

public class FacultyRegServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(FacultyRegServlet.class);


    @Override
    public void init() throws ServletException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Applicant currentUser = new Applicant();
        FacultyService srv = new FacultyService();
        List<Faculty> faculties;

        HttpSession session = request.getSession();
        Integer id = (Integer) session.getAttribute(Fields.ID); // user id

        if (nonNull(id)) {
            try {
                faculties = srv.getListForRegister(id);
                if (faculties.isEmpty()) {
                    session.setAttribute("step3", true);
                    request.setAttribute(Messages.MESSAGE, Messages.NO_NEW_REGISTRATIONS_AVAILABLE);
                }
                request.setAttribute("currentUser", currentUser);
                request.setAttribute("faculty", faculties);

                getServletContext().getRequestDispatcher(Pages.REGISTER_FOR_FACULTY)
                        .forward(request, response);
            } catch (FacultyServiceException e) {
                log.error(e.getMessage());
                getServletContext().getRequestDispatcher(Pages.ERROR)
                        .forward(request, response);
            }
        } else {
            getServletContext().getRequestDispatcher(Pages.ERROR)
                    .forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        FacultyService srv = new FacultyService();
        try {
            int id = Integer.parseInt(request.getParameter(Fields.ID));
            srv.getById(id);

            response.sendRedirect(request.getContextPath() + Pages.REGISTER_FOR_FACULTY_SRV);
        } catch (Exception e) {
            log.error(e.getMessage());
            response.sendRedirect(request.getContextPath() + Pages.ERROR);
        }

    }

}
