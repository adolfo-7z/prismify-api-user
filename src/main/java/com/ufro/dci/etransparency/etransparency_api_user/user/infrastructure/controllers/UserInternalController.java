package com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.ufro.dci.etransparency.etransparency_api_user.user.application.services.UserService;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.models.User;
import com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.controllers.dto.InternalUserAuthDTO;

@RestController
@RequestMapping("users/internal")
public class UserInternalController {

    private final UserService userService;
    private final String defaultUsername;

    public UserInternalController(UserService userService,
            @Value("${spring.application.default-username}") String defaultUsername) {
        this.userService = userService;
        this.defaultUsername = defaultUsername;
    }

    @GetMapping("/by-username/{username}")
    public ResponseEntity<InternalUserAuthDTO> getUserByUsername(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        InternalUserAuthDTO dto = new InternalUserAuthDTO(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getRole().name(),
                user.isActive());
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/{id}/email")
    public ResponseEntity<String> getUserEmailById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        String email = user.getEmail();
        return new ResponseEntity<>(email, HttpStatus.OK);
    }

    @GetMapping("/admin")
    public ResponseEntity<String> getAdminEmail() {
        User user = userService.getUserByUsername(this.defaultUsername);
        String email = user.getEmail();
        return new ResponseEntity<>(email, HttpStatus.OK);
    }

    @PatchMapping("/{id}/increment-audits")
    public ResponseEntity<Void> incrementAudits(@PathVariable Long id) {
        userService.incrementAuditsPerformed(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
