package prismify.email.application.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import prismify.email.application.support.EmailTemplateLoader;
import prismify.email.domain.models.MailMessage;
import prismify.email.domain.ports.out.SendMailPort;

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
        String evaluationName = "EvaluationTest";
        composeMailUseCase.sendEvidenceRejectedEmail(message, evaluationName);
        assertEquals("Evidencia Rechazada", message.getSubject());
        assertTrue(message.getBody().contains("rechazada"));
        assertTrue(message.getBody().contains(evaluationName));
        verify(sendMailPort).send(message);
    }

    @Test
    void testSendAppealEvidenceEmail() {
        MailMessage message = new MailMessage();
        String evaluationName = "EvaluationTest";
        composeMailUseCase.sendAppealEvidenceEmail(message, evaluationName);
        assertEquals("Evidencia Apelada", message.getSubject());
        assertTrue(message.getBody().contains("apelación"));
        assertTrue(message.getBody().contains(evaluationName));
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

    @Test
    void testSendRecoveryCodeEmail() {
        try (MockedStatic<EmailTemplateLoader> utilities = Mockito.mockStatic(EmailTemplateLoader.class)) {
            String code = "ABC123";
            MailMessage message = new MailMessage();
            utilities.when(() -> EmailTemplateLoader.getSubject("recoveryCode"))
                    .thenReturn("Código de Recuperación");
            utilities.when(() -> EmailTemplateLoader.getBody("recoveryCode", Map.of("code", code)))
                    .thenReturn("Tu código de recuperación es: ABC123");
            composeMailUseCase.sendRecoveryCodeEmail(message, code);
            assertEquals("Código de Recuperación", message.getSubject());
            assertEquals("Tu código de recuperación es: ABC123", message.getBody());
            verify(sendMailPort).send(message);
        }
    }

    @Test
    void testSendNewPasswordAlert() {
        try (MockedStatic<EmailTemplateLoader> utilities = Mockito.mockStatic(EmailTemplateLoader.class)) {
            MailMessage message = new MailMessage();
            utilities.when(() -> EmailTemplateLoader.getSubject("newPassword"))
                    .thenReturn("Nueva Contraseña Generada");
            utilities.when(() -> EmailTemplateLoader.getBody("newPassword"))
                    .thenReturn("Se ha generado una nueva contraseña para tu cuenta.");
            composeMailUseCase.sendNewPasswordAlert(message);
            assertEquals("Nueva Contraseña Generada", message.getSubject());
            assertEquals("Se ha generado una nueva contraseña para tu cuenta.", message.getBody());
            verify(sendMailPort).send(message);
        }
    }

    @Test
    void testSendAuditorAssigmentEmail() {
        try (MockedStatic<EmailTemplateLoader> utilities = Mockito.mockStatic(EmailTemplateLoader.class)) {
            String evaluationName = "Evaluación 001";
            MailMessage message = new MailMessage();
            utilities.when(() -> EmailTemplateLoader.getSubject("newAuditorAssignment"))
                    .thenReturn("Nuevo Auditor Asignado");
            utilities.when(() -> EmailTemplateLoader.getBody("newAuditorAssignment",
                    Map.of("newEvaluation", evaluationName)))
                    .thenReturn("Has sido asignado a la evaluación Evaluación 001");
            composeMailUseCase.sendAuditorAssigmentEmail(message, evaluationName);
            assertEquals("Nuevo Auditor Asignado", message.getSubject());
            assertEquals("Has sido asignado a la evaluación Evaluación 001", message.getBody());
            verify(sendMailPort).send(message);
        }
    }

}
