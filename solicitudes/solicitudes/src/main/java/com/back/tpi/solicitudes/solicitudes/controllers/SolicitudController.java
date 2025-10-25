package com.back.tpi.solicitudes.solicitudes.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class SolicitudController {

    private final SolicitudService service;

    public SolicitudController(SolicitudService service) {
        this.service = service;
    }

    @PostMapping("solicitudes")
    public ResponseEntity<SolicitudDTO> crearSolicitud(@RequestBody SolicitudDTO dto) {
        // En producción validar que el caller es CLIENTE y que dto.clienteId coincide con el userId
        SolicitudDTO creado = service.crearSolicitud(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping("solicitudes/{id}")
    public ResponseEntity<SolicitudDTO> obtenerPorId(@PathVariable Long id) {
        // En producción validar permisos: owner (cliente) o operador
        SolicitudDTO dto = service.obtenerPorId(id);
        if (dto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }

    public static class EstadoUpdateRequest {
        public String estado;
    }

    @PatchMapping("solicitudes/{id}/estado")
    public ResponseEntity<SolicitudDTO> actualizarEstado(@PathVariable Long id, @RequestBody EstadoUpdateRequest req) {
        // En producción validar rol OPERADOR
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

    @GetMapping("solicitudes/estado/{estado}")
    public ResponseEntity<List<SolicitudDTO>> listarPorEstado(@PathVariable String estado) {
        // En producción validar rol OPERADOR
        EstadoSolicitud e;
        try {
            e = EstadoSolicitud.valueOf(estado.toUpperCase());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(service.listarPorEstado(e));
    }
}