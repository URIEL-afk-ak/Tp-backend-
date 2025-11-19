package com.tpi.gateway.api_gateway.config;

import org.springdoc.core.properties.SwaggerUiConfigParameters;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayOpenApiConfiguration {

    @Bean
    public CommandLineRunner openApiGroups(
            DiscoveryClient discoveryClient,
            SwaggerUiConfigParameters swaggerUiConfigParameters) {

        return args -> discoveryClient.getServices().stream()
                .filter(service -> !service.equalsIgnoreCase("api-gateway"))
                .forEach(swaggerUiConfigParameters::addGroup);
    }
}
