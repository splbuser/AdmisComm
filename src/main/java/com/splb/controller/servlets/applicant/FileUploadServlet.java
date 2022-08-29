package com.splb.controller.servlets.applicant;

import com.splb.controller.pages.Pages;
import com.splb.model.entity.Applicant;
import com.splb.service.ApplicantService;
import com.splb.service.exceptions.UserServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.io.*;
import java.util.List;

import static java.util.Objects.nonNull;

@WebServlet(name = "FileUploadServlet", urlPatterns = {"/upload"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 100
)

public class FileUploadServlet extends HttpServlet {

    private static final String UPLOAD_DIRECTORY = "images";
//    private static final String ID = "155";

//    private static final int MEMORY_THRESHOLD = 1024 * 1024;
//    private static final long MAX_REQUEST_SIZE = 1024 * 1024 * 10;
//    private static final int MAX_FILE_SIZE = 1024 * 1024 * 100;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ApplicantService srv = new ApplicantService();

        HttpSession session = request.getSession();
        Applicant user = (Applicant) session.getAttribute("user");
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
//        response.getWriter().print("The file uploaded successfully.");
        response.sendRedirect(getServletContext().getContextPath() + "/Userinfo");
/////////////////////////////////////////////////////////////////////////////
//        if (FileUploadBase.isMultipartContent((RequestContext) request)) {
//
//            DiskFileItemFactory factory = new DiskFileItemFactory();
//            factory.setSizeThreshold(MEMORY_THRESHOLD);
//            factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
//
//            ServletFileUpload upload = new ServletFileUpload(factory);
//            upload.setFileSizeMax(MAX_FILE_SIZE);
//            upload.setSizeMax(MAX_REQUEST_SIZE);
//            String uploadPath = getServletContext().getRealPath("")
//                    + File.separator + UPLOAD_DIRECTORY;
//            File uploadDir = new File(uploadPath);
//            if (!uploadDir.exists()) {
//                uploadDir.mkdir();
//            }
//            try {
//                List<FileItem> formItems = upload.parseRequest((RequestContext) request);
//                if (nonNull(formItems ) && !formItems.isEmpty()) {
//                    for (FileItem item : formItems) {
//                        if (!item.isFormField()) {
//                            String fileName = new File(item.getName()).getName();
//                            String filePath = uploadPath + File.separator + fileName;
//                            File storeFile = new File(filePath);
//                            item.write(storeFile);
//                            request.setAttribute("message", "File "
//                                    + fileName + " has uploaded successfully!");
//                        }
//                    }
//                }
//            } catch (Exception e) {
//                throw new IOException(e.getMessage());
//            }
//        }
    }
}