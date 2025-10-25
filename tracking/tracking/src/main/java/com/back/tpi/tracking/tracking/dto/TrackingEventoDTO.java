package com.back.tpi.tracking.tracking.dto;

import java.time.LocalDateTime;

import com.back.tpi.tracking.tracking.entity.TrackingEvento;

public class TrackingEventoDTO {
    public Long id;
    public Long contenedorId;
    public Long transportistaId;
    public String tipo;
    public String descripcion;
    public Double lat;
    public Double lon;
    public LocalDateTime createdAt;

    public TrackingEvento toEntity() {
        TrackingEvento e = new TrackingEvento();
        e.setId(this.id);
        e.setContenedorId(this.contenedorId);
        e.setTransportistaId(this.transportistaId);
        e.setTipo(this.tipo);
        e.setDescripcion(this.descripcion);
        e.setLat(this.lat);
        e.setLon(this.lon);
        e.setCreatedAt(this.createdAt != null ? this.createdAt : LocalDateTime.now());
        return e;
    }

    public static TrackingEventoDTO fromEntity(TrackingEvento e) {
        if (e == null) return null;
        TrackingEventoDTO d = new TrackingEventoDTO();
        d.id = e.getId();
        d.contenedorId = e.getContenedorId();
        d.transportistaId = e.getTransportistaId();
        d.tipo = e.getTipo();
        d.descripcion = e.getDescripcion();
        d.lat = e.getLat();
        d.lon = e.getLon();
        d.createdAt = e.getCreatedAt();
        return d;
    }
}