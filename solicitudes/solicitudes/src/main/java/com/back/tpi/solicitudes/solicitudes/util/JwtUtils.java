package com.back.tpi.solicitudes.solicitudes.util;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Utilidad para extraer información del token JWT de Keycloak
 */
public class JwtUtils {

    /**
     * Extrae los roles del JWT. Keycloak puede enviar roles en diferentes formatos.
     */
    public static List<String> extractRoles(Jwt jwt) {
        // Intentar obtener roles de realm_access
        Object realmAccess = jwt.getClaim("realm_access");
        if (realmAccess instanceof java.util.Map) {
            @SuppressWarnings("unchecked")
            java.util.Map<String, Object> realmAccessMap = (java.util.Map<String, Object>) realmAccess;
            Object rolesObj = realmAccessMap.get("roles");
            if (rolesObj instanceof List) {
                @SuppressWarnings("unchecked")
                List<String> realmRoles = (List<String>) rolesObj;
                return realmRoles;
            }
        }
        
        // Si no hay roles en realm_access, devolver lista vacía
        return List.of();
    }

    /**
     * Extrae el subject (username) del JWT
     */
    public static String extractUsername(Jwt jwt) {
        return jwt.getSubject();
    }

    /**
     * Extrae el email del JWT
     */
    public static String extractEmail(Jwt jwt) {
        return jwt.getClaimAsString("email");
    }

    /**
     * Verifica si el JWT tiene un rol específico
     */
    public static boolean hasRole(Jwt jwt, String role) {
        List<String> roles = extractRoles(jwt);
        return roles.contains(role.toUpperCase()) || roles.contains(role.toLowerCase());
    }

    /**
     * Convierte roles de Keycloak a GrantedAuthority de Spring Security
     */
    public static Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
        List<String> roles = extractRoles(jwt);
        return roles.stream()
            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
            .collect(Collectors.toList());
    }

    /**
     * Extrae el ID del usuario desde el JWT
     */
    public static String extractUserId(Jwt jwt) {
        // Keycloak usa "sub" como identificador único del usuario
        return jwt.getSubject();
    }
}

