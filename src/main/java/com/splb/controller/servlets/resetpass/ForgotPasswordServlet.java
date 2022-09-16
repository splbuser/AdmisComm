package com.splb.controller.servlets.resetpass;

import com.splb.controller.pages.Messages;
import com.splb.controller.pages.Pages;
import com.splb.model.dao.constant.Fields;
import com.splb.service.ApplicantService;
import com.splb.service.Service;
import com.splb.service.exceptions.UserServiceException;
import com.splb.service.utils.notifier.MailSender;
import com.splb.service.utils.notifier.MailText;
import com.splb.service.utils.notifier.Sender;
import com.splb.service.utils.notifier.exception.SenderException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;


import static java.util.Objects.nonNull;

public class ForgotPasswordServlet extends HttpServlet {

    private static final Logger log = LogManager.getLogger(ForgotPasswordServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        ApplicantService srv = new ApplicantService();
        String email = request.getParameter(Fields.APPLICANT_EMAIL);
        int rValue = 0;
        try {
            if (nonNull(email) && !email.isEmpty() && srv.checkEmail(email)) {
                Random rand = SecureRandom.getInstanceStrong();
                rValue = Math.abs(rand.nextInt());
                Sender s = new MailSender(email, MailText.RES_PASS_SUBJ.getText(),
                        String.format(MailText.RES_PASS_BODY.getText(), rValue));
                s.send();
                log.info("restore password sent to {} ", email);
                session.setAttribute(Fields.OTP, rValue);
                session.setAttribute(Fields.APPLICANT_EMAIL, email);
                request.setAttribute(Messages.MESSAGE, "OTP sent to your e-mail");
                request.getRequestDispatcher(Pages.ENTER_OTP).forward(request, response);
            } else {
                session.setAttribute(Messages.MESSAGE, "Sorry, we can't find your profile");
                response.sendRedirect(request.getContextPath() + "/forgotPassword" );
            }
        } catch (SenderException | NoSuchAlgorithmException | UserServiceException e) {
            log.error(e.getMessage());
            response.sendRedirect(Pages.ERROR);
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher(Pages.FORGOT_PASSWORD).forward(req, resp);
    }
}
