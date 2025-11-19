package com.back.tpi.rutas_tramos.rutas_tramos.dto;

import java.time.LocalDateTime;

import com.back.tpi.rutas_tramos.rutas_tramos.entity.EstadoTramo;
import com.back.tpi.rutas_tramos.rutas_tramos.entity.Tramo;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Datos de un tramo asignado")
public class TramoDTO {
    @Schema(description = "Identificador del tramo", example = "5", accessMode = Schema.AccessMode.READ_ONLY)
    public Long id;
    @Schema(description = "Solicitud asociada", example = "12", requiredMode = Schema.RequiredMode.REQUIRED)
    public Long solicitudId;
    @Schema(description = "Transportista asignado", example = "4")
    public Long transportistaId;
    @Schema(description = "Origen del tramo", example = "Buenos Aires")
    public String origen;
    @Schema(description = "Destino del tramo", example = "Rosario")
    public String destino;
    @Schema(description = "Estado del tramo", example = "ASIGNADO")
    public EstadoTramo estado;
    @Schema(description = "Costo estimado", example = "12000.0")
    public Double costoEstimado;
    @Schema(description = "Duracion estimada en minutos", example = "180")
    public Long duracionMinutos;
    @Schema(description = "Inicio estimado", example = "2024-03-10T09:00:00")
    public LocalDateTime inicioEstimado;
    @Schema(description = "Fin estimado", example = "2024-03-10T12:00:00")
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
