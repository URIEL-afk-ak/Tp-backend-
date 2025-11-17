package com.back.tpi.rutas_tramos.rutas_tramos.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tramos")
public class Tramo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "solicitud_id", nullable = false)
    private Long solicitudId;

    @Column(name = "transportista_id")
    private Long transportistaId;
    
    // ↓↓↓ CAMPOS AGREGADOS PARA LA ASIGNACIÓN Y REGISTRO REAL ↓↓↓
    @Column(name = "camion_id")
    private Long camionId; 
    
    private Double distanciaKmEstimada; 
    private Double distanciaKmReal;     
    private Double costoReal;           
    // ↑↑↑ FIN CAMPOS AGREGADOS ↑↑↑

    @Column(nullable = false)
    private String origen;

    @Column(nullable = false)
    private String destino;

    @Enumerated(EnumType.STRING)
    private EstadoTramo estado = EstadoTramo.PENDIENTE;

    private Double costoEstimado;
    private Long duracionMinutos;

    private LocalDateTime inicioEstimado;
    private LocalDateTime finEstimado;

    // --- Getters y Setters ---

    public Long getCamionId() { return camionId; }
    public void setCamionId(Long camionId) { this.camionId = camionId; }
    
    public Double getDistanciaKmEstimada() { return distanciaKmEstimada; }
    public void setDistanciaKmEstimada(Double distanciaKmEstimada) { this.distanciaKmEstimada = distanciaKmEstimada; }
    
    public Double getDistanciaKmReal() { return distanciaKmReal; }
    public void setDistanciaKmReal(Double distanciaKmReal) { this.distanciaKmReal = distanciaKmReal; }
    
    public Double getCostoReal() { return costoReal; }
    public void setCostoReal(Double costoReal) { this.costoReal = costoReal; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getSolicitudId() { return solicitudId; }
    public void setSolicitudId(Long solicitudId) { this.solicitudId = solicitudId; }
    public Long getTransportistaId() { return transportistaId; }
    public void setTransportistaId(Long transportistaId) { this.transportistaId = transportistaId; }
    public String getOrigen() { return origen; }
    public void setOrigen(String origen) { this.origen = origen; }
    public String getDestino() { return destino; }
    public void setDestino(String destino) { this.destino = destino; }
    public EstadoTramo getEstado() { return estado; }
    public void setEstado(EstadoTramo estado) { this.estado = estado; }
    public Double getCostoEstimado() { return costoEstimado; }
    public void setCostoEstimado(Double costoEstimado) { this.costoEstimado = costoEstimado; }
    public Long getDuracionMinutos() { return duracionMinutos; }
    public void setDuracionMinutos(Long duracionMinutos) { this.duracionMinutos = duracionMinutos; }
    public LocalDateTime getInicioEstimado() { return inicioEstimado; }
    public void setInicioEstimado(LocalDateTime inicioEstimado) { this.inicioEstimado = inicioEstimado; }
    public LocalDateTime getFinEstimado() { return finEstimado; }
    public void setFinEstimado(LocalDateTime finEstimado) { this.finEstimado = finEstimado; }
}