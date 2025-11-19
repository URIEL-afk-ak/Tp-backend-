package com.back.tpi.depositos.depositos;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
		info = @Info(
				title = "API de Depósitos",
				version = "1.0.0",
				description = "Gestión de depósitos y estadías"
		),
		security = @SecurityRequirement(name = "bearerAuth")
)
@SpringBootApplication
public class DepositosApplication {

	public static void main(String[] args) {
		SpringApplication.run(DepositosApplication.class, args);
	}

}
