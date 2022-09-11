package com.splb.controller.servlets.applicant;

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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ApplicantService srv = new ApplicantService();
        HttpSession session = request.getSession();
        Applicant user = new Applicant();
        try {
            int id = (Integer) session.getAttribute(Fields.ID);
            user = srv.get(id);
        } catch (UserServiceException e) {
            log.error(e.getMessage());
            request.getRequestDispatcher(Pages.ERROR)
                    .forward(request, response);
        }
        request.setAttribute("userinfo", user);
        request.getRequestDispatcher(Pages.USER_INFO_EDIT)
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Applicant user = (Applicant) session.getAttribute("user");
        ApplicantService srv = new ApplicantService();
        Applicant applicant = new Applicant();
        int id = (Integer) session.getAttribute(Fields.ID);
        String lastName = request.getParameter(Fields.APPLICANT_LAST_NAME);
        String firstName = request.getParameter(Fields.APPLICANT_FIRST_NAME);
        String city = request.getParameter(Fields.APPLICANT_CITY);
        String region = request.getParameter(Fields.APPLICANT_REGION);
        String eduInst = request.getParameter(Fields.APPLICANT_EDUC_INST);
        String upl = request.getParameter(Fields.APPLICANT_UPLOAD_STATUS);
        applicant.setId(id);
        applicant.setLastName(lastName);
        applicant.setFirstName(firstName);
        applicant.setCity(city);
        applicant.setRegion(region);
        applicant.setEducationalInstitution(eduInst);
        List<String> errorMessages = DataValidator.validateEditForm(lastName, firstName, city, region, eduInst);
        try {
            if (nonNull(upl) && upl.equals(DELETE)) {
                String filePath = getServletContext().getRealPath("") + UPLOAD_DIRECTORY;
                String fileName = srv.get(id).getUploaded();
                try {
                Files.delete(Path.of(filePath + fileName));
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
                srv.upload(id, null);
                user.setUploaded(null);
            }
            if (errorMessages == null) {
                srv.update(applicant);
                response.sendRedirect(request.getContextPath() + Pages.USER_INFO_SHOW);
            } else {
                String[] inputValues = {null, null, null, null, lastName, firstName, city, region, eduInst};
                List<String> validValues = DataValidator.getValidatedValues(inputValues, errorMessages);
                log.debug(errorMessages);
                session.setAttribute("validValues", validValues);
                session.setAttribute("errors", errorMessages);
                response.sendRedirect(request.getContextPath() + "/Editinfo");
            }
        } catch (UserServiceException e) {
            log.error("Error while updating");
            response.sendRedirect(request.getContextPath() + Pages.ERROR);
        }
    }
}