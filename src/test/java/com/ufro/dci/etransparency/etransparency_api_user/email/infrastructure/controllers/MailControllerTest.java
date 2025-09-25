package com.ufro.dci.etransparency.etransparency_api_user.email.infrastructure.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.ufro.dci.etransparency.etransparency_api_user.email.application.services.MailService;
import com.ufro.dci.etransparency.etransparency_api_user.email.infrastructure.controllers.dto.SendMailRequestDTO;

@WebMvcTest(MailController.class)
@AutoConfigureMockMvc(addFilters = false)
class MailControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MailService mailService;

    private SendMailRequestDTO request;

    @BeforeEach
    void setUp() {
        request = new SendMailRequestDTO();
        request.setTo("prueba@correo.ncl");
        request.setSubject("Asunto Prueba");
        request.setBody("Cuerpo Prueba");
    }

    private String toJson(Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }

    @Test
    void shouldSendGenericMail() throws Exception {
        mockMvc.perform(post("/internal/mail/send")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request)))
                .andExpect(status().isOk());
        verify(mailService).sendGenericMail(any());
    }

    @Test
    void shouldSendEvaluationRejectedEmail() throws Exception {
        mockMvc.perform(post("/internal/mail/send/evaluation/rejected")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request)))
                .andExpect(status().isOk());
        verify(mailService).sendEvaluationRejectedEmail(any());
    }

    @Test
    void shouldSendAuditEvaluationEmail() throws Exception {
        mockMvc.perform(post("/internal/mail/send/evaluation/audit")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request)))
                .andExpect(status().isOk());
        verify(mailService).sendAuditEvaluationEmail(any());
    }

    @Test
    void shouldSendFinishEvaluationEmail() throws Exception {
        mockMvc.perform(post("/internal/mail/send/evaluation/finish")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request)))
                .andExpect(status().isOk());
        verify(mailService).sendFinishEvaluationEmail(any());
    }

    @Test
    void shouldSendNewEvaluationRequestEmail() throws Exception {
        mockMvc.perform(post("/internal/mail/send/evaluation")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request)))
                .andExpect(status().isOk());
        verify(mailService).sendNewEvaluationRequestEmail(any());
    }

    @Test
    void shouldSendEvidenceRejectedEmail() throws Exception {
        String evaluationName = "EvaluationTest";
        mockMvc.perform(post("/internal/mail/send/evidence/rejected/"+evaluationName)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request)))
                .andExpect(status().isOk());
        verify(mailService).sendEvidenceRejectedEmail(any(), anyString());
    }

    @Test
    void shouldSendAppealEvidenceEmail() throws Exception {
        String evaluationName = "EvaluationTest";
        mockMvc.perform(post("/internal/mail/send/evidence/appeal/"+evaluationName)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request)))
                .andExpect(status().isOk());
        verify(mailService).sendAppealEvidenceEmail(any(), anyString());
    }

    @Test
    void shouldSendNewInstitutionRequestEmail() throws Exception {
        mockMvc.perform(post("/internal/mail/send/institution")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request)))
                .andExpect(status().isOk());
        verify(mailService).sendNewInstitutionRequestEmail(any());
    }

}
