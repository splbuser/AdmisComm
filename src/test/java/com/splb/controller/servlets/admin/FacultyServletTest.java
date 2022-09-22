package com.splb.controller.servlets.admin;

import com.splb.controller.pages.Pages;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.Mockito.*;

class FacultyServletTest {

//    private final static String FACULTY_CREATE = "/jsp/faculty-create.jsp";

    final HttpSession session = mock(HttpSession.class);
    final HttpServletRequest request = mock(HttpServletRequest.class);
    final HttpServletResponse response = mock(HttpServletResponse.class);
    final ServletContext contest = mock(ServletContext.class);
    final RequestDispatcher dispatcher = mock(RequestDispatcher.class);
//    final EnrollmentService srv = mock(EnrollmentService.class);
//    final ApplicantService asrv = mock(ApplicantService.class);
//    static EnrollmentService srv;
//    static ApplicantService asrv;

//    @BeforeAll
//   static void setUp()  {
//        srv = new EnrollmentService(new com.splb.model.dao.connection.DirectConnectionBuilder());
//        asrv = new ApplicantService(new com.splb.model.dao.connection.DirectConnectionBuilder());
//    }

    @Test
    void whenCallDoGetThenServletReturnFacultyCreatePage() throws ServletException, IOException {
        final CreateFacultyServlet servlet = new CreateFacultyServlet();
        when(request.getServletContext()).thenReturn(contest);
        when(request.getRequestDispatcher(Pages.FACULTY_CREATE)).thenReturn(dispatcher);
        servlet.doGet(request, response);
        verify(request, times(1)).getRequestDispatcher(Pages.FACULTY_CREATE);
        verify(request, never()).getSession();
        verify(dispatcher).forward(request, response);
    }


//    @Test
//    void whenCallDoGetThenServletReturnEnrollmentPage() throws ServletException, IOException, UserServiceException, EnrollmentServiceException {
//
//        final DisplayEnrollmentServlet servlet = new DisplayEnrollmentServlet();
//
//        when(request.getSession()).thenReturn(session);
//        when(request.getServletContext()).thenReturn(contest);
//        when(new EnrollmentService()).thenReturn(srv);
//        when(new ApplicantService()).thenReturn(asrv);
//        when(request.getRequestDispatcher(Pages.ENROLLMENT_PAGE)).thenReturn(dispatcher);
//
//        servlet.doGet(request, response);
//
//        verify(request, times(1)).getRequestDispatcher(Pages.ENROLLMENT_PAGE);
//        verify(request, never()).getSession();
//        verify(dispatcher).forward(request, response);
//    }



}