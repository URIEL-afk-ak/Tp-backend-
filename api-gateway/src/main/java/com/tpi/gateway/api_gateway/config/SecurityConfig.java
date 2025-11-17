// Archivo: SecurityConfig.java

package com.tpi.gateway.api_gateway.config;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(
            ServerHttpSecurity http,
            org.springframework.security.oauth2.jwt.ReactiveJwtDecoder jwtDecoder) {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeExchange(exchanges -> exchanges
                // Permitir acceso a rutas públicas si es necesario (ej: /auth/login)
                // .pathMatchers("/auth/**").permitAll() 
                // Todas las demás peticiones deben estar autenticadas
                .anyExchange().authenticated() 
            )
            .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtDecoder(jwtDecoder))); 

        return http.build();
    }
    
    /**
     * Filtro Global para propagar el token JWT a los microservicios de backend.
     * Si no se hace esto, el microservicio no recibirá el token y fallará el @PreAuthorize.
     */
    @Bean
    public GlobalFilter tokenPropagationFilter() {
        return new GlobalFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                // Obtener el contexto de seguridad reactivo
                return ReactiveSecurityContextHolder.getContext()
                    .map(SecurityContext::getAuthentication)
                    .filter(auth -> auth instanceof JwtAuthenticationToken)
                    .cast(JwtAuthenticationToken.class)
                    .map(JwtAuthenticationToken::getToken)
                    .map(Jwt::getTokenValue)
                    .flatMap(token -> {
                        // Crear un request decorator que agrega el header Authorization
                        ServerHttpRequestDecorator decoratedRequest = new ServerHttpRequestDecorator(exchange.getRequest()) {
                            @Override
                            public HttpHeaders getHeaders() {
                                HttpHeaders headers = new HttpHeaders();
                                headers.putAll(super.getHeaders());
                                // Asegurar que el header Authorization se propague al backend
                                headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);
                                return headers;
                            }
                        };
                        
                        ServerWebExchange mutatedExchange = exchange.mutate()
                            .request(decoratedRequest)
                            .build();
                        return chain.filter(mutatedExchange);
                    })
                    .switchIfEmpty(chain.filter(exchange));
            }
        };
    }

    @Bean
    public org.springframework.security.oauth2.jwt.ReactiveJwtDecoder jwtDecoder(
            @org.springframework.beans.factory.annotation.Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}") String issuerUri) {
        return org.springframework.security.oauth2.jwt.ReactiveJwtDecoders.fromIssuerLocation(issuerUri);
    }
}