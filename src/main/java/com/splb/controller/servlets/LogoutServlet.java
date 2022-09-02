package com.splb.controller.servlets;

import com.splb.controller.pages.Pages;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;

import static java.util.Objects.nonNull;

public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (nonNull(session)) {
            session.invalidate();
            getServletContext().getRequestDispatcher(Pages.INDEX)
                    .forward(request, response);
        }
    }
}