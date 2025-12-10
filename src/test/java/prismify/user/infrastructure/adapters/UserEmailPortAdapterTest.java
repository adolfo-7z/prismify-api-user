package prismify.user.infrastructure.adapters;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import prismify.user.infrastructure.controllers.dto.user.SendMailRequestDTO;
import prismify.user.infrastructure.controllers.exception.custom.UserEmailPortException;

@ExtendWith(MockitoExtension.class)
class UserEmailPortAdapterTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private UserEmailPortAdapter adapter;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(adapter, "userApiUrl", "http://mock-user-api/v1/api");
    }

    @Test
    void sendRecoveryCodeEmail_ShouldInvokeCorrectEndpoint() {
        String to = "prueba@correo.cl";
        String code = "1234";
        adapter.sendRecoveryCodeEmail(to, code);
        ArgumentCaptor<String> urlCaptor = ArgumentCaptor.forClass(String.class);

        @SuppressWarnings("unchecked")
        ArgumentCaptor<HttpEntity<SendMailRequestDTO>> entityCaptor = ArgumentCaptor.forClass(HttpEntity.class);
        verify(restTemplate).postForEntity(urlCaptor.capture(), entityCaptor.capture(), eq(Void.class));
        assertEquals("http://mock-user-api/v1/api/internal/mail/send/recovery/code/1234",
                urlCaptor.getValue());
        SendMailRequestDTO body = entityCaptor.getValue().getBody();
        assertNotNull(body);
        assertEquals(to, body.getTo());
        assertEquals(MediaType.APPLICATION_JSON,
                entityCaptor.getValue().getHeaders().getContentType());
    }

    @Test
    void sendNewPasswordAlert_ShouldInvokeCorrectEndpoint() {
        String to = "prueba@correo.cl";
        adapter.sendNewPasswordAlert(to);
        ArgumentCaptor<String> urlCaptor = ArgumentCaptor.forClass(String.class);

        @SuppressWarnings("unchecked")
        ArgumentCaptor<HttpEntity<SendMailRequestDTO>> entityCaptor = ArgumentCaptor.forClass(HttpEntity.class);
        verify(restTemplate).postForEntity(urlCaptor.capture(), entityCaptor.capture(), eq(Void.class));
        assertEquals("http://mock-user-api/v1/api/internal/mail/send/recovery/new",
                urlCaptor.getValue());
        SendMailRequestDTO body = entityCaptor.getValue().getBody();
        assertNotNull(body);
        assertEquals(to, body.getTo());
    }

    @Test
    void postMail_WhenRestClientException_ShouldThrowUserEmailPortException() {
        doThrow(new RestClientException("error"))
                .when(restTemplate)
                .postForEntity(anyString(), any(), eq(Void.class));
        assertThrows(UserEmailPortException.class,
                () -> adapter.sendNewPasswordAlert("mock-user-api"));
    }

}
