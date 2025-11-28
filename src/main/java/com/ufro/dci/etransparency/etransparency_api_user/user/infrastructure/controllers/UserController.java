package com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.ufro.dci.etransparency.etransparency_api_user.user.application.services.UserService;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.models.Role;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.models.User;
import com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.controllers.dto.responses.ApiResponse;
import com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.controllers.dto.responses.PageMeta;
import com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.controllers.dto.user.*;

import jakarta.validation.Valid;

/**
 * Controlador REST para la gestión de usuarios en la aplicación.
 * Proporciona endpoints para crear, leer, actualizar, desactivar y eliminar
 * usuarios,
 * así como para obtener notificaciones de un usuario específico.
 * 
 * @author Adolfo Plaza
 */
@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Crea un nuevo usuario en el sistema.
     * Solo accesible para usuarios con rol ADMIN.
     * 
     * @param user DTO que contiene los datos del usuario a crear.
     * @return ResponseEntity con el usuario creado y estado HTTP 201 (CREATED).
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UserDTO>> createUser(@RequestBody @Valid CreateUserRequestDTO user) {
        User createdUser = userService.createUser(user.toDomain());
        return ApiResponse.created(UserDTOMapper.toDto(createdUser));
    }

    /**
     * Obtiene los datos de un usuario por su ID.
     * Accesible para roles ADMIN, MANAGER y AUDITOR.
     * 
     * @param id ID del usuario a consultar.
     * @return ResponseEntity con el DTO del usuario y estado HTTP 200 (OK).
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('AUDITOR') or hasRole('SERVICE')")
    public ApiResponse<UserDTO> readUser(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ApiResponse.ok(UserDTOMapper.toDto(user));
    }

    /**
     * Obtiene la lista de todos los usuarios paginada.
     * Solo accesible para usuarios con rol ADMIN.
     * 
     * @param page Número de página (por defecto 0).
     * @param size Tamaño de la página (por defecto 10).
     * @param date Orden de la fecha (asc o desc, por defecto "desc").
     * @return ResponseEntity con la lista de usuarios DTO y estado HTTP 200 (OK).
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<List<UserDTO>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email,
            @RequestParam(required = false, defaultValue = "desc") String date,
            @RequestParam(required = false) Boolean active,
            @RequestParam(required = false) Role role) {
        Page<UserDTO> userDtos = userService.getAllUsers(page, size, username, email, date, active, role)
                .map(UserDTOMapper::toDto);
        return ApiResponse.paged(userDtos.getContent(), PageMeta.of(userDtos.getNumber(), userDtos.getSize(),
                userDtos.getTotalElements(), userDtos.getTotalPages()));
    }

    /**
     * Actualiza parcialmente los datos de un usuario existente.
     * Accesible para roles ADMIN, MANAGER y AUDITOR.
     * 
     * @param id          ID del usuario a actualizar.
     * @param updatedUser DTO con los datos actualizados del usuario.
     * @return ResponseEntity con el usuario actualizado y estado HTTP 200 (OK).
     */
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('AUDITOR')")
    public ApiResponse<UserDTO> updateUser(@PathVariable Long id, @RequestBody UpdateUserRequestDTO updatedUser) {
        User partialUser = UserDTOMapper.fromDto(updatedUser);
        User user = userService.updateUser(id, partialUser);
        return ApiResponse.ok(UserDTOMapper.toDto(user));
    }

    /**
     * Activa o desactiva el estado de un usuario.
     * Solo accesible para usuarios con rol ADMIN.
     * 
     * @param id ID del usuario cuyo estado se desea cambiar.
     * @return ResponseEntity con un mensaje indicando el resultado y estado HTTP
     *         200 (OK).
     */
    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<String> toggleUserStatus(@PathVariable Long id) {
        return ApiResponse.ok(userService.toggleUserStatus(id));
    }

    /**
     * Elimina un usuario por su ID.
     * Solo accesible para usuarios con rol ADMIN.
     * 
     * @param id ID del usuario a eliminar.
     * @return ResponseEntity con un mensaje de confirmación y estado HTTP 200 (OK).
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<String> deleteUser(@PathVariable Long id) {
        return ApiResponse.ok(userService.deleteUser(id));
    }

    /**
     * Solicita un código de recuperación de contraseña.
     * La ruta no requiere autorización
     *
     * @param body mapa con la clave {@code email}, que corresponde al correo del
     *             usuario
     * @return {@link ResponseEntity} con estado {@code 200 OK} si el código fue
     *         enviado
     */
    @PostMapping("/recovery/code")
    public ApiResponse<Void> requestRecoveryCode(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        userService.sendRecoveryCode(email);
        return ApiResponse.ok(null);
    }

    /**
     * Endpoint para validar un código de recuperación de contraseña.
     * La ruta no requiere autorización
     *
     * @param body mapa con las claves {@code email} y {@code recoveryCode}
     * @return {@link ResponseEntity} con estado {@code 200 OK} si el código es
     *         válido
     */
    @PostMapping("/recovery/validate")
    public ApiResponse<Void> validateRecoveryCode(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String recoveryCode = body.get("recoveryCode");
        userService.validateRecoveryCode(email, recoveryCode);
        return ApiResponse.ok(null);
    }

    /**
     * Endpoint para establecer una nueva contraseña tras la validación.
     * La ruta no requiere autorización
     *
     * @param request objeto con los datos necesarios para el restablecimiento de
     *                contraseña:
     *                {@code email}, {@code password} y {@code validationPassword}
     * @return {@link ResponseEntity} con estado {@code 200 OK} si la contraseña se
     *         actualizó correctamente
     */
    @PostMapping("/recovery/password")
    public ApiResponse<Void> validateNewPassword(@Valid @RequestBody PasswordResetRequestDTO request) {
        userService.validateNewPassword(request.getEmail(), request.getPassword(), request.getValidationPassword());
        return ApiResponse.ok(null);
    }

    /**
     * Obtiene las notificaciones de un usuario por su ID.
     * Accesible para roles ADMIN, AUDITOR y MANAGER.
     * 
     * @param id ID del usuario cuyas notificaciones se desean consultar.
     * @return ResponseEntity con la lista de notificaciones y estado HTTP 200 (OK).
     */
    @GetMapping("/{id}/notifications")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('AUDITOR')")
    public ApiResponse<List<NotificationDTO>> getUserNotifications(@PathVariable Long id) {
        List<NotificationDTO> dtos = userService.getNotifications(id).stream().map(UserDTOMapper::toDto).toList();
        return ApiResponse.ok(dtos);
    }

    /**
     * Elimina una notificación específica de un usuario.
     * Accesible para roles ADMIN, AUDITOR y MANAGER.
     *
     * @param userId ID del usuario al que pertenece la notificación.
     * @param id     ID de la notificación a eliminar.
     * @return ResponseEntity con estado HTTP 200 (OK) si la eliminación fue
     *         exitosa.
     */
    @DeleteMapping("/{userId}/notifications/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('AUDITOR')")
    public ApiResponse<Void> removeNotification(@PathVariable Long userId, @PathVariable Long id) {
        userService.removeNotification(userId, id);
        return ApiResponse.ok(null);
    }

    /**
     * Elimina todas las notificaciones de un usuario.
     * Accesible para roles ADMIN, AUDITOR y MANAGER.
     *
     * @param userId ID del usuario cuyas notificaciones se desean eliminar.
     * @return ResponseEntity con estado HTTP 200 (OK) si la limpieza fue exitosa.
     */
    @DeleteMapping("/{userId}/notifications")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('AUDITOR')")
    public ApiResponse<Void> removeAllNotifications(@PathVariable Long userId) {
        userService.clearNotifications(userId);
        return ApiResponse.ok(null);
    }
}
