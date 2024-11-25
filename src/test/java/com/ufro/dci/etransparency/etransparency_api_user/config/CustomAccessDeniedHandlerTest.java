package com.ufro.dci.etransparency.etransparency_api_user.config;

import static org.mockito.Mockito.*;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.access.AccessDeniedException;

import com.ufro.dci.etransparency.etransparency_api_user.config.security.handler.CustomAccessDeniedHandler;

class CustomAccessDeniedHandlerTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private PrintWriter writer;

    @InjectMocks
    private CustomAccessDeniedHandler accessDeniedHandler;

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        when(response.getWriter()).thenReturn(writer);
    }

    @Test
    void testHandleAccessDenied() throws IOException, ServletException {
        AccessDeniedException exception = new AccessDeniedException("Access is denied");

        accessDeniedHandler.handle(request, response, exception);

        verify(response).setStatus(HttpServletResponse.SC_FORBIDDEN);
        verify(response).setContentType("application/json");

        String expectedResponse = "{\"errorCode\": \"Access Denied\", \"message\": \"Access is denied\", \"status\": 403}";
        verify(writer).write(expectedResponse);
        verify(writer).flush();
        verify(writer).close();
    }

    @Test
    void testHandleCustomMessage() throws IOException, ServletException {
        AccessDeniedException exception = new AccessDeniedException("Custom access denied message");

        accessDeniedHandler.handle(request, response, exception);

        String expectedResponse = "{\"errorCode\": \"Access Denied\", \"message\": \"Custom access denied message\", \"status\": 403}";
        verify(writer).write(expectedResponse);
    }
}
