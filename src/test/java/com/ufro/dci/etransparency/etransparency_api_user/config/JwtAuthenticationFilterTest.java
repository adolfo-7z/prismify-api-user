package com.ufro.dci.etransparency.etransparency_api_user.config;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;

import com.ufro.dci.etransparency.etransparency_api_user.config.security.filters.JwtAuthenticationFilter;
import com.ufro.dci.etransparency.etransparency_api_user.config.security.utils.JwtUtils;

 class JwtAuthenticationFilterTest {

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private Authentication auth;

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @BeforeEach
     void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtUtils);
    }


    @Test
     void testAttemptAuthenticationFailure() throws IOException {
        when(request.getInputStream()).thenThrow(new IOException("Unable to read input stream"));

        Exception exception = assertThrows(AuthenticationServiceException.class, () -> {
            jwtAuthenticationFilter.attemptAuthentication(request, response);
        });

        assertEquals("Unable to authenticate user", exception.getMessage());
    }
    


}
