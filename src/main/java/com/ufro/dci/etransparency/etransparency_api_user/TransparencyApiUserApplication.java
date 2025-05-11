package com.ufro.dci.etransparency.etransparency_api_user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TransparencyApiUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransparencyApiUserApplication.class, args);
	}

}
