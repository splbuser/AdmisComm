package com.splb.controller.servlets;

import com.splb.controller.pages.Messages;
import com.splb.controller.pages.Pages;
import com.splb.model.dao.constant.Fields;
import com.splb.model.dao.implementation.ApplicantResultDAOImpl;
import com.splb.model.entity.Applicant;
import com.splb.service.ApplicantResultService;
import com.splb.service.ApplicantService;
import com.splb.service.utils.PassCrypt;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static java.util.Objects.nonNull;

public class LoginServlet extends HttpServlet {

    private static final Logger log = LogManager.getLogger(LoginServlet.class);


    @Override
    public void init() throws ServletException {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ApplicantService srv = new ApplicantService();
        ApplicantResultService asrv = new ApplicantResultService();

        HttpSession httpSession = req.getSession();
        String userName = req.getParameter(Fields.APPLICANT_NAME);
        String pass = req.getParameter(Fields.APPLICANT_PASSWORD);


        try {

            /* Checking whether the details of user are null or not */
            if (nonNull(userName) && nonNull(pass)) {
                String password = PassCrypt.encodeWithoutPadding(pass.getBytes());
                Applicant user = srv.login(userName, password);
                if (nonNull(user)) {
                    /* Checking if user blocked */
                    if (user.isBlockStatus()) {
                        httpSession.setAttribute(Messages.MESSAGE, Messages.ADMINISTRATOR_BLOCKED_YOU);
                        resp.sendRedirect(getServletContext().getContextPath() + Pages.ERROR);
                    } else {
                        /* Storing the login details in session */
                        int id = user.getId();

                        httpSession.setAttribute("user", user);
                        httpSession.setAttribute("id", id);
                        httpSession.setAttribute("user_name", user.getUserName());
                        httpSession.setAttribute("resultCheck", asrv.checkSub(id));
                        httpSession.setAttribute("hellouser", user.getFirstName() + " " + user.getLastName());

                        if (user.isAdminStatus()) {
                            httpSession.setAttribute("role", "ADMIN"); // set user role
                            resp.sendRedirect(req.getContextPath() + Pages.FACULTY_TABLE);
                        } else {
                            httpSession.setAttribute("role", "USER"); // set user role
                            resp.sendRedirect(req.getContextPath() + Pages.USER_INDEX);
                        }
                        // delete message after success login
                        if (httpSession.getAttribute(Messages.MESSAGE) != null) {
                            httpSession.removeAttribute(Messages.MESSAGE);
                        }
                        log.info("{} log in", userName);
                    }

                } else {
                    //If wrong credentials are entered
                    httpSession.setAttribute(Messages.MESSAGE, Messages.WRONG_CREDENTIAL);
                    resp.sendRedirect(req.getContextPath() + "Login");
                }
            } else {
                /* If username or password is empty or null */
                httpSession.setAttribute("credential", Messages.NULL);
                resp.sendRedirect(req.getContextPath() + "Login");

            }
        } catch (Exception e) {
            log.error("Login error: {}", e.getMessage());
            resp.sendRedirect(getServletContext().getContextPath() + Pages.ERROR);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher(Pages.LOGIN)
                .forward(req, resp);
    }
}
