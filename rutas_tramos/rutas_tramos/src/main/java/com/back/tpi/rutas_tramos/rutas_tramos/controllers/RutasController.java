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

import com.back.tpi.rutas_tramos.rutas_tramos.entity.EstadoTramo;
import com.back.tpi.rutas_tramos.rutas_tramos.entity.Tramo;
import com.back.tpi.rutas_tramos.rutas_tramos.service.RutasService;

@RestController
@RequestMapping("/api")
public class RutasController {

    private final RutasService service;

    public RutasController(RutasService service) { this.service = service; }

    public static class PreviewRequest { public Long solicitudId; public String origen; public String destino; public Double distanciaKm; }

    @PostMapping("/rutas/preview")
    public ResponseEntity<Tramo> previewRuta(@RequestBody PreviewRequest req) {
        if (req == null || req.distanciaKm == null) return ResponseEntity.badRequest().build();
        Tramo preview = service.previewRuta(req.solicitudId, req.origen, req.destino, req.distanciaKm);
        return ResponseEntity.ok(preview);
    }

    @PostMapping("/rutas/asignar")
    public ResponseEntity<Tramo> asignarRuta(@RequestBody Tramo tramo) {
        Tramo creado = service.asignarRuta(tramo);
        return ResponseEntity.status(201).body(creado);
    }

    @PatchMapping("/tramos/{id}/estado")
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
    public ResponseEntity<List<Tramo>> tramosAsignados(@RequestParam Long transportistaId) {
        return ResponseEntity.ok(service.listarAsignados(transportistaId));
    }
}