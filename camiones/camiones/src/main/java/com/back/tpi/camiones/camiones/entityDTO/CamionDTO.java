package com.back.tpi.camiones.camiones.entityDTO;

import com.back.tpi.camiones.camiones.entity.Camion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CamionDTO {
    
    private Long id;
    private Long transportistaId;
    private String patente;
    private Double capacidadPesoKg;
    private Double capacidadVolumenM3;
    private Double costoBaseKm;
    private Boolean disponible;

    // Convierte este DTO a la entidad Camion (mapea transportistaId)
    public Camion toEntity() {
        Camion c = new Camion();
        c.setId(this.id);
        c.setTransportistaId(this.transportistaId);
        c.setPatente(this.patente);
        c.setCapacidadPesoKg(this.capacidadPesoKg);
        c.setCapacidadVolumenM3(this.capacidadVolumenM3);
        c.setCostoBaseKm(this.costoBaseKm);
        c.setDisponible(this.disponible);
        return c;
    }

    // Crea un DTO a partir de la entidad Camion (extrae transportistaId)
    public static CamionDTO fromEntity(Camion c) {
        if (c == null) return null;
        return new CamionDTO(
            c.getId(),
            c.getTransportistaId(),
            c.getPatente(),
            c.getCapacidadPesoKg(),
            c.getCapacidadVolumenM3(),
            c.getCostoBaseKm(),
            c.getDisponible()
        );
    }
}