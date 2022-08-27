package com.splb.controller.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;


@WebFilter(urlPatterns = {"/DisplayFaculty", "/DisplayApplicants", "/Statement", "/Enrollment",
        "/create", "/delete", "/edit", "/watchlist", "/blockbusting", "/logout", "/finalize",
        "/Reristerforthisfaculty", "/Userinfo", "/addresult", "/Submitresult", "/Reristerforfaculty", "/user-index.jsp"})

public class AllUserFilter implements Filter {
    private static final Logger log = LogManager.getLogger(AllUserFilter.class);

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            log.info("No logged-in user detected, so redirect to login page");
            response.sendRedirect(((HttpServletRequest) req).getContextPath() + "/Login");
        } else {
            chain.doFilter(req, res); // Logged-in user found, so just continue request.
        }
    }

}
