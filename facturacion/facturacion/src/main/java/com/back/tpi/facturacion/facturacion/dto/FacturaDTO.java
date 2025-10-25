package com.back.tpi.facturacion.facturacion.dto;

import java.time.LocalDateTime;

import com.back.tpi.facturacion.facturacion.entity.Factura;

public class FacturaDTO {
    public Long id;
    public Long solicitudId;
    public Double monto;
    public Boolean pagado;
    public LocalDateTime createdAt;

    public Factura toEntity() {
        Factura f = new Factura();
        f.setId(this.id);
        f.setSolicitudId(this.solicitudId);
        f.setMonto(this.monto);
        f.setPagado(this.pagado != null ? this.pagado : false);
        f.setCreatedAt(this.createdAt != null ? this.createdAt : LocalDateTime.now());
        return f;
    }

    public static FacturaDTO fromEntity(Factura f) {
        if (f == null) return null;
        FacturaDTO d = new FacturaDTO();
        d.id = f.getId();
        d.solicitudId = f.getSolicitudId();
        d.monto = f.getMonto();
        d.pagado = f.getPagado();
        d.createdAt = f.getCreatedAt();
        return d;
    }
}