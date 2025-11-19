package com.back.tpi.facturacion.facturacion.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.back.tpi.facturacion.facturacion.entity.Factura;
import com.back.tpi.facturacion.facturacion.service.FacturaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api")
@Tag(name = "Facturacion", description = "Operaciones de tarifas y facturas")
public class FacturacionController {

    private final FacturaService service;

    public FacturacionController(FacturaService service) { this.service = service; }

    // Endpoints para Gestión de Tarifas (Asumiendo que existe un TarifaDTO/Entity)
    // Se utiliza 'Object' como placeholder ya que no se proporcionó la clase TarifaDTO.
    
    @PostMapping("/tarifas")
    @Operation(summary = "Crear tarifa", description = "Registra una nueva tarifa base")
    public ResponseEntity<?> crearTarifa(@RequestBody Object tarifaDTO) { 
        try {
            // Se debe implementar service.crearTarifa(tarifaDTO);
            Object creado = service.crearTarifa(tarifaDTO);
            return ResponseEntity.status(201).body(creado);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/tarifas/{id}")
    @Operation(summary = "Actualizar tarifa", description = "Actualiza una tarifa existente por id")
    public ResponseEntity<?> actualizarTarifa(@PathVariable Long id, @RequestBody Object tarifaDTO) {
        try {
            // Se debe implementar service.actualizarTarifa(id, tarifaDTO);
            Object actualizado = service.actualizarTarifa(id, tarifaDTO);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/tarifas/estimacion")
    @Operation(summary = "Estimar tarifa", description = "Calcula una estimación de tarifa por distancia y peso")
    public ResponseEntity<Double> estimacion(@RequestParam double distanciaKm, @RequestParam double pesoKg) {
        double est = service.estimarTarifa(distanciaKm, pesoKg);
        return ResponseEntity.ok(est);
    }
    
    // Nuevo: Calcular costo real final de la solicitud (Req 7)
    @GetMapping("/facturas/calcular/solicitud/{solicitudId}")
    @Operation(
        summary = "Calcular costo final por solicitud",
        description = "Calcula estadías, tramos y genera/actualiza la factura de una solicitud",
        responses = @ApiResponse(
            responseCode = "200",
            description = "Factura calculada",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Factura.class))
        )
    )
    public ResponseEntity<Factura> calcularCostoReal(@PathVariable Long solicitudId) {
        try {
            // El servicio debe orquestar el cálculo de estadias, tramos y crear/actualizar la factura.
            Factura facturaCalculada = service.calcularYRegistrarFacturaFinal(solicitudId); 
            return ResponseEntity.ok(facturaCalculada);
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/facturas")
    @Operation(
        summary = "Crear factura",
        responses = @ApiResponse(
            responseCode = "201",
            description = "Factura creada",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Factura.class))
        )
    )
    public ResponseEntity<Factura> crearFactura(@RequestBody Factura factura) {
        Factura creado = service.crearFactura(factura);
        return ResponseEntity.status(201).body(creado);
    }

    @PatchMapping("/facturas/{id}/pago")
    @Operation(summary = "Actualizar estado de pago de la factura")
    public ResponseEntity<Factura> pagoFactura(@PathVariable Long id, @RequestBody Boolean pagado) {
        try {
            Factura f = service.marcarPago(id, pagado != null ? pagado : true);
            return ResponseEntity.ok(f);
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
