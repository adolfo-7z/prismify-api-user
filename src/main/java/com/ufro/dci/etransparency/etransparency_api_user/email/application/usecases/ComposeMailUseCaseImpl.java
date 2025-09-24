package com.ufro.dci.etransparency.etransparency_api_user.email.application.usecases;

import java.util.Map;

import com.ufro.dci.etransparency.etransparency_api_user.email.application.support.EmailTemplateLoader;
import com.ufro.dci.etransparency.etransparency_api_user.email.domain.models.MailMessage;
import com.ufro.dci.etransparency.etransparency_api_user.email.domain.ports.in.ComposeMailUseCase;
import com.ufro.dci.etransparency.etransparency_api_user.email.domain.ports.out.SendMailPort;

/**
 * Implementación del caso de uso para la composición y envío de correos
 * electrónicos.
 * <p>
 * Esta clase utiliza un {@link SendMailPort} para enviar los correos y
 * {@link EmailTemplateLoader} para obtener los asuntos y cuerpos de los
 * mensajes
 * según plantillas predefinidas.
 * 
 * @author Adolfo Plaza
 */
public class ComposeMailUseCaseImpl implements ComposeMailUseCase {

    private final String adminMail;
    private final SendMailPort sendMailPort;

    public ComposeMailUseCaseImpl(String adminMail,
            SendMailPort sendMailPort) {
        this.adminMail = adminMail;
        this.sendMailPort = sendMailPort;
    }

    /**
     * Envía un correo genérico utilizando la información del mensaje proporcionado.
     * 
     * @param message Mensaje de correo a enviar
     */
    @Override
    public void sendGenericMail(MailMessage message) {
        sendMailPort.send(message);
    }

    /**
     * Envía un correo indicando que la evaluación ha sido rechazada.
     * 
     * @param message Mensaje de correo a enviar
     */
    @Override
    public void sendEvaluationRejectedEmail(MailMessage message) {
        message.setSubject(EmailTemplateLoader.getSubject("evaluationRejected"));
        message.setBody(EmailTemplateLoader.getBody("evaluationRejected"));
        sendMailPort.send(message);
    }

    /**
     * Envía un correo relacionado con la auditoría de la evaluación.
     * 
     * @param message Mensaje de correo a enviar
     */
    @Override
    public void sendAuditEvaluationEmail(MailMessage message) {
        message.setSubject(EmailTemplateLoader.getSubject("auditEvaluation"));
        message.setBody(EmailTemplateLoader.getBody("auditEvaluation"));
        sendMailPort.send(message);
    }

    /**
     * Envía un correo indicando que la evaluación ha finalizado.
     * 
     * @param message Mensaje de correo a enviar
     */
    @Override
    public void sendFinishEvaluationEmail(MailMessage message) {
        message.setSubject(EmailTemplateLoader.getSubject("finishEvaluation"));
        message.setBody(EmailTemplateLoader.getBody("finishEvaluation"));
        sendMailPort.send(message);
    }

    /**
     * Envía un correo solicitando una nueva evaluación al administrador.
     * 
     * @param message Mensaje de correo a enviar
     */
    @Override
    public void sendNewEvaluationRequestEmail(MailMessage message) {
        message.setSubject(EmailTemplateLoader.getSubject("evaluationRequest"));
        message.setBody(EmailTemplateLoader.getBody("evaluationRequest"));
        message.setTo(adminMail);
        sendMailPort.send(message);
    }

    /**
     * Envía un correo indicando que una evidencia ha sido rechazada.
     * 
     * @param message Mensaje de correo a enviar
     */
    @Override
    public void sendEvidenceRejectedEmail(MailMessage message) {
        message.setSubject(EmailTemplateLoader.getSubject("evidenceRejected"));
        message.setBody(EmailTemplateLoader.getBody("evidenceRejected"));
        sendMailPort.send(message);
    }

    /**
     * Envía un correo relacionado con una apelación de evidencia.
     * 
     * @param message Mensaje de correo a enviar
     */
    @Override
    public void sendAppealEvidenceEmail(MailMessage message) {
        message.setSubject(EmailTemplateLoader.getSubject("appealedEvidence"));
        message.setBody(EmailTemplateLoader.getBody("appealedEvidence"));
        sendMailPort.send(message);
    }

    /**
     * Envía un correo solicitando la creación de una nueva institución al
     * administrador.
     * 
     * @param message Mensaje de correo a enviar
     */
    @Override
    public void sendNewInstitutionRequestEmail(MailMessage message) {
        message.setSubject(EmailTemplateLoader.getSubject("institutionRequest"));
        message.setBody(EmailTemplateLoader.getBody("institutionRequest"));
        message.setTo(adminMail);
        sendMailPort.send(message);
    }

    /**
     * Envía un correo con código de recuperación de contraseña
     * 
     * @param message Mensaje de correo a enviar
     */
    @Override
    public void sendRecoveryCodeEmail(MailMessage message, String code) {
        message.setSubject(EmailTemplateLoader.getSubject("recoveryCode"));
        message.setBody(EmailTemplateLoader.getBody("recoveryCode", Map.of("code", code)));
        sendMailPort.send(message);
    }

    /**
     * Envía un correo alertando del cambio de contraseña del usuario
     * 
     * @param message Mensaje de correo a enviar
     */
    @Override
    public void sendNewPasswordAlert(MailMessage message) {
        message.setSubject(EmailTemplateLoader.getSubject("newPassword"));
        message.setBody(EmailTemplateLoader.getBody("newPassword"));
        sendMailPort.send(message);
    }

    @Override
    public void sendAuditorAssigmentEmail(MailMessage message, String newEvaluation) {
        message.setSubject(EmailTemplateLoader.getSubject("newAuditorAssignment"));
        message.setBody(EmailTemplateLoader.getBody("newAuditorAssignment", Map.of("newEvaluation", newEvaluation)));
        sendMailPort.send(message);
    }

}
