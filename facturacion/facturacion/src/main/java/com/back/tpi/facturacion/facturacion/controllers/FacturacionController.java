package com.back.tpi.facturacion.facturacion.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.back.tpi.facturacion.facturacion.entity.Factura;
import com.back.tpi.facturacion.facturacion.service.FacturaService;

@RestController
@RequestMapping("/api")
public class FacturacionController {

    private final FacturaService service;

    public FacturacionController(FacturaService service) { this.service = service; }

    @GetMapping("/tarifas/estimacion")
    public ResponseEntity<Double> estimacion(@RequestParam double distanciaKm, @RequestParam double pesoKg) {
        double est = service.estimarTarifa(distanciaKm, pesoKg);
        return ResponseEntity.ok(est);
    }

    @PostMapping("/facturas")
    public ResponseEntity<Factura> crearFactura(@RequestBody Factura factura) {
        Factura creado = service.crearFactura(factura);
        return ResponseEntity.status(201).body(creado);
    }

    @PatchMapping("/facturas/{id}/pago")
    public ResponseEntity<Factura> pagoFactura(@PathVariable Long id, @RequestBody Boolean pagado) {
        try {
            Factura f = service.marcarPago(id, pagado != null ? pagado : true);
            return ResponseEntity.ok(f);
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}