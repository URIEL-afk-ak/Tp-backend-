package com.back.tpi.rutas_tramos.rutas_tramos.dto;

import java.time.LocalDateTime;

import com.back.tpi.rutas_tramos.rutas_tramos.entity.EstadoTramo;
import com.back.tpi.rutas_tramos.rutas_tramos.entity.Tramo;

public class TramoDTO {
    public Long id;
    public Long solicitudId;
    public Long transportistaId;
    public String origen;
    public String destino;
    public EstadoTramo estado;
    public Double costoEstimado;
    public Long duracionMinutos;
    public LocalDateTime inicioEstimado;
    public LocalDateTime finEstimado;

    public Tramo toEntity() {
        Tramo t = new Tramo();
        t.setId(this.id);
        t.setSolicitudId(this.solicitudId);
        t.setTransportistaId(this.transportistaId);
        t.setOrigen(this.origen);
        t.setDestino(this.destino);
        if (this.estado != null) t.setEstado(this.estado);
        t.setCostoEstimado(this.costoEstimado);
        t.setDuracionMinutos(this.duracionMinutos);
        t.setInicioEstimado(this.inicioEstimado);
        t.setFinEstimado(this.finEstimado);
        return t;
    }

    public static TramoDTO fromEntity(Tramo t) {
        if (t == null) return null;
        TramoDTO d = new TramoDTO();
        d.id = t.getId();
        d.solicitudId = t.getSolicitudId();
        d.transportistaId = t.getTransportistaId();
        d.origen = t.getOrigen();
        d.destino = t.getDestino();
        d.estado = t.getEstado();
        d.costoEstimado = t.getCostoEstimado();
        d.duracionMinutos = t.getDuracionMinutos();
        d.inicioEstimado = t.getInicioEstimado();
        d.finEstimado = t.getFinEstimado();
        return d;
    }
}