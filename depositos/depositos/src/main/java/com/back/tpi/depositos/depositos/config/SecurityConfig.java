package com.back.tpi.depositos.depositos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private static final Set<String> ROLES_POR_DEFECTO = Set.of(
        "DEFAULT-ROLES-TPI-REALM",
        "OFFLINE_ACCESS",
        "UMA_AUTHORIZATION"
    );

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            org.springframework.security.oauth2.jwt.JwtDecoder jwtDecoder) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                // Swagger/OpenAPI - permitir en desarrollo
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                
                // Endpoints específicos por rol
                // Depósitos - Solo OPERADOR y ADMIN
                .requestMatchers("/api/depositos").hasAnyRole("OPERADOR", "ADMIN")
                .requestMatchers("/api/depositos/**").hasAnyRole("OPERADOR", "ADMIN")
                
                // Estadías - Solo OPERADOR y ADMIN
                .requestMatchers("/api/estadias/**").hasAnyRole("OPERADOR", "ADMIN")
                
                .anyRequest().authenticated()
            )
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt
                    .decoder(jwtDecoder)
                    .jwtAuthenticationConverter(jwtAuthenticationConverter())
                )
            );

        return http.build();
    }

    @Bean
    public Converter<Jwt, AbstractAuthenticationToken> jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(new KeycloakJwtGrantedAuthoritiesConverter());
        return jwtConverter;
    }

    private static class KeycloakJwtGrantedAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
        
        @Override
        public Collection<GrantedAuthority> convert(Jwt jwt) {
            Object realmAccess = jwt.getClaim("realm_access");
            
            if (!(realmAccess instanceof Map<?, ?> realmAccessMap)) {
                return Collections.emptyList();
            }
            Object rolesObj = realmAccessMap.get("roles");
            if (!(rolesObj instanceof List<?> roles)) {
                return Collections.emptyList();
            }

            return roles.stream()
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .map(String::trim)
                .filter(role -> !role.isEmpty())
                .map(String::toUpperCase)
                .filter(role -> !ROLES_POR_DEFECTO.contains(role))
                .filter(role -> !role.startsWith("DEFAULT-"))
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
        }
    }

    @Bean
    public org.springframework.security.oauth2.jwt.JwtDecoder jwtDecoder(
            @org.springframework.beans.factory.annotation.Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}") String issuerUri) {
        return org.springframework.security.oauth2.jwt.NimbusJwtDecoder.withIssuerLocation(issuerUri).build();
    }
}

