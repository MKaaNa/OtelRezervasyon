package com.MKaaN.OtelBackend;

import com.MKaaN.OtelBackend.services.auth.AuthService;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OtelBackendApplication {

	private final AuthService authService;

	public OtelBackendApplication(AuthService authService) {
		this.authService = authService;
	}

	public static void main(String[] args) {
		SpringApplication.run(OtelBackendApplication.class, args);

	}

}


