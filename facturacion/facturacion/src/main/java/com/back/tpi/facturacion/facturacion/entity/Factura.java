package com.back.tpi.facturacion.facturacion.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "facturas")
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "solicitud_id")
    private Long solicitudId;

    private Double monto;
    private Boolean pagado = false;
    private LocalDateTime createdAt = LocalDateTime.now();

    // 
    // NUEVOS CAMPOS AGREGADOS PARA EL CÁLCULO DE COSTOS
    //
    private Double costoTotal; 
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
    
    //
    // NUEVOS GETTERS Y SETTERS
    //
    public Double getCostoTotal() { return costoTotal; }
    public void setCostoTotal(Double costoTotal) { this.costoTotal = costoTotal; }
    public Double getDistanciaTotalKm() { return distanciaTotalKm; }
    public void setDistanciaTotalKm(Double distanciaTotalKm) { this.distanciaTotalKm = distanciaTotalKm; }
}