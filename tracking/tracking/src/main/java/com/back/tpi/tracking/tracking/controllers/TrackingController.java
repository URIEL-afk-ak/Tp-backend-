package com.back.tpi.tracking.tracking.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.back.tpi.tracking.tracking.entity.TrackingEvento;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.back.tpi.tracking.tracking.service.TrackingService;

@RestController
@RequestMapping("/api/tracking")
@Tag(name = "Tracking", description = "Operaciones de consulta y registro de eventos de tracking de contenedores")
public class TrackingController {

    private final TrackingService service;

    public TrackingController(TrackingService service) { this.service = service; }

    @GetMapping("/contenedores/{id}/eventos")
    @PreAuthorize("hasAnyRole('CLIENTE', 'OPERADOR', 'ADMIN', 'TRANSPORTISTA')")
    @Operation(
        summary = "Obtener eventos por contenedor",
        description = "Devuelve el listado de eventos de tracking asociados a un contenedor",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Listado encontrado",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = TrackingEvento.class))
            )
        }
    )
    public ResponseEntity<List<TrackingEvento>> eventos(@PathVariable Long id) {
        return ResponseEntity.ok(service.listarEventos(id));
    }

    @PostMapping("/eventos")
    @PreAuthorize("hasAnyRole('TRANSPORTISTA', 'OPERADOR', 'ADMIN')")
    @Operation(
        summary = "Registrar nuevo evento",
        description = "Crea un evento de tracking para un contenedor",
        responses = {
            @ApiResponse(
                responseCode = "201",
                description = "Evento creado",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = TrackingEvento.class))
            )
        }
    )
    public ResponseEntity<TrackingEvento> registrar(@RequestBody TrackingEvento evento) {
        TrackingEvento creado = service.registrarEvento(evento);
        return ResponseEntity.status(201).body(creado);
    }
}
