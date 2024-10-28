package com.ufro.dci.etransparency.etransparency_api_user.controllers.administrator;

import lombok.*;

import jakarta.validation.Valid;

import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.ufro.dci.etransparency.etransparency_api_user.utils.Constants.*;

import com.ufro.dci.etransparency.etransparency_api_user.dtos.administrator.AdministratorDTO;
import com.ufro.dci.etransparency.etransparency_api_user.dtos.administrator.AdministratorUpdateDTO;
import com.ufro.dci.etransparency.etransparency_api_user.handler.ResponseHandler;
import com.ufro.dci.etransparency.etransparency_api_user.services.administrator.AdministratorCrudService;

@RestController
@RequestMapping("admins")
@RequiredArgsConstructor
public class AdministratorCrudController {

    private final AdministratorCrudService administratorCrudService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> getAdministrator(@PathVariable Long id) {
        return ResponseHandler.generateResponse(OPERATION_SUCCESSFUL, HttpStatus.OK,
                administratorCrudService.getAdministrator(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> createAdministrator(@Valid @RequestBody AdministratorDTO administratorDTO) {
        return ResponseHandler.generateResponse(OPERATION_SUCCESSFUL, HttpStatus.CREATED,
                administratorCrudService.createAdministrator(administratorDTO));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> updateAdministrator(@PathVariable Long id,
            @Valid @RequestBody AdministratorUpdateDTO administratorUpdateDTO) {
        return ResponseHandler.generateResponse(OPERATION_SUCCESSFUL, HttpStatus.OK,
                administratorCrudService.updateAdministrator(id, administratorUpdateDTO));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> toggleAdministratorStatus(@PathVariable Long id) {
        return ResponseHandler.generateResponse(OPERATION_SUCCESSFUL, HttpStatus.OK,
                administratorCrudService.toggleAdministratorStatus(id));
    }

}
