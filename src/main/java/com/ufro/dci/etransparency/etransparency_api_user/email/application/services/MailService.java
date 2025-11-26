package com.ufro.dci.etransparency.etransparency_api_user.email.application.services;

import com.ufro.dci.etransparency.etransparency_api_user.email.domain.models.MailMessage;
import com.ufro.dci.etransparency.etransparency_api_user.email.domain.ports.in.ComposeMailUseCase;

import lombok.RequiredArgsConstructor;

/**
 * Servicio de correo encargado de delegar el envío de distintos tipos de
 * mensajes de correo
 * a una implementación concreta de {@link ComposeMailUseCase}.
 * 
 * @author Adolfo Plaza
 */
@RequiredArgsConstructor
public class MailService implements ComposeMailUseCase {

    private final ComposeMailUseCase composeMailUseCase;

    /**
     * Envía un correo genérico.
     * 
     * @param message el mensaje de correo a enviar
     */
    @Override
    public void sendGenericMail(MailMessage message) {
        composeMailUseCase.sendGenericMail(message);
    }

    /**
     * Envía un correo indicando que una evaluación ha sido rechazada.
     * 
     * @param message el mensaje de correo a enviar
     */
    @Override
    public void sendEvaluationRejectedEmail(MailMessage message) {
        composeMailUseCase.sendEvaluationRejectedEmail(message);
    }

    /**
     * Envía un correo relacionado con la auditoría de una evaluación.
     * 
     * @param message el mensaje de correo a enviar
     */
    @Override
    public void sendAuditEvaluationEmail(MailMessage message) {
        composeMailUseCase.sendAuditEvaluationEmail(message);
    }

    /**
     * Envía un correo indicando que una evaluación ha finalizado.
     * 
     * @param message el mensaje de correo a enviar
     */
    @Override
    public void sendFinishEvaluationEmail(MailMessage message) {
        composeMailUseCase.sendFinishEvaluationEmail(message);
    }

    /**
     * Envía un correo notificando una nueva solicitud de evaluación.
     * 
     * @param message el mensaje de correo a enviar
     */
    @Override
    public void sendNewEvaluationRequestEmail(MailMessage message) {
        composeMailUseCase.sendNewEvaluationRequestEmail(message);
    }

    /**
     * Envía un correo indicando que una evidencia ha sido rechazada.
     * 
     * @param message el mensaje de correo a enviar
     */
    @Override
    public void sendEvidenceRejectedEmail(MailMessage message, String evaluationName) {
        composeMailUseCase.sendEvidenceRejectedEmail(message, evaluationName);
    }

    /**
     * Envía un correo relacionado con una apelación de evidencia.
     * 
     * @param message el mensaje de correo a enviar
     */
    @Override
    public void sendAppealEvidenceEmail(MailMessage message, String evaluationName) {
        composeMailUseCase.sendAppealEvidenceEmail(message, evaluationName);
    }

    /**
     * Envía un correo notificando una nueva solicitud de institución.
     * 
     * @param message el mensaje de correo a enviar
     */
    @Override
    public void sendNewInstitutionRequestEmail(MailMessage message) {
        composeMailUseCase.sendNewInstitutionRequestEmail(message);
    }

    /**
     * Envía un correo con código de recuperación.
     * 
     * @param message el mensaje de correo a enviar
     */
    @Override
    public void sendRecoveryCodeEmail(MailMessage message, String code) {
        composeMailUseCase.sendRecoveryCodeEmail(message, code);
    }

    /**
     * Envía un correo notificando del cambio de contraseña del usuario.
     * 
     * @param message el mensaje de correo a enviar
     */
    @Override
    public void sendNewPasswordAlert(MailMessage message) {
        composeMailUseCase.sendNewPasswordAlert(message);
    }

    /**
     * Envía un correo notificando al auditor sobre nueva asignación.
     * 
     * @param message el mensaje de correo a enviar
     */
    @Override
    public void sendAuditorAssigmentEmail(MailMessage message, String newEvaluation) {
        composeMailUseCase.sendAuditorAssigmentEmail(message, newEvaluation);
    }

}
