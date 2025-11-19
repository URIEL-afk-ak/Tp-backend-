package com.back.tpi.solicitudes.solicitudes.dto;

import java.time.LocalDateTime;

import com.back.tpi.solicitudes.solicitudes.entity.EstadoSolicitud;
import com.back.tpi.solicitudes.solicitudes.entity.Solicitud;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Datos de una solicitud de transporte")
public class SolicitudDTO {

    @Schema(description = "Identificador de la solicitud", example = "7", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    @Schema(description = "ID del cliente", example = "3", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long clienteId;
    @Schema(description = "Origen de la carga", example = "Cordoba")
    private String origen;
    @Schema(description = "Destino de la carga", example = "Mendoza")
    private String destino;
    @Schema(description = "Peso en kilogramos", example = "1200.5")
    private Double pesoKg;
    @Schema(description = "Volumen en m3", example = "15.0")
    private Double volumenM3;
    @Schema(description = "Estado de la solicitud", example = "CREADA")
    private EstadoSolicitud estado;
    @Schema(description = "Fecha de creación", example = "2024-02-01T10:00:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;
    @Schema(description = "Fecha de última actualización", example = "2024-02-02T12:00:00", accessMode = Schema.AccessMode.READ_ONLY)
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
