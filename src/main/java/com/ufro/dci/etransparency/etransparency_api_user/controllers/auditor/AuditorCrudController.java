package com.ufro.dci.etransparency.etransparency_api_user.controllers.auditor;

import lombok.RequiredArgsConstructor;

import jakarta.validation.Valid;

import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.ufro.dci.etransparency.etransparency_api_user.utils.Constants.*;

import com.ufro.dci.etransparency.etransparency_api_user.dtos.auditor.AuditorDTO;
import com.ufro.dci.etransparency.etransparency_api_user.dtos.auditor.AuditorUpdateDTO;
import com.ufro.dci.etransparency.etransparency_api_user.handler.ResponseHandler;
import com.ufro.dci.etransparency.etransparency_api_user.services.auditor.AuditorCrudService;

@RestController
@RequestMapping("auditors")
@RequiredArgsConstructor
public class AuditorCrudController {

    private final AuditorCrudService auditorCrudService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('AUDITOR')")
    public ResponseEntity<Object> getAuditor(@PathVariable Long id) {
        return ResponseHandler.generateResponse(OPERATION_SUCCESSFUL, HttpStatus.OK,
                auditorCrudService.getAuditor(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> createAuditor(@Valid @RequestBody AuditorDTO auditorDTO) {
        return ResponseHandler.generateResponse(OPERATION_SUCCESSFUL, HttpStatus.CREATED,
                auditorCrudService.createAuditor(auditorDTO));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('AUDITOR')")
    public ResponseEntity<Object> updateAuditor(@Valid @PathVariable Long id,
            @Valid @RequestBody AuditorUpdateDTO auditorUpdateDTO) {
        return ResponseHandler.generateResponse(OPERATION_SUCCESSFUL, HttpStatus.OK,
                auditorCrudService.updateAuditor(id, auditorUpdateDTO));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> toggleAuditorStatus(@PathVariable Long id) {
        return ResponseHandler.generateResponse(OPERATION_SUCCESSFUL, HttpStatus.OK,
                auditorCrudService.toggleAuditorStatus(id));
    }
}
