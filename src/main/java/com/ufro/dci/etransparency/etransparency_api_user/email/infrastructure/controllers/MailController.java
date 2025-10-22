package com.ufro.dci.etransparency.etransparency_api_user.email.infrastructure.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.ufro.dci.etransparency.etransparency_api_user.email.application.services.MailService;
import com.ufro.dci.etransparency.etransparency_api_user.email.infrastructure.controllers.dto.SendMailRequestDTO;

import lombok.extern.slf4j.Slf4j;

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
@RequestMapping("internal/mail/send")
@PreAuthorize("hasRole('SERVICE')")
@Slf4j
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
        log.info("Generic email sent to: " + request.getTo());
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
        log.info("Evaluation rejected email sent to: " + request.getTo());
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
        log.info("Evaluation audit email sent to: " + request.getTo());
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
        log.info("Evaluation finished email sent to: " + request.getTo());
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
    @PostMapping("/evidence/rejected/{evaluationName}")
    public ResponseEntity<Void> sendEvidenceRejectedEmail(@RequestBody SendMailRequestDTO request, @PathVariable String evaluationName) {
        mailService.sendEvidenceRejectedEmail(request.toDomain(), evaluationName);
        log.info("Evidence rejected email sent to: " + request.getTo());
        return ResponseEntity.ok().build();
    }

    /**
     * Envía un correo electrónico relacionado con la apelación de una evidencia.
     *
     * @param request DTO con los datos necesarios para el envío del correo.
     * @return ResponseEntity con estado HTTP 200 OK si el envío fue exitoso.
     */
    @PostMapping("/evidence/appeal/{evaluationName}")
    public ResponseEntity<Void> sendAppealEvidenceEmail(@RequestBody SendMailRequestDTO request, @PathVariable String evaluationName) {
        mailService.sendAppealEvidenceEmail(request.toDomain(), evaluationName);
        log.info("Evidence appealed email sent to: " + request.getTo());
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
        log.info("New institution request email sent to: " + request.getTo());
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
        log.info("Password recovery code email sent to: " + request.getTo());
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
        log.info("New password alert email sent to: " + request.getTo());
        return ResponseEntity.ok().build();
    }

    /**
     * Envía un correo notificando al auditor de que se le ha asignado una nueva
     * evaluación a auditar
     *
     * @param request DTO con los datos necesarios para el envío del correo.
     * @return ResponseEntity con estado HTTP 200 OK si el envío fue exitoso.
     */
    @PostMapping("/auditor/assignment/{newEvaluation}")
    public ResponseEntity<Void> sendAuditorAssigmentEmail(@RequestBody SendMailRequestDTO request,
            @PathVariable String newEvaluation) {
        mailService.sendAuditorAssigmentEmail(request.toDomain(), newEvaluation);
        log.info("New auditor assignment email sent to: " + request.getTo());
        return ResponseEntity.ok().build();
    }

}
