package com.back.tpi.tracking.tracking.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.back.tpi.tracking.tracking.entity.TrackingEvento;
import com.back.tpi.tracking.tracking.service.TrackingService;

@RestController
@RequestMapping("/api/tracking")
public class TrackingController {

    private final TrackingService service;

    public TrackingController(TrackingService service) { this.service = service; }

    @GetMapping("/contenedores/{id}/eventos")
    public ResponseEntity<List<TrackingEvento>> eventos(@PathVariable Long id) {
        return ResponseEntity.ok(service.listarEventos(id));
    }

    @PostMapping("/eventos")
    public ResponseEntity<TrackingEvento> registrar(@RequestBody TrackingEvento evento) {
        TrackingEvento creado = service.registrarEvento(evento);
        return ResponseEntity.status(201).body(creado);
    }
}