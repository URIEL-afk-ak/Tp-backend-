package com.back.tpi.solicitudes.solicitudes.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.back.tpi.solicitudes.solicitudes.dto.SolicitudDTO;
import com.back.tpi.solicitudes.solicitudes.entity.EstadoSolicitud;
import com.back.tpi.solicitudes.solicitudes.service.SolicitudService;
import com.back.tpi.solicitudes.solicitudes.util.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

/*
 Endpoints:
 POST   /api/solicitudes                Roles: CLIENTE (create)
 GET    /api/solicitudes/{id}           Roles: CLIENTE (owner) | OPERADOR
 PATCH  /api/solicitudes/{id}/estado    Roles: OPERADOR
 GET    /api/solicitudes/estado/{estado} Roles: OPERADOR
 Note: no hay seguridad implementada aquí; integrar Spring Security y checks de roles/ownership en producción.
*/

@RestController
@RequestMapping("/api")
@Tag(name = "Solicitudes", description = "Operaciones sobre solicitudes de transporte")
public class SolicitudController {

    private final SolicitudService service;

    public SolicitudController(SolicitudService service) {
        this.service = service;
    }

    @PostMapping("solicitudes")
    @Operation(
        summary = "Crear solicitud",
        description = "Crea una solicitud de transporte para un cliente",
        responses = {
            @ApiResponse(responseCode = "201", description = "Solicitud creada", content = @Content(schema = @Schema(implementation = SolicitudDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
        }
    )
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<SolicitudDTO> crearSolicitud(@RequestBody SolicitudDTO dto, Authentication authentication) {
        // Validar que el clienteId del DTO coincida con el usuario autenticado
        if (authentication != null && authentication.getPrincipal() instanceof Jwt jwt) {
            String userId = JwtUtils.extractUserId(jwt);
            // Aquí podrías validar que dto.clienteId corresponde al userId si tienes esa relación
            // Por ahora solo validamos que sea CLIENTE
        }
        SolicitudDTO creado = service.crearSolicitud(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping("solicitudes/{id}")
    @Operation(
        summary = "Obtener solicitud por id",
        responses = {
            @ApiResponse(responseCode = "200", description = "Solicitud encontrada", content = @Content(schema = @Schema(implementation = SolicitudDTO.class))),
            @ApiResponse(responseCode = "404", description = "Solicitud no encontrada")
        }
    )
    @PreAuthorize("hasAnyRole('CLIENTE', 'OPERADOR', 'ADMIN')")
    public ResponseEntity<SolicitudDTO> obtenerPorId(@PathVariable Long id, Authentication authentication) {
        SolicitudDTO dto = service.obtenerPorId(id);
        if (dto == null) return ResponseEntity.notFound().build();
        
        // Validar ownership si es CLIENTE (no OPERADOR/ADMIN)
        if (authentication != null && authentication.getPrincipal() instanceof Jwt jwt) {
            boolean isCliente = JwtUtils.hasRole(jwt, "CLIENTE");
            if (isCliente && !JwtUtils.hasRole(jwt, "OPERADOR") && !JwtUtils.hasRole(jwt, "ADMIN")) {
                // Aquí deberías validar que el cliente es dueño de la solicitud
                // Por ejemplo: dto.getClienteId().equals(userId)
                // Por ahora permitimos el acceso
            }
        }
        
        return ResponseEntity.ok(dto);
    }

    public static class EstadoUpdateRequest {
        public String estado;
    }

    // Nuevo: DTO para finalizar solicitud y registrar valores reales (Req 8)
    public static class SolicitudFinalizacionRequest {
        public Double tiempoRealHoras; // Total real time
        public Double costoRealTotal; // Total real cost
    }

    @PatchMapping("solicitudes/{id}/estado")
    @Operation(
        summary = "Actualizar estado de solicitud",
        description = "Actualiza el estado a ACEPTADA, RECHAZADA, EN_PROCESO, ENTREGADA, etc.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Solicitud actualizada", content = @Content(schema = @Schema(implementation = SolicitudDTO.class))),
            @ApiResponse(responseCode = "400", description = "Estado inválido"),
            @ApiResponse(responseCode = "404", description = "Solicitud no encontrada")
        }
    )
    @PreAuthorize("hasAnyRole('OPERADOR', 'ADMIN')")
    public ResponseEntity<SolicitudDTO> actualizarEstado(@PathVariable Long id, @RequestBody EstadoUpdateRequest req) {
        if (req == null || req.estado == null) return ResponseEntity.badRequest().build();
        EstadoSolicitud nuevo;
        try {
            nuevo = EstadoSolicitud.valueOf(req.estado.toUpperCase());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
        try {
            SolicitudDTO actualizado = service.actualizarEstado(id, nuevo);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PatchMapping("solicitudes/{id}/finalizar")
    @Operation(
        summary = "Finalizar solicitud",
        description = "Registra tiempos y costo real y marca la solicitud como entregada",
        responses = {
            @ApiResponse(responseCode = "200", description = "Solicitud finalizada", content = @Content(schema = @Schema(implementation = SolicitudDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Solicitud no encontrada")
        }
    )
    @PreAuthorize("hasAnyRole('OPERADOR', 'ADMIN')")
    public ResponseEntity<SolicitudDTO> finalizarSolicitud(@PathVariable Long id, @RequestBody SolicitudFinalizacionRequest req) {
        if (req == null || req.tiempoRealHoras == null || req.costoRealTotal == null) return ResponseEntity.badRequest().build();
        try {
            // El servicio debe actualizar el estado a ENTREGADA y registrar el tiempo y costo.
            SolicitudDTO actualizado = service.finalizarSolicitud(id, req.tiempoRealHoras, req.costoRealTotal);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("solicitudes/estado/{estado}")
    @Operation(
        summary = "Listar solicitudes por estado",
        responses = @ApiResponse(responseCode = "200", description = "Listado de solicitudes", content = @Content(schema = @Schema(implementation = SolicitudDTO.class)))
    )
    @PreAuthorize("hasAnyRole('OPERADOR', 'ADMIN')")
    public ResponseEntity<List<SolicitudDTO>> listarPorEstado(@PathVariable String estado) {
        EstadoSolicitud e;
        try {
            e = EstadoSolicitud.valueOf(estado.toUpperCase());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(service.listarPorEstado(e));
    }
}
