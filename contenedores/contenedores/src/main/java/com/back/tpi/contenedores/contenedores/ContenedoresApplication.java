package com.back.tpi.contenedores.contenedores;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
		info = @Info(
				title = "API de Contenedores",
				version = "1.0.0",
				description = "Gestión de contenedores y estados logísticos"
		),
		security = @SecurityRequirement(name = "bearerAuth")
)
@SpringBootApplication
public class ContenedoresApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContenedoresApplication.class, args);
	}

}
