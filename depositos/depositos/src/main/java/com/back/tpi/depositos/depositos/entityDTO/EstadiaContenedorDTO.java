package com.back.tpi.depositos.depositos.entityDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos de la estadía de un contenedor en un depósito")
public class EstadiaContenedorDTO {
    
    @Schema(description = "Identificador de la estadía", example = "7", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    @Schema(description = "ID del contenedor", example = "12", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long contenedorId;
    @Schema(description = "ID del depósito", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long depositoId;
    @Schema(description = "Fecha de entrada del contenedor", example = "2024-02-10T08:00:00")
    private LocalDateTime fechaEntrada;
    @Schema(description = "Fecha de salida calculada", example = "2024-02-12T17:00:00")
    private LocalDateTime fechaSalida;
    @Schema(description = "Total de días de estadía", example = "3")
    private Integer diasEstadia;
    @Schema(description = "Costo calculado de la estadía", example = "4500.00")
    private Double costoCalculado;
    @Schema(description = "Fecha de creación del registro", example = "2024-02-10T08:05:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime fechaCreacion;
}
