package com.ufro.dci.etransparency.etransparency_api_user.controllers.auth;

import static com.ufro.dci.etransparency.etransparency_api_user.utils.Constants.*;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ufro.dci.etransparency.etransparency_api_user.handler.ResponseHandler;
import com.ufro.dci.etransparency.etransparency_api_user.services.auth.RecoveryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("recovery")
@RequiredArgsConstructor
public class RecoveryController {

    private final RecoveryService recoveryService;

    @PostMapping("/code")
    public ResponseEntity<Object> requestRecoveryCode(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        return ResponseHandler.generateResponse(OPERATION_SUCCESSFUL, HttpStatus.OK,
                recoveryService.sendRecoveryCode(email));
    }

    @PostMapping("/validate")
    public ResponseEntity<Object> validateRecoveryCode(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String recoveryCode = body.get("recoveryCode");
        return ResponseHandler.generateResponse(OPERATION_SUCCESSFUL, HttpStatus.OK,
                recoveryService.validateRecoveryCode(email, recoveryCode));
    }

    @PostMapping("/password")
    public ResponseEntity<Object> validateNewPassword(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");
        String validationPassword = body.get("validationPassword");
        return ResponseHandler.generateResponse(OPERATION_SUCCESSFUL, HttpStatus.OK,
                recoveryService.validateNewPassword(email, password, validationPassword));
    }

}
