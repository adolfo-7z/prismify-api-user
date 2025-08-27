package com.ufro.dci.etransparency.etransparency_api_user.email.infrastructure.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ufro.dci.etransparency.etransparency_api_user.email.application.services.MailService;
import com.ufro.dci.etransparency.etransparency_api_user.email.infrastructure.controllers.dto.SendMailRequestDTO;

@RestController
@RequestMapping("/mail/internal")
public class MailController {

    private final MailService mailService;

    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/send")
    public ResponseEntity<Void> sendMail(@RequestBody SendMailRequestDTO request) {
        mailService.sendGenericMail(request.toDomain());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/send/evaluation/rejected")
    public ResponseEntity<Void> sendEvaluationRejectedEmail(@RequestBody SendMailRequestDTO request) {
        mailService.sendEvaluationRejectedEmail(request.toDomain());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/send/evaluation/audit")
    public ResponseEntity<Void> sendAuditEvaluationEmail(@RequestBody SendMailRequestDTO request) {
        mailService.sendAuditEvaluationEmail(request.toDomain());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/send/evaluation/finish")
    public ResponseEntity<Void> sendFinishEvaluationEmail(@RequestBody SendMailRequestDTO request) {
        mailService.sendFinishEvaluationEmail(request.toDomain());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/send/evaluation")
    public ResponseEntity<Void> sendNewEvaluationRequestEmail(@RequestBody SendMailRequestDTO request) {
        mailService.sendNewEvaluationRequestEmail(request.toDomain());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/send/evidence/rejected")
    public ResponseEntity<Void> sendEvidenceRejectedEmail(@RequestBody SendMailRequestDTO request) {
        mailService.sendEvidenceRejectedEmail(request.toDomain());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/send/evidence/appeal")
    public ResponseEntity<Void> sendAppealEvidenceEmail(@RequestBody SendMailRequestDTO request) {
        mailService.sendAppealEvidenceEmail(request.toDomain());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/send/institution")
    public ResponseEntity<Void> sendNewInstitutionRequestEmail(@RequestBody SendMailRequestDTO request) {
        mailService.sendNewInstitutionRequestEmail(request.toDomain());
        return ResponseEntity.ok().build();
    }

}
