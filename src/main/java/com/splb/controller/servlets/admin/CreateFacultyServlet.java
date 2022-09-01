package com.splb.controller.servlets.admin;

import java.io.IOException;

import com.splb.controller.pages.Pages;
import com.splb.model.dao.constant.Fields;
import com.splb.model.entity.Faculty;
import com.splb.service.FacultyService;
import com.splb.service.utils.FacultyDataValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static java.util.Objects.nonNull;


/**
 * Servlet for creating a new faculty.
 * On get request, the servlet will give the user a form to fill in
 * the added data in the form of the faculty-create.jsp page. With
 * a post request, the user receives the data submitted via the form
 * and adds it to the database using the addFaculty method. After
 * successful addition, it redirects to the DisplayFaculty servlet..
 */


public class CreateFacultyServlet extends HttpServlet {

    private static final Logger log = LogManager.getLogger(CreateFacultyServlet.class);


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        getServletContext().getRequestDispatcher(Pages.FACULTY_CREATE)
                .forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        FacultyService srv = new FacultyService();
        Faculty faculty = null;
        try {

            String name = request.getParameter("name");
            int budgetPlaces = Integer.parseInt(request.getParameter(Fields.FACULTY_BUDGET_PLACES));
            int totalPlaces = Integer.parseInt(request.getParameter(Fields.FACULTY_TOTAL_PLACES));
            String subjectOne = request.getParameter(Fields.SUBJECT_ONE);
            String subjectTwo = request.getParameter(Fields.SUBJECT_TWO);

            if (
                    FacultyDataValidator.validateName(name) &&
                            FacultyDataValidator.validateCapacity(budgetPlaces, totalPlaces) &&
                            FacultyDataValidator.validateName(subjectOne) &&
                            FacultyDataValidator.validateName(subjectTwo)
            ) {
                faculty = new Faculty(name, budgetPlaces, totalPlaces, subjectOne, subjectTwo);
            }

            if (nonNull(faculty)) {
                srv.add(faculty);
                response.sendRedirect(request.getContextPath() + Pages.FACULTY_TABLE);
            } else {
                log.error("faculty was not added");
                response.sendRedirect(request.getContextPath() + Pages.ERROR);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            response.sendRedirect(request.getContextPath() + Pages.ERROR);
        }
    }
}