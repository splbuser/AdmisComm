package com.splb.controller.servlets.resetpass;

import com.splb.controller.pages.Messages;
import com.splb.controller.pages.Pages;
import com.splb.model.dao.constant.Fields;
import com.splb.service.ApplicantService;
import com.splb.service.utils.DataValidator;
import com.splb.service.utils.PassCrypt;
import com.splb.service.utils.notifier.MailSender;
import com.splb.service.utils.notifier.MailText;
import com.splb.service.utils.notifier.Sender;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Serial;

import static java.util.Objects.nonNull;

public class SetNewPasswordServlet extends HttpServlet {

    private static final Logger log = LogManager.getLogger(SetNewPasswordServlet.class);

    @Serial
    private static final long serialVersionUID = 1L;
    public static final String SUCCESS = "Password reset success";
    public static final String FAILED = "Password reset failed";
    public static final String CONF_PASSWORD = "confPassword";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute(Fields.APPLICANT_EMAIL);
        String newPass = request.getParameter(Fields.APPLICANT_PASSWORD);
        String rePass = request.getParameter(CONF_PASSWORD);
        if (nonNull(newPass) && nonNull(rePass) &&
                DataValidator.validatePassword(newPass) &&
                newPass.equals(rePass)) {
            try {
                String encodedPass = PassCrypt.encodeWithoutPadding(newPass.getBytes());
                int i = new ApplicantService().changePassword(email, encodedPass);
                if (i > 0) {
                    session.setAttribute(Messages.MESSAGE, SUCCESS);
                    log.info(SUCCESS);
                    Sender s = new MailSender(email, MailText.RES_PASS_SUC_SUBJ.getText(),
                            String.format(MailText.RES_PASS_SUC_BODY.getText(), newPass));
                    s.send();
                } else {
                    session.setAttribute(Messages.MESSAGE, FAILED);
                    log.info(FAILED);
                }
                request.getRequestDispatcher(Pages.LOGIN)
                        .forward(request, response);
            } catch (Exception e) {
                log.error(e.getMessage());
                response.sendRedirect(Pages.ERROR);
            }
        } else {
            request.setAttribute(Messages.MESSAGE, Messages.TRY_ANOTHER_ONE);
            request.getRequestDispatcher(Pages.NEW_PASSWORD).forward(request, response);
        }
    }
}
