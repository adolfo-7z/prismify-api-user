package com.ufro.dci.etransparency.etransparency_api_user;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(locations="classpath:test.properties")
class TransparencyApiUserApplicationTests {

	@Test
	void contextLoads() {
	}

}
