package com.ufro.dci.etransparency.etransparency_api_user.config;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ufro.dci.etransparency.etransparency_api_user.models.administrator.Administrator;
import com.ufro.dci.etransparency.etransparency_api_user.repositories.administrator.AdministratorRepository;

@SpringBootTest
 class DataInitializerConfigTest {

    @Value("${spring.application.default-username}")
    private String defaultUsername;

    @Value("${spring.application.default-email}")
    private String defaultEmail;

    @Value("${spring.application.default-password}")
    private String defaultPassword;

    @Mock
    private AdministratorRepository administratorRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private DataInitializerConfig dataInitializerConfig;

    @BeforeEach
     void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void testInitDataAdminExists() throws Exception {
        when(administratorRepository.existsByUsername(defaultUsername)).thenReturn(true);

        CommandLineRunner runner = dataInitializerConfig.initData();
        runner.run();

        verify(administratorRepository, never()).save(any(Administrator.class));
    }

}
