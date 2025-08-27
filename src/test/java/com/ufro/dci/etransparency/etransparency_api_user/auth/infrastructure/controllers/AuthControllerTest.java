package com.ufro.dci.etransparency.etransparency_api_user.auth.infrastructure.controllers;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.ufro.dci.etransparency.etransparency_api_user.auth.application.services.AuthService;
import com.ufro.dci.etransparency.etransparency_api_user.auth.domain.ports.out.TokenProvider;
import com.ufro.dci.etransparency.etransparency_api_user.auth.infrastructure.controllers.dto.AuthRequestDTO;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthService authService;

    @MockitoBean
    private TokenProvider tokenProvider;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("login_ShouldReturnToken_WhenCredentialsAreValid")
    void login_ShouldReturnToken_WhenCredentialsAreValid() throws Exception {
        AuthRequestDTO request = new AuthRequestDTO();
        request.setUsername("techpriest");
        request.setPassword("sacredpassword");
        String mockToken = "BearerMachineSpiritToken";
        Mockito.when(authService.login(anyString(), anyString()))
                .thenReturn(mockToken);
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(mockToken));
    }

}
