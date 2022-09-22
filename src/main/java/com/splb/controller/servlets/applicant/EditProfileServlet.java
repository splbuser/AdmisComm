package com.splb.controller.servlets.applicant;

import com.splb.controller.pages.Messages;
import com.splb.controller.pages.Pages;
import com.splb.model.dao.constant.Fields;
import com.splb.model.entity.Applicant;
import com.splb.service.ApplicantService;
import com.splb.service.exceptions.UserServiceException;
import com.splb.service.utils.DataValidator;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.util.Objects.nonNull;

public class EditProfileServlet extends HttpServlet {

    private static final Logger log = LogManager.getLogger(EditProfileServlet.class);
    private static final String UPLOAD_DIRECTORY = "images/";
    public static final String DELETE = "Delete";
    public static final String USERINFO = "userinfo";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        try {
            int id = (Integer) session.getAttribute(Fields.ID);
            Applicant user = new ApplicantService().get(id);
            request.setAttribute(USERINFO, user);
            request.getRequestDispatcher(Pages.USER_INFO_EDIT)
                    .forward(request, response);
        } catch (UserServiceException e) {
            log.error(e.getMessage());
            request.getRequestDispatcher(Pages.ERROR)
                    .forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Applicant user = (Applicant) session.getAttribute(Fields.USER);
        ApplicantService srv = new ApplicantService();
        int id = (Integer) session.getAttribute(Fields.ID);
        String lastName = request.getParameter(Fields.APPLICANT_LAST_NAME);
        String firstName = request.getParameter(Fields.APPLICANT_FIRST_NAME);
        String city = request.getParameter(Fields.APPLICANT_CITY);
        String region = request.getParameter(Fields.APPLICANT_REGION);
        String eduInst = request.getParameter(Fields.APPLICANT_EDUC_INST);
        String upl = request.getParameter(Fields.APPLICANT_UPLOAD_STATUS);
        List<String> errorMessages = DataValidator.validateEditForm(lastName, firstName, city, region, eduInst);
        try {
            if (nonNull(upl) && upl.equals(DELETE)) {
                String filePath = getServletContext().getRealPath("") + UPLOAD_DIRECTORY;
                String fileName = srv.get(id).getUploaded();
                Files.delete(Path.of(filePath + fileName));
                srv.upload(id, null);
                user.setUploaded(null);
            }
            if (errorMessages == null) {
                user.setId(id);
                user.setLastName(lastName);
                user.setFirstName(firstName);
                user.setCity(city);
                user.setRegion(region);
                user.setEducationalInstitution(eduInst);
                srv.update(user);
                response.sendRedirect(request.getContextPath() + Pages.USER_INFO_SHOW);
            } else {
                String[] inputValues = {null, null, null, null, lastName, firstName, city, region, eduInst};
                List<String> validValues = DataValidator.getValidatedValues(inputValues, errorMessages);
                log.debug(errorMessages);
                session.setAttribute(Fields.VALID_VALUES, validValues);
                session.setAttribute(Messages.ERRORS, errorMessages);
                response.sendRedirect(request.getContextPath() + Pages.EDITINFO);
            }
        } catch (UserServiceException | IOException e) {
            log.error("Error while updating");
            response.sendRedirect(request.getContextPath() + Pages.ERROR);
        }
    }
}