package com.splb.controller.servlets;

import com.splb.controller.pages.Pages;
import com.splb.model.dao.constant.Fields;
import com.splb.model.entity.Applicant;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;

import static java.util.Objects.nonNull;

public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Applicant user = (Applicant) session.getAttribute(Fields.USER);
        if (nonNull(user)) {
            session.invalidate();
            request.getRequestDispatcher(Pages.INDEX)
                    .forward(request, response);
        } else {
            request.getRequestDispatcher(Pages.ERROR)
                    .forward(request, response);
        }
    }
}