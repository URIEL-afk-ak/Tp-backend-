package com.back.tpi.rutas_tramos.rutas_tramos.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.back.tpi.rutas_tramos.rutas_tramos.dto.TramoDTO;
import com.back.tpi.rutas_tramos.rutas_tramos.entity.EstadoTramo;
import com.back.tpi.rutas_tramos.rutas_tramos.entity.Tramo;
import com.back.tpi.rutas_tramos.rutas_tramos.service.RutasService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api")
@Tag(name = "Rutas y Tramos", description = "Operaciones para asignar rutas y administrar tramos")
public class RutasController {

    private final RutasService service;

    public RutasController(RutasService service) { this.service = service; }

    public static class PreviewRequest { public Long solicitudId; public String origen; public String destino; public Double distanciaKm; }

    public static class AsignarCamionRequest { public Long camionId; }
    
    // Nuevo: Asignar un camión a un tramo (Req 4)
    @PatchMapping("/tramos/{id}/asignarCamion")
    @Operation(
        summary = "Asignar camión a tramo",
        description = "Asigna un camión a un tramo y valida capacidad",
        responses = {
            @ApiResponse(responseCode = "200", description = "Tramo actualizado", content = @Content(schema = @Schema(implementation = Tramo.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Tramo no encontrado")
        }
    )
    public ResponseEntity<Tramo> asignarCamionATramo(@PathVariable Long id, @RequestBody AsignarCamionRequest req) {
        if (req == null || req.camionId == null) return ResponseEntity.badRequest().build();
        try {
            // El servicio debe validar peso/volumen antes de la asignación (Req --.)
            Tramo actualizado = service.asignarCamionATramo(id, req.camionId); 
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException ex) {
            // Devolver 404 si el tramo no existe o 400 si falla la validación de capacidad
            return ResponseEntity.notFound().build(); 
        }
    }

    @PostMapping("/rutas/preview")
    @Operation(
        summary = "Pre visualizar ruta",
        description = "Calcula un tramo de preview con distancia estimada",
        responses = @ApiResponse(responseCode = "200", description = "Tramo de preview", content = @Content(schema = @Schema(implementation = Tramo.class)))
    )
    public ResponseEntity<Tramo> previewRuta(@RequestBody PreviewRequest req) {
        if (req == null || req.distanciaKm == null) return ResponseEntity.badRequest().build();
        Tramo preview = service.previewRuta(req.solicitudId, req.origen, req.destino, req.distanciaKm);
        return ResponseEntity.ok(preview);
    }

    @PostMapping("/rutas/asignar")
    @Operation(
        summary = "Asignar ruta",
        description = "Crea el tramo real de la solicitud",
        responses = @ApiResponse(responseCode = "201", description = "Tramo creado", content = @Content(schema = @Schema(implementation = Tramo.class)))
    )
    public ResponseEntity<Tramo> asignarRuta(@RequestBody Tramo tramo) {
        Tramo creado = service.asignarRuta(tramo);
        return ResponseEntity.status(201).body(creado);
    }

    @PatchMapping("/tramos/{id}/estado")
    @Operation(
        summary = "Actualizar estado del tramo",
        description = "Actualiza el estado de un tramo",
        responses = {
            @ApiResponse(responseCode = "200", description = "Tramo actualizado", content = @Content(schema = @Schema(implementation = Tramo.class))),
            @ApiResponse(responseCode = "400", description = "Estado inválido"),
            @ApiResponse(responseCode = "404", description = "Tramo no encontrado")
        }
    )
    public ResponseEntity<Tramo> actualizarEstado(@PathVariable Long id, @RequestBody String estado) {
        try {
            EstadoTramo e = EstadoTramo.valueOf(estado.replace("\"","").toUpperCase());
            Tramo actualizado = service.actualizarEstado(id, e);
            return ResponseEntity.ok(actualizado);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/tramos/asignados")
    @Operation(
        summary = "Listar tramos asignados a transportista",
        responses = @ApiResponse(responseCode = "200", description = "Listado de tramos", content = @Content(schema = @Schema(implementation = Tramo.class)))
    )
    public ResponseEntity<List<Tramo>> tramosAsignados(@RequestParam Long transportistaId) {
        return ResponseEntity.ok(service.listarAsignados(transportistaId));
    }
}
