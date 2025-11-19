package com.back.tpi.facturacion.facturacion.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "facturas")
@Schema(description = "Factura generada a partir de una solicitud")
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador de la factura", example = "20", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Column(name = "solicitud_id")
    @Schema(description = "ID de la solicitud asociada", example = "15")
    private Long solicitudId;

    @Schema(description = "Monto facturado", example = "15000.50")
    private Double monto;
    @Schema(description = "Indicador de pago", example = "false")
    private Boolean pagado = false;
    @Schema(description = "Fecha de creación", example = "2024-04-20T12:00:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Schema(description = "Costo total calculado", example = "18000.00")
    private Double costoTotal; 
    @Schema(description = "Distancia total recorrida", example = "320.5")
    private Double distanciaTotalKm; 

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getSolicitudId() { return solicitudId; }
    public void setSolicitudId(Long solicitudId) { this.solicitudId = solicitudId; }
    public Double getMonto() { return monto; }
    public void setMonto(Double monto) { this.monto = monto; }
    public Boolean getPagado() { return pagado; }
    public void setPagado(Boolean pagado) { this.pagado = pagado; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public Double getCostoTotal() { return costoTotal; }
    public void setCostoTotal(Double costoTotal) { this.costoTotal = costoTotal; }
    public Double getDistanciaTotalKm() { return distanciaTotalKm; }
    public void setDistanciaTotalKm(Double distanciaTotalKm) { this.distanciaTotalKm = distanciaTotalKm; }
}
