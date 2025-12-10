package prismify.auth.infrastructure.controllers;

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

import prismify.auth.application.services.AuthService;
import prismify.auth.domain.ports.out.TokenProvider;
import prismify.auth.infrastructure.controllers.dto.AuthRequestDTO;

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
        request.setIdentifier("username");
        request.setPassword("password");
        String mockToken = "BearerToken";
        Mockito.when(authService.login(anyString(), anyString()))
                .thenReturn(mockToken);
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.token").value(mockToken));
    }

}
