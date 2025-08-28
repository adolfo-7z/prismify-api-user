package com.ufro.dci.etransparency.etransparency_api_user.auth.infrastructure.controllers;

import org.apache.hc.client5.http.auth.InvalidCredentialsException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.ufro.dci.etransparency.etransparency_api_user.auth.application.services.AuthService;
import com.ufro.dci.etransparency.etransparency_api_user.auth.infrastructure.controllers.dto.*;

/**
 * Controlador REST encargado de manejar las operaciones de autenticación.
 * Proporciona endpoints para iniciar sesión de los usuarios.
 * 
 * <p>
 * Este controlador expone un endpoint para login que devuelve un token de
 * autenticación
 * en caso de credenciales válidas.
 * 
 * @author Adolfo Plaza
 */
@RestController
@RequestMapping("auth")
public class AuthController {

    private final AuthService authService;

    /**
     * Constructor del controlador de autenticación.
     * 
     * @param authService servicio encargado de la lógica de autenticación
     */
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Endpoint para iniciar sesión.
     * Recibe las credenciales del usuario, valida la información y retorna un token
     * de autenticación.
     * 
     * @param request objeto que contiene el nombre de usuario y la contraseña
     * @return ResponseEntity que contiene el token de autenticación en un
     *         {@link AuthResponseDTO}
     * @throws InvalidCredentialsException si las credenciales proporcionadas son
     *                                     incorrectas
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO request)
            throws InvalidCredentialsException {
        AuthResponseDTO response = new AuthResponseDTO();
        response.setToken(authService.login(request.getUsername(), request.getPassword()));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
