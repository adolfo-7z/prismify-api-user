package com.ufro.dci.etransparency.etransparency_api_user.config.security.handler;

import java.io.IOException;
import java.io.PrintWriter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException accessDeniedException)
            throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();
        writer.write("{\"errorCode\": \"Access Denied\", \"message\": \"" + accessDeniedException.getMessage()
                + "\", \"status\": " + HttpServletResponse.SC_FORBIDDEN + "}");
        writer.flush();
        writer.close();
    }

}
