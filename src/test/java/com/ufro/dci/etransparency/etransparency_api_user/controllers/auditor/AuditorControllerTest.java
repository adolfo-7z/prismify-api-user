package com.ufro.dci.etransparency.etransparency_api_user.controllers.auditor;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.ufro.dci.etransparency.etransparency_api_user.TransparencyApiUserApplication;
import com.ufro.dci.etransparency.etransparency_api_user.config.security.utils.JwtUtils;
import com.ufro.dci.etransparency.etransparency_api_user.services.auditor.AuditorService;

import org.springframework.security.test.context.support.WithMockUser;

import java.util.HashMap;

@SpringBootTest(classes = TransparencyApiUserApplication.class)
@AutoConfigureMockMvc
public class AuditorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuditorService auditorService;

    @MockBean
    private JwtUtils jwtUtils;

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGetAllAuditors() throws Exception {
        Mockito.when(auditorService.getAllAuditors(0, 12, "asc", ""))
                .thenReturn( new HashMap<String, Object>());
        mockMvc.perform(get("/auditors")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer dummy_token")
                        .param("page", "0")
                        .param("size", "12")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    public void testGetAllAuditorsForbidden() throws Exception {
        mockMvc.perform(get("/auditors")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer dummy_token")
                        .param("page", "0")
                        .param("size", "12")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$").isNotEmpty());
    }
}
