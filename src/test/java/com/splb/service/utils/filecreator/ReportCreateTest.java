package com.splb.service.utils.filecreator;

import com.splb.model.dao.connection.DirectConnectionBuilder;
import com.splb.service.EnrollmentService;
import com.splb.service.exceptions.EnrollmentServiceException;
import com.splb.service.utils.filecreator.factory.FileType;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ReportCreateTest {

    static EnrollmentService srv;
    static HttpServletResponse response;


    @BeforeAll
    static void setUp() throws IOException {
        srv = new EnrollmentService(new DirectConnectionBuilder());
        response = mock(HttpServletResponse.class);
        ServletOutputStream writer = new ServletOutputStream() {
            @Override
            public void write(int b) throws IOException {
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setWriteListener(WriteListener writeListener) {
            }
        };
        when(response.getOutputStream()).thenReturn(writer);
    }

    @Test
    void createFile() {
        try {
            srv.getReport(response, FileType.CREATE_DOC);
            srv.getReport(response, FileType.CREATE_PDF);
            srv.getReport(response, FileType.CREATE_XLS);
        } catch (EnrollmentServiceException e) {
            e.printStackTrace();
        }
    }
}