package com.ufro.dci.etransparency.etransparency_api_user.controllers.administrator;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufro.dci.etransparency.etransparency_api_user.TransparencyApiUserApplication;
import com.ufro.dci.etransparency.etransparency_api_user.config.security.utils.JwtUtils;
import com.ufro.dci.etransparency.etransparency_api_user.dtos.administrator.AdministratorDTO;
import com.ufro.dci.etransparency.etransparency_api_user.dtos.administrator.AdministratorUpdateDTO;
import com.ufro.dci.etransparency.etransparency_api_user.services.administrator.AdministratorCrudService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(classes = TransparencyApiUserApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource(locations="classpath:test.properties")
class AdministratorCrudControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdministratorCrudService administratorCrudService;

    @MockBean
    private JwtUtils jwtUtils;

    @Autowired
    private ObjectMapper objectMapper;

    private String jsonContent = "{"
            + "\"username\": \"validuser\","
            + "\"email\": \"validuser@example.com\","
            + "\"password\": \"Password1!\","
            + "\"phoneNumber\": \"+56912345678\""
            + "}";

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetAdministratorAsAdmin() throws Exception {
        Mockito.when(administratorCrudService.getAdministrator(1L)).thenReturn(new AdministratorDTO());

        mockMvc.perform(get("/admins/1")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer dummy_token")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    void testGetAdministratorAsManager() throws Exception {
        mockMvc.perform(get("/admins/1")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer dummy_token")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testCreateAdministratorAsAdmin() throws Exception {
        Mockito.when(administratorCrudService.createAdministrator(new AdministratorDTO())).thenReturn(new AdministratorDTO());

        mockMvc.perform(post("/admins")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer dummy_token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    void testCreateAdministratorAsManager() throws Exception {
        mockMvc.perform(post("/admins")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer dummy_token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testUpdateAdministratorAsAdmin() throws Exception {
        Mockito.when(administratorCrudService.updateAdministrator(1L, objectMapper.readValue(jsonContent, AdministratorUpdateDTO.class)))
                .thenReturn(objectMapper.readValue(jsonContent, AdministratorDTO.class));

        mockMvc.perform(patch("/admins/1")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer dummy_token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.username").value("validuser"))
                .andExpect(jsonPath("$.data.email").value("validuser@example.com"));
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    void testUpdateAdministratorAsManager() throws Exception {
        mockMvc.perform(patch("/admins/1")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer dummy_token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testToggleAdministratorStatusAsAdmin() throws Exception {
        Mockito.when(administratorCrudService.toggleAdministratorStatus(1L)).thenReturn(new AdministratorDTO());

        mockMvc.perform(patch("/admins/1/status")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer dummy_token")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    void testToggleAdministratorStatusAsManager() throws Exception {
        mockMvc.perform(patch("/admins/1/status")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer dummy_token")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
}
