package com.splb.controller.servlets.admin;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.LongStream;

import com.splb.controller.pages.Messages;
import com.splb.controller.pages.Pages;
import com.splb.model.dao.constant.Fields;
import com.splb.model.entity.Faculty;
import com.splb.service.FacultyService;
import com.splb.service.utils.DataValidator;
import com.splb.service.utils.FacultyDataValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Servlet for creating a new faculty.
 * On get request, the servlet will give the user a form to fill in
 * the added data in the form of the faculty-create.jsp page. With
 * a post request, the user receives the data submitted via the form
 * and adds it to the database using the addFaculty method. After
 * successful addition, it redirects to the DisplayFaculty servlet.
 */

public class CreateFacultyServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(CreateFacultyServlet.class);


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher(Pages.FACULTY_CREATE)
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String name = request.getParameter(Fields.FACULTY_NAME);
        int budgetPlaces = Integer.parseInt(request.getParameter(Fields.FACULTY_BUDGET_PLACES));
        int totalPlaces = Integer.parseInt(request.getParameter(Fields.FACULTY_TOTAL_PLACES));
        String subjectOne = request.getParameter(Fields.SUBJECT_ONE);
        String subjectTwo = request.getParameter(Fields.SUBJECT_TWO);
        List<String> errorMessages = FacultyDataValidator.validateForm(name, budgetPlaces,
                totalPlaces, subjectOne, subjectTwo);
        try {
            boolean checkName = new FacultyService().checkByName(name);
            if (errorMessages == null && !checkName) {
                new FacultyService().add(new Faculty(name, budgetPlaces, totalPlaces, subjectOne, subjectTwo));
                response.sendRedirect(request.getContextPath() + Pages.FACULTY_TABLE);
            } else if (errorMessages == null && checkName) {
                List<String> validValues = Arrays.asList(null, null, subjectOne, subjectTwo);
                log.debug(Messages.FNAME_NOT_UNIQUE);
                session.setAttribute(Fields.VALID_VALUES, validValues);
                session.setAttribute(Messages.ERROR, Messages.FNAME_NOT_UNIQUE);
                response.sendRedirect(request.getContextPath() + Pages.CREATE);
            } else {
                String[] inputValues = {name, null, subjectOne, subjectTwo};
                List<String> validValues = FacultyDataValidator.getValidatedValues(inputValues, errorMessages);
                log.debug(errorMessages);
                session.setAttribute(Fields.VALID_VALUES, validValues);
                session.setAttribute(Messages.ERRORS, errorMessages);
                response.sendRedirect(request.getContextPath() + Pages.CREATE);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            response.sendRedirect(request.getContextPath() + Pages.ERROR);
        }
    }
}