package com.back.tpi.solicitudes.solicitudes;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
		info = @Info(
				title = "API de Solicitudes",
				version = "1.0.0",
				description = "Gestión de solicitudes de transporte"
		),
		security = @SecurityRequirement(name = "bearerAuth")
)
@SpringBootApplication
public class SolicitudesApplication {

	public static void main(String[] args) {
		SpringApplication.run(SolicitudesApplication.class, args);
	}

}
