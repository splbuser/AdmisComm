package com.splb.controller.servlets.applicant;

import com.splb.controller.pages.Messages;
import com.splb.controller.pages.Pages;
import com.splb.model.dao.constant.Fields;
import com.splb.model.entity.Applicant;
import com.splb.service.ApplicantService;
import com.splb.service.exceptions.UserServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.io.*;

import static java.util.Objects.nonNull;

//@WebServlet(name = "FileUploadServlet", urlPatterns = {"/upload"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 100
)

public class FileUploadServlet extends HttpServlet {

    private static final Logger log = LogManager.getLogger(FileUploadServlet.class);
    private static final String UPLOAD_DIRECTORY = "images";
    public static final String FILE = "file";
    public static final String WRONG_INPUT_FILE = "Wrong input file";

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Applicant user = (Applicant) session.getAttribute(Fields.USER);
        String username = user.getUserName();
        int id = user.getId();
        String uploadPath = getServletContext().getRealPath("") + UPLOAD_DIRECTORY;
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdir();
        Part filePart = request.getPart(FILE);

        String extension = filePart.getSubmittedFileName()
                .substring(filePart.getSubmittedFileName().length() - 4);
        for (Part part : request.getParts()) {
            part.write(String.format("%s%s%s_%d%s", uploadPath, File.separator, username, id, extension));
        }
        try {
            new ApplicantService().upload(id, String.format("%s_%d%s", username, id, extension));
            user.setUploaded(String.format("%s_%d%s", username, id, extension));
        } catch (UserServiceException e) {
            log.error(e.getMessage());
            session.setAttribute(Messages.MESSAGE, WRONG_INPUT_FILE);
            response.sendRedirect(getServletContext().getContextPath() + Pages.ERROR);
        }
        response.sendRedirect(getServletContext().getContextPath() + Pages.USER_INFO_SHOW);

    }
}