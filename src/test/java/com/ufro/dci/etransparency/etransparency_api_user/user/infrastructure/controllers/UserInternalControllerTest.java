package com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.controllers;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ufro.dci.etransparency.etransparency_api_user.user.application.services.UserService;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.models.Role;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.models.User;

@WebMvcTest(UserInternalController.class)
class UserInternalControllerTest {

    @MockitoBean
    private UserService userService;

    private MockMvc mockMvc;

    private final String defaultUsername = "adminTechPriest";

    @BeforeEach
    void setUp() {
        UserInternalController controller = new UserInternalController(userService, defaultUsername);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void shouldReturnUserByUsername() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setUsername("admin");
        user.setEmail("admin@correo.cl");
        user.setPassword("password123");
        user.setRole(Role.ADMIN);
        user.setActive(true);
        when(userService.getUserByUsername("techpriest")).thenReturn(user);
        mockMvc.perform(get("/internal/users/by-username/techpriest"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value("admin"))
                .andExpect(jsonPath("$.password").value("password123"))
                .andExpect(jsonPath("$.role").value("ADMIN"))
                .andExpect(jsonPath("$.active").value(true));
    }

    @Test
    void shouldReturnUserEmailById() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setUsername("admin");
        user.setEmail("admin@correo.cl");
        user.setRole(Role.ADMIN);
        user.setActive(true);
        when(userService.getUserById(42L)).thenReturn(user);
        mockMvc.perform(get("/internal/users/42/email"))
                .andExpect(status().isOk())
                .andExpect(content().string("admin@correo.cl"));
    }

    @Test
    void shouldReturnAdminEmail() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setUsername("admin");
        user.setEmail("admin@correo.cl");
        user.setRole(Role.ADMIN);
        user.setActive(true);
        when(userService.getUserByUsername(defaultUsername)).thenReturn(user);
        mockMvc.perform(get("/internal/users/admin"))
                .andExpect(status().isOk())
                .andExpect(content().string("admin@correo.cl"));
    }

    @Test
    void shouldIncrementAudits() throws Exception {
        doNothing().when(userService).incrementAuditsPerformed(99L);
        mockMvc.perform(patch("/internal/users/99/increment-audits"))
                .andExpect(status().isOk());
    }

}
