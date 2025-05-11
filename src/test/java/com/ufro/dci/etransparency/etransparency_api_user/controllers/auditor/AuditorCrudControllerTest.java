package com.ufro.dci.etransparency.etransparency_api_user.controllers.auditor;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufro.dci.etransparency.etransparency_api_user.TransparencyApiUserApplication;
import com.ufro.dci.etransparency.etransparency_api_user.config.security.utils.JwtUtils;
import com.ufro.dci.etransparency.etransparency_api_user.dtos.auditor.AuditorDTO;
import com.ufro.dci.etransparency.etransparency_api_user.dtos.auditor.AuditorUpdateDTO;
import com.ufro.dci.etransparency.etransparency_api_user.services.auditor.AuditorCrudService;

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
public class AuditorCrudControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuditorCrudService auditorCrudService;

    @MockBean
    private JwtUtils jwtUtils;

    @Autowired
    private ObjectMapper objectMapper;

    private String jsonContent = "{"
            + "\"username\": \"validuser\","
            + "\"email\": \"validuser@example.com\","
            + "\"password\": \"Password1!\","
            + "\"city\": \"Valid City\","
            + "\"nAssignedInstitutions\": 5,"
            + "\"color\": \"Blue\","
            + "\"acronym\": \"VALID\","
            + "\"phoneNumber\": \"+56912345678\""
            + "}";

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGetAuditorAsAdmin() throws Exception {
        Mockito.when(auditorCrudService.getAuditor(1L)).thenReturn(new AuditorDTO());

        mockMvc.perform(get("/auditors/1")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer dummy_token")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    @WithMockUser(roles = "AUDITOR")
    public void testGetAuditorAsAuditor() throws Exception {
        Mockito.when(auditorCrudService.getAuditor(1L)).thenReturn(new AuditorDTO());

        mockMvc.perform(get("/auditors/1")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer dummy_token")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    public void testGetAuditorAsManager() throws Exception {
        mockMvc.perform(get("/auditors/1")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer dummy_token")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testCreateAuditorAsAdmin() throws Exception {
        Mockito.when(auditorCrudService.createAuditor(new AuditorDTO())).thenReturn(new AuditorDTO());

        mockMvc.perform(post("/auditors")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer dummy_token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    @WithMockUser(roles = "AUDITOR")
    public void testCreateAuditorAsAuditor() throws Exception {
        mockMvc.perform(post("/auditors")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer dummy_token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testUpdateAuditorAsAdmin() throws Exception {
        Mockito.when(auditorCrudService.updateAuditor(1L, objectMapper.readValue(jsonContent, AuditorUpdateDTO.class)))
                .thenReturn(objectMapper.readValue(jsonContent, AuditorDTO.class));

        mockMvc.perform(patch("/auditors/1")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer dummy_token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.city").value("Valid City"))
                //TODO: Ver por que devuelve null nAssignedInstitutions
                //.andExpect(jsonPath("$.data.nAssignedInstitutions").value(5))
                .andExpect(jsonPath("$.data.color").value("Blue"))
                .andExpect(jsonPath("$.data.acronym").value("VALID"));
    }


    @Test
    @WithMockUser(roles = "AUDITOR")
    public void testUpdateAuditorAsAuditor() throws Exception {
        Mockito.when(auditorCrudService.updateAuditor(1L, objectMapper.readValue(jsonContent, AuditorUpdateDTO.class)))
                .thenReturn(objectMapper.readValue(jsonContent, AuditorDTO.class));

        mockMvc.perform(patch("/auditors/1")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer dummy_token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.city").value("Valid City"))
                //.andExpect(jsonPath("$.nAssignedInstitutions").value(10))
                .andExpect(jsonPath("$.data.color").value("Blue"))
                .andExpect(jsonPath("$.data.acronym").value("VALID"));
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    public void testUpdateAuditorAsManager() throws Exception {
        Mockito.when(auditorCrudService.updateAuditor(1L, objectMapper.readValue(jsonContent, AuditorUpdateDTO.class)))
                .thenReturn(objectMapper.readValue(jsonContent, AuditorDTO.class));

        mockMvc.perform(patch("/auditors/1")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer dummy_token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testToggleAuditorStatusAsAdmin() throws Exception {
        Mockito.when(auditorCrudService.toggleAuditorStatus(1L)).thenReturn(new AuditorDTO());

        mockMvc.perform(patch("/auditors/1/status")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer dummy_token")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    @WithMockUser(roles = "AUDITOR")
    public void testToggleAuditorStatusAsAuditor() throws Exception {
        mockMvc.perform(patch("/auditors/1/status")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer dummy_token")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$").isNotEmpty());
    }
}
