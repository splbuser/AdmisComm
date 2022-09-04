package com.splb.controller.servlets.admin;

import com.splb.model.dao.constant.Fields;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.Mockito.*;

class CreateFacultyServletTest {

    private final static String path = "/jsp/faculty-create.jsp";

    @Test
    void whenCallDoGetThenServletReturnFacultyCreatePage() throws ServletException, IOException {

        final CreateFacultyServlet servlet = new CreateFacultyServlet();

        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final ServletConfig config = mock(ServletConfig.class);
        final ServletContext contest = mock(ServletContext.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(request.getServletContext()).thenReturn(contest);
        when(request.getRequestDispatcher(path)).thenReturn(dispatcher);

        servlet.doGet(request, response);

        verify(request, times(1)).getRequestDispatcher(path);
        verify(request, never()).getSession();
        verify(dispatcher).forward(request, response);
    }

}