package com.ufro.dci.etransparency.etransparency_api_user.auth.infrastructure.controllers;

import org.apache.hc.client5.http.auth.InvalidCredentialsException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.ufro.dci.etransparency.etransparency_api_user.auth.application.services.AuthService;
import com.ufro.dci.etransparency.etransparency_api_user.auth.infrastructure.controllers.dto.*;

@RestController
@RequestMapping("auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO request)
            throws InvalidCredentialsException {
        AuthResponseDTO response = new AuthResponseDTO();
        response.setToken(authService.login(request.getUsername(), request.getPassword()));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
