package com.ufro.dci.etransparency.etransparency_api_user.controllers.administrator;

import lombok.RequiredArgsConstructor;

import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.ufro.dci.etransparency.etransparency_api_user.utils.Constants.*;

import com.ufro.dci.etransparency.etransparency_api_user.handler.ResponseHandler;
import com.ufro.dci.etransparency.etransparency_api_user.services.administrator.AdministratorService;

@RestController
@RequestMapping("admins")
@RequiredArgsConstructor
public class AdministratorController {

    private final AdministratorService administratorService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> getAllAdministrators(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size) {
        return ResponseHandler.generateResponse(OPERATION_SUCCESSFUL, HttpStatus.OK,
                administratorService.getAllAdministrators(page, size));
    }

    @GetMapping("/dashboard-stats")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> getAdminDashboard() {
        return ResponseHandler.generateResponse(OPERATION_SUCCESSFUL, HttpStatus.OK,
                administratorService.getAdminDashboard());
    }

}
