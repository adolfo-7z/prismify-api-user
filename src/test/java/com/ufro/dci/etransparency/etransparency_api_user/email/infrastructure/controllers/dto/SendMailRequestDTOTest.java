package com.ufro.dci.etransparency.etransparency_api_user.email.infrastructure.controllers.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ufro.dci.etransparency.etransparency_api_user.email.domain.models.MailMessage;

@ExtendWith(MockitoExtension.class)
class SendMailRequestDTOTest {

    @Test
    void shouldConvertToDomainCorrectly() {
        String to = "juancito@correo.cl";
        String subject = "Asunto Prueba";
        String body = "Ejecutar cuerpo prueba";
        SendMailRequestDTO dto = new SendMailRequestDTO(to, subject, body);
        MailMessage domain = dto.toDomain();
        assertNotNull(domain);
        assertEquals(to, domain.getTo());
        assertEquals(subject, domain.getSubject());
        assertEquals(body, domain.getBody());
    }

    @Test
    void shouldCreateDTOUsingAllArgsConstructor() {
        SendMailRequestDTO dto = new SendMailRequestDTO(
                "juancito@correo.cl",
                "Asunto Prueba",
                "Ejecutar cuerpo prueba");
        assertEquals("juancito@correo.cl", dto.getTo());
        assertEquals("Asunto Prueba", dto.getSubject());
        assertEquals("Ejecutar cuerpo prueba", dto.getBody());
    }

    @Test
    void shouldAllowSettingFieldsViaSetters() {
        SendMailRequestDTO dto = new SendMailRequestDTO();
        dto.setTo("juancito@correo.cl");
        dto.setSubject("Asunto Prueba");
        dto.setBody("Ejecutar cuerpo prueba");
        assertEquals("juancito@correo.cl", dto.getTo());
        assertEquals("Asunto Prueba", dto.getSubject());
        assertEquals("Ejecutar cuerpo prueba", dto.getBody());
    }

}
