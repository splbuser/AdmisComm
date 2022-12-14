package com.splb.controller.filters;

import com.splb.controller.pages.Pages;
import com.splb.model.entity.Role;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static java.util.Objects.nonNull;

/**
 * filter for admin access
 */
@WebFilter(filterName = "AdminFilter", urlPatterns = {"/Reristerforfaculty", "/Submitresult", "/user-index.jsp"})
public class AdminFilter implements Filter {
    private static final Logger log = LogManager.getLogger(AdminFilter.class);
    public static final String ROLE = "role";

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);

        if (nonNull(session) && nonNull(session.getAttribute(ROLE))) {
            if (session.getAttribute(ROLE).equals(Role.ADMIN)) {
                log.info("ADMIN shall not pass");
                response.sendRedirect(request.getContextPath() + Pages.FACULTY_TABLE);
            } else if (session.getAttribute(ROLE).equals(Role.USER)) {
                chain.doFilter(req, res);
            }
        } else {
            chain.doFilter(req, res);
        }
    }
}