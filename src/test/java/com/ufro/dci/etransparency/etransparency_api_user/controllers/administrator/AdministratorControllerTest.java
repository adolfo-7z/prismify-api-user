package com.ufro.dci.etransparency.etransparency_api_user.controllers.administrator;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.ufro.dci.etransparency.etransparency_api_user.TransparencyApiUserApplication;
import com.ufro.dci.etransparency.etransparency_api_user.dtos.administrator.AdministratorDTO;
import com.ufro.dci.etransparency.etransparency_api_user.services.administrator.AdministratorService;

@SpringBootTest(classes = TransparencyApiUserApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource(locations="classpath:test.properties")
class AdministratorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AdministratorService administratorService;

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetAllAdministrators() throws Exception {
        List<AdministratorDTO> expectedAdministrators = Arrays.asList(
                new AdministratorDTO(),
                new AdministratorDTO());

        Long i = 1L;
        for (AdministratorDTO administratorDTO : expectedAdministrators) {
            administratorDTO.setId(i);
            administratorDTO.setUsername("username" + (i++));
        }

        when(administratorService.getAllAdministrators(anyInt(), anyInt())).thenReturn(expectedAdministrators);

        mockMvc.perform(get("/admins")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].username").value(expectedAdministrators.get(0).getUsername()))
                .andExpect(jsonPath("$.data[0].id").value(expectedAdministrators.get(0).getId()))
                .andExpect(jsonPath("$.data[1].username").value(expectedAdministrators.get(1).getUsername()))
                .andExpect(jsonPath("$.data[1].id").value(expectedAdministrators.get(1).getId()));

        verify(administratorService).getAllAdministrators(anyInt(),anyInt());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetAllAdministratorsEmpty() throws Exception {
        List<AdministratorDTO> emptyAdministrators = Collections.emptyList();

        when(administratorService.getAllAdministrators(anyInt(), anyInt())).thenReturn(emptyAdministrators);

        mockMvc.perform(get("/admins")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isEmpty());

        verify(administratorService).getAllAdministrators(anyInt(),anyInt());

    }

    @Test
    void testGetAdminDashboardWithoutToken() throws Exception {
        mockMvc.perform(get("/admins/dashboard-stats")
                        .contentType("application/json"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetAdminDashboard() throws Exception {
        mockMvc.perform(get("/admins/dashboard-stats")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetAdminDashboardWithToken() throws Exception {
        mockMvc.perform(get("/admins/dashboard-stats")
                        .header("Authorization", "Bearer dummy_token")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }
}