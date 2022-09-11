package com.splb.controller.servlets.admin;

import com.splb.controller.pages.Pages;
import com.splb.model.dao.constant.Fields;
import com.splb.service.StatementService;
import com.splb.service.exceptions.StatementServiceException;
import com.splb.service.sorting.SortStatementImpl;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

public class StatementServlet extends HttpServlet {

    private static final Logger log = LogManager.getLogger(StatementServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StatementService srv = new StatementService();
        HttpSession session = request.getSession();
        String type = request.getParameter(Fields.TYPE);
        String sortBy = request.getParameter(Fields.SORT_BY);
        List<com.splb.model.entity.Statement> list = new ArrayList<>();
        try {
            if (type == null || sortBy == null) {
                type = (String) session.getAttribute(Fields.TYPE);
                sortBy = (String) session.getAttribute(Fields.SORT_BY);
                if (type == null || sortBy == null) {
                    list = srv.getList();
                } else {
                    SortStatementImpl ss = new SortStatementImpl();
                    list = ss.getSortedList(type, sortBy, srv.getList());
                }
            } else {
                SortStatementImpl ss = new SortStatementImpl();
                list = ss.getSortedList(type, sortBy, srv.getList());
                session.setAttribute(Fields.TYPE, type);
                session.setAttribute(Fields.SORT_BY, sortBy);
            }
        } catch (StatementServiceException e) {
            log.error(MessageFormat.format("could not get statement: {}", e.getMessage()));
            request.getRequestDispatcher(Pages.ERROR)
                    .forward(request, response);
        }
        request.setAttribute("list", list);
        request.getRequestDispatcher(Pages.STATEMENT_PAGE)
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StatementService srv = new StatementService();
        String action = req.getParameter(Fields.ACTION);
        try {
            if (nonNull(action) && action.equals(Fields.FINALIZE)) {
                srv.finalizeStatement();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            resp.sendRedirect(req.getContextPath() + Pages.ERROR);
        }
        resp.sendRedirect(req.getContextPath() + Pages.ENROLLMENT);
    }
}