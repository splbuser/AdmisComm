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

public class RegisterServlet extends HttpServlet {

    private static final Logger log = LogManager.getLogger(RegisterServlet.class);


    @Override
    public void init() throws ServletException {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ApplicantService srv = new ApplicantService();
        /* Geta input data from register form */
        String userName = req.getParameter(Fields.APPLICANT_USER_NAME);
        String password = req.getParameter(Fields.APPLICANT_PASSWORD);
        String rePassword = req.getParameter(Fields.PASSWORD_REPEAT);
        String email = req.getParameter(Fields.APPLICANT_EMAIL);
        String firstName = req.getParameter(Fields.APPLICANT_FIRST_NAME);
        String lastName = req.getParameter(Fields.APPLICANT_LAST_NAME);
        String city = req.getParameter(Fields.APPLICANT_CITY);
        String region = req.getParameter(Fields.APPLICANT_REGION);
        String educationalInstitution = req.getParameter(Fields.APPLICANT_EDUC_INST);

        /* Create session */
        HttpSession httpSession = req.getSession();

        /* input data validation */
        if (
                DataValidator.validateUserName(userName) ||
                        DataValidator.validatePassword(password) ||
                        DataValidator.validateRePassword(password, rePassword) ||
                        DataValidator.validateEmail(email) ||
                        DataValidator.validateName(firstName) ||
                        DataValidator.validateName(lastName) ||
                        DataValidator.validateName(city) ||
                        DataValidator.validateName(region) ||
                        DataValidator.validateName(educationalInstitution)
        ) {
            /* send password for encoding */
            String encodedPass = PassCrypt.encodeWithoutPadding(password.getBytes());

            Applicant applicant = new Applicant(0, userName, encodedPass, firstName, lastName,
                    email, city, region, educationalInstitution);

            boolean insertResult = false;

            try {
                insertResult = srv.add(applicant);
                if (insertResult) {
                    httpSession.setAttribute(Messages.MESSAGE, Messages.REGISTRATION_SUCCESSFUL);
                    Sender s = new MailSender(email,  MailText.REG_SUBJ.getText(), MailText.REG_BODY.getText());
                    s.send();
                    resp.sendRedirect("Login");
                }
            } catch (ServiceException | SenderException e) {
                log.error("Error while registration {}", e.getMessage());
                httpSession.setAttribute(Messages.MESSAGE, Messages.REGISTRATION_FAIL);
                resp.sendRedirect(getServletContext().getContextPath() + Pages.ERROR);
            }

        } else {
            log.info(Messages.VALIDATION_FAIL);
            httpSession.setAttribute(Messages.MESSAGE, Messages.VALIDATION_FAIL);
            resp.sendRedirect(getServletContext().getContextPath() + Pages.ERROR);
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session.getAttribute(Fields.ID) == null) {
            getServletContext().getRequestDispatcher(Pages.REGISTER)
                    .forward(req, resp);
        } else {
            getServletContext().getRequestDispatcher(Pages.INDEX)
                    .forward(req, resp);
        }
    }
}