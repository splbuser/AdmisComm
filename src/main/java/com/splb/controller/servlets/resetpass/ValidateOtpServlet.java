package com.splb.controller.servlets.resetpass;

import com.splb.controller.pages.Messages;
import com.splb.controller.pages.Pages;
import com.splb.model.dao.constant.Fields;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.Serial;

public class ValidateOtpServlet extends HttpServlet {

    @Serial
    private static final long serialVersionUID = 1L;
    public static final String SUCCESS = "success";
    public static final String WRONG_OTP = "Wrong otp";

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String email = request.getParameter(Fields.APPLICANT_EMAIL);
        int value = Integer.parseInt(request.getParameter(Fields.OTP));
        int otp = (int) session.getAttribute(Fields.OTP);
        if (value == otp) {
            request.setAttribute(Fields.APPLICANT_EMAIL, email);
            request.setAttribute(Fields.STATUS, SUCCESS);
            request.getRequestDispatcher(Pages.NEW_PASSWORD)
                    .forward(request, response);
        } else {
            request.setAttribute(Messages.MESSAGE, WRONG_OTP);
            request.getRequestDispatcher(Pages.ENTER_OTP)
                    .forward(request, response);
        }
    }
}