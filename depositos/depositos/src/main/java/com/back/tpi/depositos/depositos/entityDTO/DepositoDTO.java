package com.back.tpi.depositos.depositos.entityDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos básicos de un depósito")
public class DepositoDTO {
    
    @Schema(description = "Identificador del depósito", example = "2", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    @Schema(description = "Nombre del depósito", example = "Depósito Central")
    private String nombre;
    @Schema(description = "Dirección del depósito", example = "Av. Siempre Viva 742")
    private String direccion;
    @Schema(description = "Ubicación geográfica", example = "Buenos Aires")
    private String ubicacionGeografica;
    @Schema(description = "Fecha de creación", example = "2024-02-10T10:00:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime fechaCreacion;
    @Schema(description = "Fecha de última actualización", example = "2024-02-15T12:30:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime fechaActualizacion;
}
