package com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.config;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ufro.dci.etransparency.etransparency_api_user.user.domain.ports.in.CreateUserIfNotExistsUseCase;

@ExtendWith(MockitoExtension.class)
class AdminInitializerConfigTest {

    @Mock
    private CreateUserIfNotExistsUseCase createUserIfNotExistsUseCase;

    @InjectMocks
    private AdminInitializerConfig config;

    @Captor
    ArgumentCaptor<String> passwordCaptor;

    @BeforeEach
    void setUp() throws Exception {
        injectField("defaultUsername", "admin");
        injectField("defaultEmail", "admin@etransparencia.cl");
        injectField("defaultPassword", "transparencia123");
    }

    @Test
    void shouldCreateAdminIfMissingOnApplicationEvent() {
        ApplicationReadyEvent event = mock(ApplicationReadyEvent.class);
        config.onApplicationEvent(event);
        verify(createUserIfNotExistsUseCase, times(1))
                .createAdminIfMissing(eq("admin"), eq("admin@etransparencia.cl"), passwordCaptor.capture());
        String encodedPassword = passwordCaptor.getValue();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        assertThat(encoder.matches("transparencia123", encodedPassword)).isTrue();
    }

    private void injectField(String fieldName, String value) throws Exception {
        Field field = AdminInitializerConfig.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(config, value);
    }

}
