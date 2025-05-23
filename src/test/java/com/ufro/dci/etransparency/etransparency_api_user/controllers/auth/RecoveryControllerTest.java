package com.ufro.dci.etransparency.etransparency_api_user.controllers.auth;

import java.util.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.ufro.dci.etransparency.etransparency_api_user.TransparencyApiUserApplication;
import com.ufro.dci.etransparency.etransparency_api_user.services.auth.RecoveryService;

@SpringBootTest(classes = TransparencyApiUserApplication.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@TestPropertySource(locations="classpath:test.properties")
public class RecoveryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecoveryService recoveryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testRequestRecoveryCode() throws Exception {

        String email = "prueba@ejemplo.cl";

        when(recoveryService.sendRecoveryCode(email)).thenReturn("Recovery code sent");

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("email", email);

        mockMvc.perform(post("/recovery/code")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Operation successful"))
                .andExpect(jsonPath("$.data").value("Recovery code sent"));

    }

    @Test
    void testValidateRecoveryCode() throws Exception {
        String email = "prueba@ejemplo.cl";
        String recoveryCode = "123456";

        Mockito.when(recoveryService.validateRecoveryCode(email, recoveryCode)).thenReturn("Recovery code successfully validated");

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("email", email);
        requestBody.put("recoveryCode", recoveryCode);

        mockMvc.perform(post("/recovery/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Operation successful"))
                .andExpect(jsonPath("$.data").value("Recovery code successfully validated"));
    }

    @Test
    void testValidateNewPassword() throws Exception {
        String email = "prueba@ejemplo.cl";
        String password = "NuevaContraseña123!";
        String validationPassword = "NuevaContraseña123!";

        Mockito.when(recoveryService.validateNewPassword(email, password, validationPassword))
                .thenReturn("Password successfully updated");

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("email", email);
        requestBody.put("password", password);
        requestBody.put("validationPassword", validationPassword);

        mockMvc.perform(post("/recovery/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Operation successful"))
                .andExpect(jsonPath("$.data").value("Password successfully updated"));
    }

}
