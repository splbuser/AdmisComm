package com.splb.controller.servlets.resetpass;

import com.splb.controller.pages.Pages;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.Mockito.*;

class ForgotPasswordServletTest {

    final HttpServletRequest request = mock(HttpServletRequest.class);
    final HttpServletResponse response = mock(HttpServletResponse.class);
    final ServletContext contest = mock(ServletContext.class);
    final RequestDispatcher dispatcher = mock(RequestDispatcher.class);

    @Test
    void whenCallDoGetThenServletReturnForgotPasswordPage() throws ServletException, IOException {
        final ForgotPasswordServlet servlet = new ForgotPasswordServlet();
        when(request.getServletContext()).thenReturn(contest);
        when(request.getRequestDispatcher(Pages.FORGOT_PASSWORD)).thenReturn(dispatcher);
        servlet.doGet(request, response);
        verify(request, times(1)).getRequestDispatcher(Pages.FORGOT_PASSWORD);
        verify(request, never()).getSession();
        verify(dispatcher).forward(request, response);
    }
}