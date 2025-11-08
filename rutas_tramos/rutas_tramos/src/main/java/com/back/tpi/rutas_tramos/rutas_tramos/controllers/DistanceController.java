package com.back.tpi.rutas_tramos.rutas_tramos.controllers;

import com.back.tpi.rutas_tramos.rutas_tramos.dto.DistanceResponse;
import com.back.tpi.rutas_tramos.rutas_tramos.service.GoogleDistanceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DistanceController {

    private final GoogleDistanceService googleDistanceService;

    public DistanceController(GoogleDistanceService googleDistanceService) {
        this.googleDistanceService = googleDistanceService;
    }

    @GetMapping("/distancia")
    public DistanceResponse getDistance(
            @RequestParam String origen,
            @RequestParam String destino) {
        return googleDistanceService.getDistance(origen, destino);
    }
}
