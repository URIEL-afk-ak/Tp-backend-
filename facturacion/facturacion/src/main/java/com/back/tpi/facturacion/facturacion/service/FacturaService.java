package com.back.tpi.facturacion.facturacion.service;

import com.back.tpi.facturacion.facturacion.entity.Factura;
import com.back.tpi.facturacion.facturacion.repository.FacturaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FacturaService {
    private final FacturaRepository repo;

    public FacturaService(FacturaRepository repo) { this.repo = repo; }

    public double estimarTarifa(double distanciaKm, double pesoKg) {
        double base = 1000; // ejemplo
        double porKm = distanciaKm * 50;
        double porPeso = Math.min(5000, pesoKg * 0.1);
        return Math.round((base + porKm + porPeso) * 100.0) / 100.0;
    }

    public Factura crearFactura(Factura f) {
        return repo.save(f);
    }

    public Factura marcarPago(Long id, boolean pagado) {
        Factura f = repo.findById(id).orElseThrow(() -> new RuntimeException("Factura no encontrada: " + id));
        f.setPagado(pagado);
        return repo.save(f);
    }
}