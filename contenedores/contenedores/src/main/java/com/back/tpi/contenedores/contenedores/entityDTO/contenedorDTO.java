package com.back.tpi.contenedores.contenedores.entityDTO;

import com.back.tpi.contenedores.contenedores.entity.EstadoContenedor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos de un contenedor")
public class contenedorDTO {
    
    @Schema(description = "Identificador del contenedor", example = "15", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    @Schema(description = "ID del cliente propietario", example = "3", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long clienteId;  // Cambiado a camelCase
    @Schema(description = "Numero de identificación único", example = "CONT-0001", requiredMode = Schema.RequiredMode.REQUIRED)
    private String numeroIdentificacion;  // Cambiado a camelCase
    @Schema(description = "Tipo de contenedor", example = "Reefer")
    private String tipo;
    @Schema(description = "Capacidad en pies cúbicos o m3", example = "33.1")
    private Double capacidad;
    @Schema(description = "Ubicación actual", example = "Puerto de Buenos Aires")
    private String ubicacion;
    @Schema(description = "Estado logístico", example = "EN_TRANSITO")
    private EstadoContenedor estado;
    @Schema(description = "Fecha de creación", example = "2024-01-10T11:00:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime fechaCreacion;  // Cambiado a camelCase
    @Schema(description = "Fecha de última actualización", example = "2024-01-12T09:30:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime fechaActualizacion;  // Cambiado a camelCase
    @Schema(description = "Fecha estimada de entrega", example = "2024-01-20T15:00:00")
    private LocalDateTime fechaEntrega;  // Cambiado a camelCase
    @Schema(description = "Observaciones adicionales", example = "Requiere enchufe a 220v")
    private String observaciones;
}
