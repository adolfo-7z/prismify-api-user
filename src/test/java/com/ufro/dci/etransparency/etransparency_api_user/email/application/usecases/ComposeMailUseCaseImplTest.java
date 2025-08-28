package com.ufro.dci.etransparency.etransparency_api_user.email.application.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ufro.dci.etransparency.etransparency_api_user.email.domain.models.MailMessage;
import com.ufro.dci.etransparency.etransparency_api_user.email.domain.ports.out.SendMailPort;

@ExtendWith(MockitoExtension.class)
class ComposeMailUseCaseImplTest {

    private final String adminMail = "admin@correo.cl";

    @Mock
    private SendMailPort sendMailPort;

    private ComposeMailUseCaseImpl composeMailUseCase;

    @BeforeEach
    void setUp() {
        composeMailUseCase = new ComposeMailUseCaseImpl(adminMail, sendMailPort);
    }

    @Test
    void testSendGenericMail() {
        MailMessage message = new MailMessage();
        composeMailUseCase.sendGenericMail(message);
        verify(sendMailPort).send(message);
    }

    @Test
    void testSendEvaluationRejectedEmail() {
        MailMessage message = new MailMessage();
        composeMailUseCase.sendEvaluationRejectedEmail(message);
        assertEquals("Evaluación Rechazada", message.getSubject());
        assertTrue(message.getBody().contains("rechazada"));
        verify(sendMailPort).send(message);
    }

    @Test
    void testSendAuditEvaluationEmail() {
        MailMessage message = new MailMessage();
        composeMailUseCase.sendAuditEvaluationEmail(message);
        assertEquals("Auditoría Iniciada", message.getSubject());
        assertTrue(message.getBody().contains("auditor"));
        verify(sendMailPort).send(message);
    }

    @Test
    void testSendFinishEvaluationEmail() {
        MailMessage message = new MailMessage();
        composeMailUseCase.sendFinishEvaluationEmail(message);
        assertEquals("Evaluación Finalizada", message.getSubject());
        assertTrue(message.getBody().contains("finalizado exitosamente"));
        verify(sendMailPort).send(message);
    }

    @Test
    void testSendNewEvaluationRequestEmail() {
        MailMessage message = new MailMessage();
        composeMailUseCase.sendNewEvaluationRequestEmail(message);
        assertEquals("Solicitud de Evaluación", message.getSubject());
        assertTrue(message.getBody().contains("nueva solicitud"));
        assertEquals("admin@correo.cl", message.getTo());
        verify(sendMailPort).send(message);
    }

    @Test
    void testSendEvidenceRejectedEmail() {
        MailMessage message = new MailMessage();
        composeMailUseCase.sendEvidenceRejectedEmail(message);
        assertEquals("Evidencia Rechazada", message.getSubject());
        assertTrue(message.getBody().contains("rechazada"));
        verify(sendMailPort).send(message);
    }

    @Test
    void testSendAppealEvidenceEmail() {
        MailMessage message = new MailMessage();
        composeMailUseCase.sendAppealEvidenceEmail(message);
        assertEquals("Evidencia Apelada", message.getSubject());
        assertTrue(message.getBody().contains("apelación"));
        verify(sendMailPort).send(message);
    }

    @Test
    void testSendNewInstitutionRequestEmail() {
        MailMessage message = new MailMessage();
        composeMailUseCase.sendNewInstitutionRequestEmail(message);
        assertEquals("Solicitud de Registro de Institución", message.getSubject());
        assertTrue(message.getBody().contains("nueva solicitud de inscripción"));
        assertEquals("admin@correo.cl", message.getTo());
        verify(sendMailPort).send(message);
    }

}
