package com.ufro.dci.etransparency.etransparency_api_user.email.application.usecases;

import com.ufro.dci.etransparency.etransparency_api_user.email.domain.models.MailMessage;
import com.ufro.dci.etransparency.etransparency_api_user.email.domain.ports.in.ComposeMailUseCase;
import com.ufro.dci.etransparency.etransparency_api_user.email.domain.ports.out.SendMailPort;

public class ComposeMailUseCaseImpl implements ComposeMailUseCase {

    private final String adminMail;
    private final SendMailPort sendMailPort;

    public ComposeMailUseCaseImpl(String adminMail,
            SendMailPort sendMailPort) {
        this.adminMail = adminMail;
        this.sendMailPort = sendMailPort;
    }

    @Override
    public void sendGenericMail(MailMessage message) {
        sendMailPort.send(message);
    }

    @Override
    public void sendEvaluationRejectedEmail(MailMessage message) {
        message.setSubject("Evaluación Rechazada");
        message.setBody(
                """
                        Estimado/a,

                        Le informamos que la evaluación enviada al sistema de autoevaluación de madurez institucional de e-Transparencia ha sido revisada y **rechazada** por no cumplir con los criterios establecidos para su validación.

                        Motivos comunes de rechazo pueden incluir:
                        - Información incompleta o inconsistente.
                        - Evidencias no adjuntadas o insuficientes.
                        - Cumplimiento parcial de los requisitos del modelo de madurez.

                        Le invitamos cordialmente a revisar el contenido ingresado, realizar las correcciones necesarias y reenviar la evaluación para su análisis. En caso de dudas o requerir asistencia, nuestro equipo de soporte está disponible para orientarle en el proceso.

                        Agradecemos su compromiso con la mejora institucional y la transparencia.

                        Atentamente,
                        Equipo de e-Transparencia
                        """);
        sendMailPort.send(message);
    }

    @Override
    public void sendAuditEvaluationEmail(MailMessage message) {
        message.setSubject("Auditoría Iniciada");
        message.setBody(
                """
                        Estimado/a,

                        Nos complace informarle que su evaluación ha superado la etapa inicial de revisión y ha sido **seleccionada para el proceso de auditoría** dentro del sistema de autoevaluación de madurez institucional de e-Transparencia.

                        Durante esta fase, un auditor asignado a su evaluación verificará la calidad de las evidencias presentadas. Este proceso busca garantizar la objetividad, confiabilidad y validez de los resultados obtenidos.

                        En caso de ser necesario, el equipo auditor podría solicitar información adicional o realizar observaciones que deberán ser atendidas oportunamente.

                        Agradecemos su participación activa y su compromiso con la mejora institucional continua.

                        Atentamente,
                        **Equipo de e-Transparencia**
                        """);
        sendMailPort.send(message);
    }

    @Override
    public void sendFinishEvaluationEmail(MailMessage message) {
        message.setSubject("Finalización de Evaluación - Evaluación de Madurez Institucional");
        message.setBody(
                """
                        Estimado/a,

                        Nos complace informarle que el proceso de evaluación de madurez institucional registrado en e-Transparencia ha sido **finalizado exitosamente**.

                        Los resultados obtenidos han sido consolidados y pueden ser consultados en el sistema. A partir de ellos, se generarán recomendaciones específicas que servirán de guía para futuras acciones estratégicas.

                        Agradecemos profundamente su compromiso con el proceso de autoevaluación y su participación activa durante todas las etapas.

                        Atentamente,
                        Equipo de e-Transparencia
                        """);
        sendMailPort.send(message);
    }

    @Override
    public void sendNewEvaluationRequestEmail(MailMessage message) {
        message.setSubject("Solicitud de Evaluación");
        message.setBody(
                """
                        Estimado/a,

                        Le informamos que se ha generado una **nueva solicitud de evaluación** en el sistema de e-Transparencia.

                        Atentamente,
                        **Equipo de e-Transparencia**
                        """);
        message.setTo(adminMail);
        sendMailPort.send(message);
    }

    @Override
    public void sendEvidenceRejectedEmail(MailMessage message) {
        message.setSubject("Evidencia Rechazada");
        message.setBody(
                """
                        Estimado/a,

                        Le informamos que una de las evidencias presentadas en el marco de la evaluación de e-Transparencia ha sido **rechazada** tras un proceso de revisión técnica.

                        Entre las posibles razones del rechazo se encuentran:
                        - Evidencia no relacionada con el criterio evaluado.
                        - Información insuficiente o poco clara.
                        - Documentación ilegible o con errores de formato.

                        Le solicitamos revisar la observación registrada en el sistema y proceder con la **apelación** del documento correspondiente.

                        Agradecemos su atención y compromiso con la mejora institucional.

                        Atentamente,
                        **Equipo de e-Transparencia**
                        """);
        sendMailPort.send(message);
    }

    @Override
    public void sendAppealEvidenceEmail(MailMessage message) {
        message.setSubject("Evidencia Apelada");
        message.setBody(
                """
                        Estimado/a Auditor/a,

                        Se ha registrado una **apelación** en el sistema de e-Transparencia relacionada con una evidencia previamente rechazada.

                        Le solicitamos revisar la nueva información proporcionada por la institución evaluada, junto con los argumentos adjuntos en la apelación, y determinar si procede la aceptación de dicha evidencia.

                        Atentamente,
                        **Equipo de e-Transparencia**
                        """);
        sendMailPort.send(message);
    }

    @Override
    public void sendNewInstitutionRequestEmail(MailMessage message) {
        message.setSubject("Solicitud de Registro de Institución");
        message.setBody(
                """
                        Estimado/a,

                        Se ha registrado una **nueva solicitud de inscripción** de una institución en el sistema de e-Transparencia.

                        Le solicitamos ingresar al sistema para revisar los datos proporcionados y proceder con la validación o rechazo correspondiente.

                        Atentamente,
                        **Equipo de e-Transparencia**
                        """);
        message.setTo(adminMail);
        sendMailPort.send(message);
    }
}
