package com.splb.controller.servlets.admin;

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

import java.io.IOException;

/**
 * In doGet method, the servlet receives the id parameter and uses it to retrieve the
 * corresponding object from the database. If the object is found, it's transferred to
 * the faculty-edit.jsp page. A form is defined here, which displays editable data in
 * form fields. When the button is pressed, the data goes back to the EditServlet servlet.
 * If the object is not found or some error occurred, the page error-page.jsp is returned.
 * In the doPost method, we receive the sent data and transfer it to the database through
 * the update() method.
 */

public class EditFacultyServlet extends HttpServlet {

    private static final Logger log = LogManager.getLogger(EditFacultyServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        FacultyService srv = new FacultyService();
        Faculty faculty;
        int id;
        try {
            if (Integer.parseInt(request.getParameter(Fields.ID)) != 0) {
                id = Integer.parseInt(request.getParameter(Fields.ID));
                faculty = srv.getById(id);
                request.setAttribute(Fields.FACULTY, faculty);
                request.getRequestDispatcher(Pages.EDIT_FACULTY)
                        .forward(request, response);
            } else {
                request.getRequestDispatcher(Pages.ERROR)
                        .forward(request, response);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            request.getRequestDispatcher(Pages.ERROR)
                    .forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        FacultyService srv = new FacultyService();
        Faculty faculty;
        try {
            int id = Integer.parseInt(request.getParameter(Fields.ID));
            String name = request.getParameter(Fields.FACULTY_NAME);
            int tp = Integer.parseInt(request.getParameter(Fields.FACULTY_TOTAL_PLACES));
            int bp = Integer.parseInt(request.getParameter(Fields.FACULTY_BUDGET_PLACES));
            String so = request.getParameter(Fields.SUBJECT_ONE);
            String st = request.getParameter(Fields.SUBJECT_TWO);
            if (id > 0 &&
                    FacultyDataValidator.validateName(name) &&
                    FacultyDataValidator.validateCapacity(bp, tp) &&
                    FacultyDataValidator.validateName(so) &&
                    FacultyDataValidator.validateName(st)
            ) {
                faculty = new Faculty(id, name, bp, tp, so, st);
                if (srv.update(faculty)) {
                    log.info("Faculty info updated successfully");
                    response.sendRedirect(request.getContextPath() + Pages.FACULTY_TABLE);
                } else {
                    log.error("Error while updating");
                    response.sendRedirect(request.getContextPath() + Pages.ERROR);
                }
            } else {
                log.error("Wrong input data");
                response.sendRedirect(request.getContextPath() + Pages.ERROR);
            }
        } catch (Exception e) {
            log.error("Wrong input data");
            response.sendRedirect(request.getContextPath() + Pages.ERROR);
        }
    }
}