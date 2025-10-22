package com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.ufro.dci.etransparency.etransparency_api_user.user.application.services.UserService;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.models.User;
import com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.controllers.dto.InternalUserAuthDTO;

/**
 * Controlador REST interno para la gestión de usuarios dentro del sistema.
 * <p>
 * Proporciona endpoints para obtener información de usuarios por username o ID,
 * obtener el email del usuario administrador por defecto y para incrementar
 * el contador de auditorías realizadas por un usuario.
 * 
 * @author Adolfo Plaza
 */
@RestController
@RequestMapping("internal/users")
@PreAuthorize("hasRole('SERVICE')")
public class UserInternalController {

    private final UserService userService;
    private final String defaultUsername;

    public UserInternalController(UserService userService,
            @Value("${spring.application.default-username}") String defaultUsername) {
        this.userService = userService;
        this.defaultUsername = defaultUsername;
    }

    /**
     * Obtiene la información de un usuario por su username.
     * 
     * @param username Nombre de usuario a buscar.
     * @return ResponseEntity que contiene un DTO con la información del usuario.
     */
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

    /**
     * Obtiene el email de un usuario por su ID.
     * 
     * @param id ID del usuario.
     * @return ResponseEntity que contiene el email del usuario.
     */
    @GetMapping("/{id}/email")
    public ResponseEntity<String> getUserEmailById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        String email = user.getEmail();
        return new ResponseEntity<>(email, HttpStatus.OK);
    }

    /**
     * Obtiene el email del usuario administrador por defecto configurado en la
     * aplicación.
     * 
     * @return ResponseEntity que contiene el email del administrador.
     */
    @GetMapping("/admin")
    public ResponseEntity<String> getAdminEmail() {
        User user = userService.getUserByUsername(this.defaultUsername);
        String email = user.getEmail();
        return new ResponseEntity<>(email, HttpStatus.OK);
    }

    /**
     * Incrementa el contador de auditorías realizadas por un usuario.
     * 
     * @param id ID del usuario cuya cantidad de auditorías será incrementada.
     * @return ResponseEntity vacío con estado HTTP 200 OK.
     */
    @PatchMapping("/{id}/increment-audits")
    public ResponseEntity<Void> incrementAudits(@PathVariable Long id) {
        userService.incrementAuditsPerformed(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/notifications/{id}")
    public ResponseEntity<Void> addNotification(@PathVariable Long id, @RequestParam String message) {
        userService.createNotification(id, message);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/notifications/admin")
    public ResponseEntity<Void> addAdminNotification(@RequestParam String message) {
        userService.createAdminNotification(message);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
