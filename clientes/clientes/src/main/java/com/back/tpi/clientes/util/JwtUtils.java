package com.back.tpi.clientes.util;

import org.springframework.security.oauth2.jwt.Jwt;
import java.util.List;

/**
 * Utilidad para extraer información del token JWT de Keycloak
 */
public class JwtUtils {

    public static List<String> extractRoles(Jwt jwt) {
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
        return List.of();
    }

    public static String extractUsername(Jwt jwt) {
        return jwt.getSubject();
    }

    public static String extractEmail(Jwt jwt) {
        return jwt.getClaimAsString("email");
    }

    public static boolean hasRole(Jwt jwt, String role) {
        List<String> roles = extractRoles(jwt);
        return roles.contains(role.toUpperCase()) || roles.contains(role.toLowerCase());
    }

    public static String extractUserId(Jwt jwt) {
        return jwt.getSubject();
    }
}


