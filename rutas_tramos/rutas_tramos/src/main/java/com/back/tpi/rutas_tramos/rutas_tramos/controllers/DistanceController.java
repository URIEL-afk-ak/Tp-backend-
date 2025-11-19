package com.back.tpi.rutas_tramos.rutas_tramos.controllers;

import com.back.tpi.rutas_tramos.rutas_tramos.dto.DistanceResult;
import com.back.tpi.rutas_tramos.rutas_tramos.service.GoogleDistanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Distancias", description = "Consulta de distancias entre puntos")
public class DistanceController {

    private final GoogleDistanceService googleDistanceService;

    public DistanceController(GoogleDistanceService googleDistanceService) {
        this.googleDistanceService = googleDistanceService;
    }

    @GetMapping("/distancia")
    @Operation(
        summary = "Obtener distancia entre origen y destino",
        responses = @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Distancia calculada",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = DistanceResult.class))
        )
    )
    public DistanceResult getDistance(
            @RequestParam String origen,
            @RequestParam String destino
    ) {
        return googleDistanceService.getDistance(origen, destino);
    }
}
