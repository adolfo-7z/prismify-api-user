package com.ufro.dci.etransparency.etransparency_api_user.email.infrastructure.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ufro.dci.etransparency.etransparency_api_user.email.application.services.MailService;
import com.ufro.dci.etransparency.etransparency_api_user.email.infrastructure.controllers.dto.SendMailRequestDTO;

/**
 * Controlador REST para el envío de correos electrónicos internos.
 * <p>
 * Esta clase expone endpoints que permiten enviar distintos tipos de correos
 * electrónicos
 * relacionados con evaluaciones, evidencias e instituciones.
 * 
 * @author Adolfo Plaza
 */
@RestController
@RequestMapping("/mail/internal/send")
public class MailController {

    private final MailService mailService;

    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    /**
     * Envía un correo electrónico genérico.
     *
     * @param request DTO que contiene la información del correo a enviar.
     * @return ResponseEntity con estado HTTP 200 OK si el envío fue exitoso.
     */
    @PostMapping("")
    public ResponseEntity<Void> sendMail(@RequestBody SendMailRequestDTO request) {
        mailService.sendGenericMail(request.toDomain());
        return ResponseEntity.ok().build();
    }

    /**
     * Envía un correo electrónico notificando que una evaluación fue rechazada.
     *
     * @param request DTO con los datos necesarios para el envío del correo.
     * @return ResponseEntity con estado HTTP 200 OK si el envío fue exitoso.
     */
    @PostMapping("/evaluation/rejected")
    public ResponseEntity<Void> sendEvaluationRejectedEmail(@RequestBody SendMailRequestDTO request) {
        mailService.sendEvaluationRejectedEmail(request.toDomain());
        return ResponseEntity.ok().build();
    }

    /**
     * Envía un correo electrónico de que autoría fue iniciada en una evaluación.
     *
     * @param request DTO con los datos necesarios para el envío del correo.
     * @return ResponseEntity con estado HTTP 200 OK si el envío fue exitoso.
     */
    @PostMapping("/evaluation/audit")
    public ResponseEntity<Void> sendAuditEvaluationEmail(@RequestBody SendMailRequestDTO request) {
        mailService.sendAuditEvaluationEmail(request.toDomain());
        return ResponseEntity.ok().build();
    }

    /**
     * Envía un correo electrónico notificando la finalización de una evaluación.
     *
     * @param request DTO con los datos necesarios para el envío del correo.
     * @return ResponseEntity con estado HTTP 200 OK si el envío fue exitoso.
     */
    @PostMapping("/evaluation/finish")
    public ResponseEntity<Void> sendFinishEvaluationEmail(@RequestBody SendMailRequestDTO request) {
        mailService.sendFinishEvaluationEmail(request.toDomain());
        return ResponseEntity.ok().build();
    }

    /**
     * Envía un correo electrónico solicitando una nueva evaluación.
     *
     * @param request DTO con los datos necesarios para el envío del correo.
     * @return ResponseEntity con estado HTTP 200 OK si el envío fue exitoso.
     */
    @PostMapping("/evaluation")
    public ResponseEntity<Void> sendNewEvaluationRequestEmail(@RequestBody SendMailRequestDTO request) {
        mailService.sendNewEvaluationRequestEmail(request.toDomain());
        return ResponseEntity.ok().build();
    }

    /**
     * Envía un correo electrónico notificando que una evidencia fue rechazada.
     *
     * @param request DTO con los datos necesarios para el envío del correo.
     * @return ResponseEntity con estado HTTP 200 OK si el envío fue exitoso.
     */
    @PostMapping("/evidence/rejected")
    public ResponseEntity<Void> sendEvidenceRejectedEmail(@RequestBody SendMailRequestDTO request) {
        mailService.sendEvidenceRejectedEmail(request.toDomain());
        return ResponseEntity.ok().build();
    }

    /**
     * Envía un correo electrónico relacionado con la apelación de una evidencia.
     *
     * @param request DTO con los datos necesarios para el envío del correo.
     * @return ResponseEntity con estado HTTP 200 OK si el envío fue exitoso.
     */
    @PostMapping("/evidence/appeal")
    public ResponseEntity<Void> sendAppealEvidenceEmail(@RequestBody SendMailRequestDTO request) {
        mailService.sendAppealEvidenceEmail(request.toDomain());
        return ResponseEntity.ok().build();
    }

    /**
     * Envía un correo electrónico solicitando la creación de una nueva institución.
     *
     * @param request DTO con los datos necesarios para el envío del correo.
     * @return ResponseEntity con estado HTTP 200 OK si el envío fue exitoso.
     */
    @PostMapping("/institution")
    public ResponseEntity<Void> sendNewInstitutionRequestEmail(@RequestBody SendMailRequestDTO request) {
        mailService.sendNewInstitutionRequestEmail(request.toDomain());
        return ResponseEntity.ok().build();
    }

    /**
     * Envía un correo con código de recuperación.
     *
     * @param request DTO con los datos necesarios para el envío del correo.
     * @return ResponseEntity con estado HTTP 200 OK si el envío fue exitoso.
     */
    @PostMapping("/recovery/code/{code}")
    public ResponseEntity<Void> sendRecoveryCodeEmail(@PathVariable String code,
            @RequestBody SendMailRequestDTO request) {
        mailService.sendRecoveryCodeEmail(request.toDomain(), code);
        return ResponseEntity.ok().build();
    }

    /**
     * Envía un correo notificando del cambio de contraseña del usuario.
     *
     * @param request DTO con los datos necesarios para el envío del correo.
     * @return ResponseEntity con estado HTTP 200 OK si el envío fue exitoso.
     */
    @PostMapping("/recovery/new")
    public ResponseEntity<Void> sendNewPasswordAlert(@RequestBody SendMailRequestDTO request) {
        mailService.sendNewPasswordAlert(request.toDomain());
        return ResponseEntity.ok().build();
    }

}
