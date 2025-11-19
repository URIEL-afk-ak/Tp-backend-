package com.back.tpi.camiones.camiones.entityDTO;

import com.back.tpi.camiones.camiones.entity.Camion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos de un camión")
public class CamionDTO {
    
    @Schema(description = "Identificador del camión", example = "10", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    @Schema(description = "Identificador del transportista asignado", example = "4")
    private Long transportistaId;
    @Schema(description = "Patente del camión", example = "ABC123")
    private String patente;
    @Schema(description = "Capacidad de peso en kilogramos", example = "15000")
    private Double capacidadPesoKg;
    @Schema(description = "Capacidad de volumen en m3", example = "60")
    private Double capacidadVolumenM3;
    @Schema(description = "Costo base por kilometro", example = "120.5")
    private Double costoBaseKm;
    @Schema(description = "Disponible para asignación", example = "true")
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
