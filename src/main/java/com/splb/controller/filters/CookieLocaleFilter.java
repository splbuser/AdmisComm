package com.splb.controller.filters;

import com.splb.model.dao.constant.Fields;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static java.util.Objects.nonNull;

/**
 * filter for set locale into cookies
 */
@WebFilter(filterName = "CookieLocaleFilter", urlPatterns = {"/*"})
public class CookieLocaleFilter implements Filter {

    public static final String LANG = "lang";

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        if (nonNull(req.getParameter(Fields.LOCALE))) {
            Cookie cookie = new Cookie(LANG, req.getParameter(Fields.LOCALE));
            res.addCookie(cookie);
//            res.sendRedirect(req.getHeader("referer"));
        }
        chain.doFilter(request, response);
    }
}