package com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.ufro.dci.etransparency.etransparency_api_user.user.application.services.UserService;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.models.Role;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.models.User;
import com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.controllers.dto.CreateUserRequestDTO;
import com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.controllers.dto.UpdateUserRequestDTO;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    private final ObjectMapper mapper = new ObjectMapper();

    private User sampleUser;

    @BeforeEach
    void setup() {
        mapper.findAndRegisterModules();
        sampleUser = new User();
        sampleUser.setId(1L);
        sampleUser.setUsername("admin");
        sampleUser.setEmail("admin@correo.cl");
        sampleUser.setRole(Role.ADMIN);
        sampleUser.setActive(true);
    }

    @Test
    void createUser_ShouldReturnCreatedUser() throws Exception {
        CreateUserRequestDTO createUserRequest = new CreateUserRequestDTO();
        createUserRequest.setUsername("juancito");
        createUserRequest.setEmail("juan@correo.cl");
        createUserRequest.setPassword("transparencia123");
        createUserRequest.setPhoneNumber("+56999999999");
        when(userService.createUser(any(User.class))).thenReturn(sampleUser);
        mockMvc.perform(post("/users")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(createUserRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(sampleUser.getId()))
                .andExpect(jsonPath("$.username").value("admin"));
        verify(userService).createUser(any(User.class));
    }

    @Test
    void readUser_ShouldReturnUser() throws Exception {
        when(userService.getUserById(1L)).thenReturn(sampleUser);
        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(sampleUser.getId()))
                .andExpect(jsonPath("$.username").value("admin"));

        verify(userService).getUserById(1L);
    }

    @Test
    void getAllUsers_ShouldReturnUserList() throws Exception {
        when(userService.getAllUsers(anyInt(), anyInt(), anyString(), anyString()))
                .thenReturn(List.of(sampleUser));
        mockMvc.perform(get("/users")
                .param("page", "0")
                .param("size", "10")
                .param("date", "desc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("admin"));

        verify(userService).getAllUsers(0, 10, "desc", "desc");
    }

    @Test
    void getUserNotifications_ShouldReturnNotificationList() throws Exception {
        List<String> notifications = List.of(
                "New evaluation available",
                "Evidence rejected");
        when(userService.getUserNotifications(1L)).thenReturn(notifications);
        mockMvc.perform(get("/users/notifications/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("New evaluation available"))
                .andExpect(jsonPath("$[1]").value("Evidence rejected"));
        verify(userService).getUserNotifications(1L);
    }

    @Test
    void updateUser_ShouldReturnUpdatedUser() throws Exception {
        UpdateUserRequestDTO updateRequest = new UpdateUserRequestDTO();
        updateRequest.setEmail("nuevocorreo@correo.cl");
        User updatedUser = new User();
        updatedUser.setId(1L);
        updatedUser.setEmail("nuevocorreo@correo.cl");
        updatedUser.setUsername("auditor");
        when(userService.updateUser(eq(1L), any(User.class))).thenReturn(updatedUser);
        mockMvc.perform(patch("/users/1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("nuevocorreo@correo.cl"));
        verify(userService).updateUser(eq(1L), any(User.class));
    }

    @Test
    void toggleUserStatus_ShouldReturnStatusMessage() throws Exception {
        when(userService.toggleUserStatus(1L)).thenReturn("User activated");

        mockMvc.perform(patch("/users/1/status")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string("User activated"));

        verify(userService).toggleUserStatus(1L);
    }

    @Test
    void deleteUser_ShouldReturnDeletionMessage() throws Exception {
        when(userService.deleteUser(1L)).thenReturn("User deleted");

        mockMvc.perform(delete("/users/1")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string("User deleted"));

        verify(userService).deleteUser(1L);
    }

}
