package com.splb.controller.servlets;

import com.splb.controller.pages.Messages;
import com.splb.controller.pages.Pages;
import com.splb.model.dao.constant.Fields;
import com.splb.model.entity.Applicant;
import com.splb.service.ApplicantResultService;
import com.splb.service.ApplicantService;
import com.splb.service.exceptions.ApplicantResultServiceException;
import com.splb.service.utils.PassCrypt;
import com.splb.service.utils.CaptchaVerification;
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
    public static final String ROLE = "role";
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_USER = "USER";
    public static final String USER = "user";
    public static final String ID = "id";
    public static final String USER_NAME = "user_name";

    public static final String HELLOUSER = "hellouser";
    public static final String RECAPTCHA_RESPONSE = "g-recaptcha-response";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ApplicantService srv = new ApplicantService();
        HttpSession httpSession = req.getSession();
        String userName = req.getParameter(Fields.APPLICANT_USER_NAME);
        String pass = req.getParameter(Fields.APPLICANT_PASSWORD);
        try {
            if (nonNull(userName) && nonNull(pass)) {
                String password = PassCrypt.encodeWithoutPadding(pass.getBytes());
                Applicant user = srv.login(userName, password);
                doLogin(req, resp, httpSession, userName, user);
            } else {
                httpSession.setAttribute(Messages.CREDENTIAL, Messages.NULL);
                resp.sendRedirect(req.getContextPath() + Pages.LOGIN);
            }
        } catch (Exception e) {
            log.error("Login error: {}", e.getMessage());
            resp.sendRedirect(getServletContext().getContextPath() + Pages.ERROR);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher(Pages.LOGIN_PAGE)
                .forward(req, resp);
    }

    private void doLogin(HttpServletRequest req, HttpServletResponse resp,
                         HttpSession httpSession, String userName, Applicant user) throws IOException, ApplicantResultServiceException {
        boolean validCaptcha = false;
        ApplicantResultService asrv = new ApplicantResultService();
        if (nonNull(user)) {
            if (user.isBlockStatus()) {
                httpSession.setAttribute(Messages.MESSAGE, Messages.ADMINISTRATOR_BLOCKED_YOU);
                resp.sendRedirect(getServletContext().getContextPath() + Pages.ERROR);
            } else {
                String gRecaptchaResponse = req.getParameter(RECAPTCHA_RESPONSE);
                log.info("gRecaptchaResponse={}", gRecaptchaResponse);
                validCaptcha = CaptchaVerification.verify(gRecaptchaResponse);
                if (validCaptcha) {
                    int id = user.getId();
                    httpSession.setAttribute(USER, user);
                    httpSession.setAttribute(ID, id);
                    httpSession.setAttribute(USER_NAME, user.getUserName());
                    httpSession.setAttribute(Fields.RESULT_CHECK, asrv.checkSub(id));
                    httpSession.setAttribute(HELLOUSER, String.format("%s %s",
                            user.getFirstName(), user.getLastName()));
                    setRole(req, resp, httpSession, userName, user);
                    if (nonNull(httpSession.getAttribute(Messages.MESSAGE))) {
                        httpSession.removeAttribute(Messages.MESSAGE);
                    }
                } else {
                    httpSession.setAttribute(Messages.MESSAGE, Messages.WRONG_CAPTCHA);
                    resp.sendRedirect(req.getContextPath() + Pages.LOGIN);
                }
            }
        } else {
            httpSession.setAttribute(Messages.MESSAGE, Messages.WRONG_CREDENTIAL);
            resp.sendRedirect(req.getContextPath() + Pages.LOGIN);
        }
    }

    private static void setRole(HttpServletRequest req, HttpServletResponse resp, HttpSession httpSession,
                                String userName, Applicant user) throws IOException {
        if (user.isAdminStatus()) {
            httpSession.setAttribute(ROLE, ROLE_ADMIN);
            resp.sendRedirect(req.getContextPath() + Pages.FACULTY_TABLE);
        } else {
            httpSession.setAttribute(ROLE, ROLE_USER);
            resp.sendRedirect(req.getContextPath() + Pages.USER_INDEX);
        }
        log.info("{} log in", userName);
    }
}