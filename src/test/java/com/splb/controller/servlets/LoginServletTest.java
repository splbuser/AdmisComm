package com.splb.controller.servlets;

import com.splb.controller.pages.Pages;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.Mockito.*;

class LoginServletTest {
    final HttpServletRequest request = mock(HttpServletRequest.class);
    final HttpServletResponse response = mock(HttpServletResponse.class);
    final ServletContext contest = mock(ServletContext.class);
    final RequestDispatcher dispatcher = mock(RequestDispatcher.class);

    @Test
    void whenCallDoGetThenServletReturnFacultyCreatePage() throws ServletException, IOException {
        final LoginServlet servlet = new LoginServlet();
        when(request.getServletContext()).thenReturn(contest);
        when(request.getRequestDispatcher(Pages.LOGIN_PAGE)).thenReturn(dispatcher);
        servlet.doGet(request, response);
        verify(request, times(1)).getRequestDispatcher(Pages.LOGIN_PAGE);
        verify(request, never()).getSession();
        verify(dispatcher).forward(request, response);
    }
}