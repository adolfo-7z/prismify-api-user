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

    private final String defaultUsername = "admin";

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
        when(userService.getUserByUsername("admin")).thenReturn(user);
        mockMvc.perform(get("/internal/users/by-username/admin"))
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
        user.setId(42L);
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

    @Test
    void shouldReturnUserByEmail() throws Exception {
        User user = new User();
        user.setId(10L);
        user.setUsername("juan_perez");
        user.setEmail("correo@empresa.cl");
        user.setPassword("password");
        user.setRole(Role.ADMIN);
        user.setActive(true);
        when(userService.getUserByEmail("correo@empresa.cl")).thenReturn(user);
        mockMvc.perform(get("/internal/users/by-email/correo@empresa.cl"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10L))
                .andExpect(jsonPath("$.username").value("juan_perez"))
                .andExpect(jsonPath("$.password").value("password"))
                .andExpect(jsonPath("$.role").value("ADMIN"))
                .andExpect(jsonPath("$.active").value(true));
    }

    @Test
    void shouldAddNotification() throws Exception {
        doNothing().when(userService).createNotification(55L, "hello world");
        mockMvc.perform(post("/internal/users/notifications/55")
                .param("message", "hello world"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldAddAdminNotification() throws Exception {
        doNothing().when(userService).createAdminNotification("system alert");
        mockMvc.perform(post("/internal/users/notifications/admin")
                .param("message", "system alert"))
                .andExpect(status().isOk());
    }

}
