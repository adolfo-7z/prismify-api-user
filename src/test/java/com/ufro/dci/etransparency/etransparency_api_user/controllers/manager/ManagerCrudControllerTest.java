package com.ufro.dci.etransparency.etransparency_api_user.controllers.manager;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufro.dci.etransparency.etransparency_api_user.TransparencyApiUserApplication;
import com.ufro.dci.etransparency.etransparency_api_user.config.security.utils.JwtUtils;
import com.ufro.dci.etransparency.etransparency_api_user.dtos.manager.ManagerDTO;
import com.ufro.dci.etransparency.etransparency_api_user.dtos.manager.ManagerUpdateDTO;
import com.ufro.dci.etransparency.etransparency_api_user.services.manager.ManagerCrudService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(classes = TransparencyApiUserApplication.class)
@AutoConfigureMockMvc
class ManagerCrudControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ManagerCrudService managerCrudService;

    @MockBean
    private JwtUtils jwtUtils;

    @Autowired
    private ObjectMapper objectMapper;

    private String jsonContent = "{"
            + "\"username\": \"validuser\","
            + "\"email\": \"validuser@example.com\","
            + "\"password\": \"Password1!\","
            + "\"position\": \"Manager Position\","
            + "\"rut\": \"12345678-9\","
            + "\"phoneNumber\": \"+56912345678\""
            + "}";

    @Test
    @WithMockUser(roles = {"ADMIN", "MANAGER"})
    void testGetManagerAsAdmin() throws Exception {
        Mockito.when(managerCrudService.getManager(1L)).thenReturn(new ManagerDTO());

        mockMvc.perform(get("/managers/1")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer dummy_token")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testCreateManagerAsAdmin() throws Exception {
        Mockito.when(managerCrudService.createManager(new ManagerDTO())).thenReturn(new ManagerDTO());

        mockMvc.perform(post("/managers")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer dummy_token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    void testCreateManagerAsManager() throws Exception {
        mockMvc.perform(post("/managers")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer dummy_token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "MANAGER"})
    void testUpdateManagerAsAdmin() throws Exception {
        Mockito.when(managerCrudService.updateManager(1L, objectMapper.readValue(jsonContent, ManagerUpdateDTO.class)))
                .thenReturn(objectMapper.readValue(jsonContent, ManagerDTO.class));

        mockMvc.perform(patch("/managers/1")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer dummy_token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.position").value("Manager Position"))
                .andExpect(jsonPath("$.data.rut").value("12345678-9"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testToggleManagerStatusAsAdmin() throws Exception {
        Mockito.when(managerCrudService.toggleManagerStatus(1L)).thenReturn(new ManagerDTO());

        mockMvc.perform(patch("/managers/1/status")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer dummy_token")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    void testToggleManagerStatusAsManager() throws Exception {
        mockMvc.perform(patch("/managers/1/status")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer dummy_token")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
}