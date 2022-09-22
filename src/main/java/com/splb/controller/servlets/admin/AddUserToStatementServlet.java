package com.splb.controller.servlets.admin;

import com.splb.controller.pages.Pages;
import com.splb.model.dao.constant.Fields;
import com.splb.service.StatementService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.text.MessageFormat;

public class AddUserToStatementServlet extends HttpServlet {

    private static final Logger log = LogManager.getLogger(AddUserToStatementServlet.class);
    public static final String USER_ID = "user_id";
    public static final String BOOL_STATUS = "boolStatus";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        StatementService srv = new StatementService();
        try {
            int userId = Integer.parseInt(request.getParameter(USER_ID));
            int facultyId = Integer.parseInt(request.getParameter(Fields.FACULTY_ID));
            boolean status = Boolean.parseBoolean(request.getParameter(BOOL_STATUS));
            if (facultyId != 0 && userId != 0 && !status) {
                srv.add(facultyId, userId);
            }
            if (status) {
                srv.remove(facultyId, userId);
            }
            response.sendRedirect(request.getContextPath() + Pages.WATCHLIST_ID + facultyId);
        } catch (Exception e) {
            log.error(MessageFormat.format("could not add or remove user: {}", e.getMessage()));
            response.sendRedirect(request.getContextPath() + Pages.ERROR);
        }
    }
}