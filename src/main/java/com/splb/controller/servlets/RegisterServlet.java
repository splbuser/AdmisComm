package com.splb.controller.servlets;

import com.splb.controller.pages.Messages;
import com.splb.controller.pages.Pages;
import com.splb.model.dao.constant.Fields;
import com.splb.model.entity.Applicant;
import com.splb.service.ApplicantService;
import com.splb.service.exceptions.ServiceException;
import com.splb.service.utils.notifier.MailSender;
import com.splb.service.utils.PassCrypt;
import com.splb.service.utils.DataValidator;
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
import java.util.List;

public class RegisterServlet extends HttpServlet {

    private static final Logger log = LogManager.getLogger(RegisterServlet.class);
    public static final String LOGIN = "Login";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ApplicantService srv = new ApplicantService();
        HttpSession httpSession = req.getSession();
        String userName = req.getParameter(Fields.APPLICANT_USER_NAME);
        String password = req.getParameter(Fields.APPLICANT_PASSWORD);
        String rePassword = req.getParameter(Fields.PASSWORD_REPEAT);
        String email = req.getParameter(Fields.APPLICANT_EMAIL);
        String lastName = req.getParameter(Fields.APPLICANT_LAST_NAME);
        String firstName = req.getParameter(Fields.APPLICANT_FIRST_NAME);
        String city = req.getParameter(Fields.APPLICANT_CITY);
        String region = req.getParameter(Fields.APPLICANT_REGION);
        String educationalInstitution = req.getParameter(Fields.APPLICANT_EDUC_INST);
        List<String> errorMessages = DataValidator.validateRegistrationForm(userName, password, rePassword, email,
                lastName, firstName, city, region, educationalInstitution);
        try {
            boolean checkUsername = srv.checkUsername(userName);
            if (errorMessages == null && !checkUsername) {
                String encodedPass = PassCrypt.encodeWithoutPadding(password.getBytes());
                Applicant applicant = new Applicant(0, userName, encodedPass, lastName, firstName,
                        email, city, region, educationalInstitution);
                boolean insertResult = srv.add(applicant);
                if (insertResult) {
                    httpSession.setAttribute(Messages.MESSAGE, Messages.REGISTRATION_SUCCESSFUL);
                    Sender s = new MailSender(email, MailText.REG_SUBJ.getText(),
                            String.format(MailText.REG_BODY.getText(), userName, password));
                    s.send();
                    resp.sendRedirect(LOGIN);
                }
            } else {
                String[] inputValues = {userName, null, null, email, lastName, firstName, city, region, educationalInstitution};
                List<String> validValues = DataValidator.getValidatedValues(inputValues, errorMessages);
                log.debug(errorMessages);
                httpSession.setAttribute("validValues", validValues);
                httpSession.setAttribute("errors", errorMessages);
                if (errorMessages.get(0) == null && checkUsername) {
                    log.debug(Messages.LOGIN_NOT_UNIQUE);
                    httpSession.setAttribute("error", Messages.LOGIN_NOT_UNIQUE);
                }
                resp.sendRedirect(getServletContext().getContextPath() + Pages.REGISTER);
            }
        } catch (ServiceException | SenderException e) {
            log.error("Error while registration {}", e.getMessage());
            httpSession.setAttribute(Messages.MESSAGE, Messages.REGISTRATION_FAIL);
            resp.sendRedirect(getServletContext().getContextPath() + Pages.REGISTER);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session.getAttribute(Fields.ID) == null) {
            req.getRequestDispatcher(Pages.REGISTER)
                    .forward(req, resp);
        } else {
            req.getRequestDispatcher(Pages.INDEX)
                    .forward(req, resp);
        }
    }
}