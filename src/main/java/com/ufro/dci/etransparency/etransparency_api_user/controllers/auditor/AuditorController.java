package com.ufro.dci.etransparency.etransparency_api_user.controllers.auditor;

import lombok.RequiredArgsConstructor;

import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.ufro.dci.etransparency.etransparency_api_user.utils.Constants.*;

import com.ufro.dci.etransparency.etransparency_api_user.handler.ResponseHandler;
import com.ufro.dci.etransparency.etransparency_api_user.services.auditor.AuditorService;

@RestController
@RequestMapping("auditors")
@RequiredArgsConstructor
public class AuditorController {

    private final AuditorService auditorService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> getAllAuditors(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(name = "date", defaultValue = "desc") String sortDirection,
            @RequestParam(defaultValue = "") String name) {
        return ResponseHandler.generateResponse(OPERATION_SUCCESSFUL, HttpStatus.OK,
                auditorService.getAllAuditors(page, size, sortDirection, name));
    }

}
