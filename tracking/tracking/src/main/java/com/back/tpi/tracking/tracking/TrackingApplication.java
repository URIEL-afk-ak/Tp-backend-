package com.back.tpi.tracking.tracking;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
    info = @Info(
        title = "API de Tracking",
        version = "1.0.0",
        description = "Operaciones de tracking de eventos para contenedores"
    ),
    security = @SecurityRequirement(name = "bearerAuth")
)
@SpringBootApplication
public class TrackingApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrackingApplication.class, args);
    }

}
