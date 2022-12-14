package com.splb.controller.servlets.admin;

import com.splb.controller.pages.Pages;
import com.splb.model.dao.constant.Fields;
import com.splb.service.FacultyService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * The servlet receives the id of the deleted object and deletes it  using
 * the deleteFacultyById(id) method. After successful removal, redirection
 * is made to DisplayFaculty.
 */

public class DeleteFacultyServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(DeleteFacultyServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        FacultyService srv = new FacultyService();
        try {
            int id = Integer.parseInt(request.getParameter(Fields.ID));
            srv.delete(id);
            response.sendRedirect(request.getContextPath() + Pages.FACULTY_TABLE);
        }
        catch(Exception e) {
            log.error(e.getMessage());
            response.sendRedirect(request.getContextPath() + Pages.ERROR);
        }
    }
}