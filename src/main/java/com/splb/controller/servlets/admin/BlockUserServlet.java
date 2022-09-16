package com.splb.controller.servlets.admin;

import com.splb.controller.pages.Pages;
import com.splb.model.dao.constant.Fields;
import com.splb.service.ApplicantService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class BlockUserServlet extends HttpServlet {

    private static final Logger log = LogManager.getLogger(BlockUserServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ApplicantService srv = new ApplicantService();
        try {
            int id = Integer.parseInt(request.getParameter(Fields.ID));
            boolean blockStatus = Boolean.parseBoolean(request.getParameter(Fields.STATUS));
            if (blockStatus) {
                srv.unblock(id);
            } else {
                srv.block(id);
            }
            response.sendRedirect(request.getHeader("referer"));
        } catch (Exception e) {
            log.error(e.getMessage());
            response.sendRedirect(request.getContextPath() + Pages.ERROR);
        }
    }
}