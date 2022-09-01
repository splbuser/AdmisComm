package com.splb.controller.filters;

import com.splb.controller.pages.Pages;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static java.util.Objects.nonNull;

@WebFilter(urlPatterns = {"/DisplayFaculty", "/DisplayApplicants", "/Statement", "/Enrollment"})
public class UserFilter implements Filter {
    private static final Logger log = LogManager.getLogger(UserFilter.class);

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);

        if (nonNull(session) && nonNull(session.getAttribute("role"))) {
            if (session.getAttribute("role").equals("USER")) {
                log.info("USER access denied");
                response.sendRedirect(request.getContextPath() + Pages.ERROR);
            } else if (session.getAttribute("role").equals("ADMIN")) {
                chain.doFilter(req, res);
            }
        } else {
            chain.doFilter(req, res);
        }
    }
}