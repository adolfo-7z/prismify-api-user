package com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.ufro.dci.etransparency.etransparency_api_user.user.application.services.UserService;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.models.User;
import com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.controllers.dto.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid CreateUserRequestDTO user) {
        User createdUser = userService.createUser(user.toDomain());
        return new ResponseEntity<>(UserDTOMapper.toDto(createdUser), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('AUDITOR')")
    public ResponseEntity<UserDTO> readUser(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return new ResponseEntity<>(UserDTOMapper.toDto(user), HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDTO>> getAllUsers(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "desc") String date) {
        return new ResponseEntity<>(userService.getAllUsers(page, size, date, date).stream().map(UserDTOMapper::toDto)
                .toList(), HttpStatus.OK);
    }

    @GetMapping("/notifications/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('AUDITOR') or hasRole('MANAGER')")
    public ResponseEntity<List<String>> getUserNotifications(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getUserNotifications(id), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('AUDITOR')")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UpdateUserRequestDTO updatedUser) {
        User partialUser = UserDTOMapper.fromDto(updatedUser);
        User user = userService.updateUser(id, partialUser);
        return new ResponseEntity<>(UserDTOMapper.toDto(user), HttpStatus.OK);
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> toggleUserStatus(@PathVariable Long id) {
        return new ResponseEntity<>(userService.toggleUserStatus(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        return new ResponseEntity<>(userService.deleteUser(id), HttpStatus.OK);
    }

}
