package com.back.tpi.tracking.tracking.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "tracking_eventos")
@Schema(description = "Evento de tracking generado para un contenedor")
public class TrackingEvento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador del evento", example = "12", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Column(name = "contenedor_id", nullable = false)
    @Schema(description = "Identificador del contenedor", example = "101", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long contenedorId;

    @Column(name = "transportista_id")
    @Schema(description = "Identificador del transportista que reporta el evento", example = "55")
    private Long transportistaId;

    @Schema(description = "Tipo de evento", example = "LLEGADA")
    private String tipo;
    @Schema(description = "Descripcion del evento", example = "El contenedor llegó al depósito")
    private String descripcion;
    @Schema(description = "Latitud asociada al evento", example = "-34.6037")
    private Double lat;
    @Schema(description = "Longitud asociada al evento", example = "-58.3816")
    private Double lon;
    @Schema(description = "Fecha de creación del evento", example = "2024-05-10T14:30:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt = LocalDateTime.now();

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getContenedorId() { return contenedorId; }
    public void setContenedorId(Long contenedorId) { this.contenedorId = contenedorId; }
    public Long getTransportistaId() { return transportistaId; }
    public void setTransportistaId(Long transportistaId) { this.transportistaId = transportistaId; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Double getLat() { return lat; }
    public void setLat(Double lat) { this.lat = lat; }
    public Double getLon() { return lon; }
    public void setLon(Double lon) { this.lon = lon; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
