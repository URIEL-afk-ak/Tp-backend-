package com.back.tpi.camiones.camiones;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
		info = @Info(
				title = "API de Camiones",
				version = "1.0.0",
				description = "Gestión de camiones y transportistas"
		),
		security = @SecurityRequirement(name = "bearerAuth")
)
@SpringBootApplication
public class CamionesApplication {

	public static void main(String[] args) {
		SpringApplication.run(CamionesApplication.class, args);
	}

}
