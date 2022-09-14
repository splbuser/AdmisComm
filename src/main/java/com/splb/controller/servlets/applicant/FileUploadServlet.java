package com.splb.controller.servlets.applicant;

import com.splb.controller.pages.Pages;
import com.splb.model.dao.constant.Fields;
import com.splb.model.entity.Applicant;
import com.splb.service.ApplicantService;
import com.splb.service.exceptions.UserServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.*;

@WebServlet(name = "FileUploadServlet", urlPatterns = {"/upload"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 100
)

public class FileUploadServlet extends HttpServlet {

    private static final String UPLOAD_DIRECTORY = "images";

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ApplicantService srv = new ApplicantService();
        HttpSession session = request.getSession();
        Applicant user = (Applicant) session.getAttribute(Fields.USER);
        String username = user.getUserName();
        int id = user.getId();
        String uploadPath = getServletContext().getRealPath("") + UPLOAD_DIRECTORY;
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdir();
        Part filePart = request.getPart("file");
        String extension = filePart.getSubmittedFileName()
                .substring(filePart.getSubmittedFileName().length() - 4);
        for (Part part : request.getParts()) {
            part.write(String.format("%s%s%s_%d%s", uploadPath, File.separator, username, id, extension));
        }
        try {
            srv.upload(id, String.format("%s_%d%s", username, id, extension));
            user.setUploaded(String.format("%s_%d%s", username, id, extension));
        } catch (UserServiceException e) {
            response.sendRedirect(getServletContext().getContextPath() + Pages.ERROR);
        }
        response.sendRedirect(getServletContext().getContextPath() + "/Userinfo");
    }
}