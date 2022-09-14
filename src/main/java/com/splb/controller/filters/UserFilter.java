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
 * filter for registered user access
 */
@WebFilter(filterName = "UserFilter", urlPatterns = {"/DisplayFaculty", "/DisplayApplicants", "/Statement", "/Enrollment"})
public class UserFilter implements Filter {
    private static final Logger log = LogManager.getLogger(UserFilter.class);
    public static final String ROLE = "role";

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);

        if (nonNull(session) && nonNull(session.getAttribute(ROLE))) {
            if (session.getAttribute(ROLE).equals(Role.USER)) {
                log.info("USER access denied");
                response.sendRedirect(request.getContextPath() + Pages.USER_INDEX);
            } else if (session.getAttribute(ROLE).equals(Role.ADMIN)) {
                chain.doFilter(req, res);
            }
        } else {
            chain.doFilter(req, res);
        }
    }
}