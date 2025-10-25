package com.back.tpi.solicitudes.solicitudes.dto;

import java.time.LocalDateTime;

import com.back.tpi.solicitudes.solicitudes.entity.EstadoSolicitud;
import com.back.tpi.solicitudes.solicitudes.entity.Solicitud;

public class SolicitudDTO {

    private Long id;
    private Long clienteId;
    private String origen;
    private String destino;
    private Double pesoKg;
    private Double volumenM3;
    private EstadoSolicitud estado;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public SolicitudDTO() {}

    public SolicitudDTO(Long id, Long clienteId, String origen, String destino,
                        Double pesoKg, Double volumenM3, EstadoSolicitud estado,
                        LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id; this.clienteId = clienteId; this.origen = origen; this.destino = destino;
        this.pesoKg = pesoKg; this.volumenM3 = volumenM3; this.estado = estado;
        this.createdAt = createdAt; this.updatedAt = updatedAt;
    }

    public static SolicitudDTO fromEntity(Solicitud s) {
        if (s == null) return null;
        return new SolicitudDTO(
            s.getId(),
            s.getClienteId(),
            s.getOrigen(),
            s.getDestino(),
            s.getPesoKg(),
            s.getVolumenM3(),
            s.getEstado(),
            s.getCreatedAt(),
            s.getUpdatedAt()
        );
    }

    public Solicitud toEntity() {
        Solicitud s = new Solicitud();
        s.setId(this.id);
        s.setClienteId(this.clienteId);
        s.setOrigen(this.origen);
        s.setDestino(this.destino);
        s.setPesoKg(this.pesoKg);
        s.setVolumenM3(this.volumenM3);
        if (this.estado != null) s.setEstado(this.estado);
        return s;
    }

    // Getters / Setters (generate in IDE)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public String getOrigen() { return origen; }
    public void setOrigen(String origen) { this.origen = origen; }
    public String getDestino() { return destino; }
    public void setDestino(String destino) { this.destino = destino; }
    public Double getPesoKg() { return pesoKg; }
    public void setPesoKg(Double pesoKg) { this.pesoKg = pesoKg; }
    public Double getVolumenM3() { return volumenM3; }
    public void setVolumenM3(Double volumenM3) { this.volumenM3 = volumenM3; }
    public EstadoSolicitud getEstado() { return estado; }
    public void setEstado(EstadoSolicitud estado) { this.estado = estado; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}