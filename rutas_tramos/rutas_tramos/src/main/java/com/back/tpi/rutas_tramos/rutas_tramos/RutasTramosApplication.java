package com.back.tpi.rutas_tramos.rutas_tramos;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
		info = @Info(
				title = "API de Rutas y Tramos",
				version = "1.0.0",
				description = "Gestión de rutas, tramos y cálculo de distancias"
		),
		security = @SecurityRequirement(name = "bearerAuth")
)
@SpringBootApplication
public class RutasTramosApplication {

	public static void main(String[] args) {
		SpringApplication.run(RutasTramosApplication.class, args);
	}

}
