package com.back.tpi.clientes.entityDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Modelo de datos para crear o devolver un cliente")
public class clienteDTO {
    
    @Schema(description = "Identificador del cliente", example = "5", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    @Schema(description = "Nombre completo del cliente", example = "Ana Gomez", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombre_completo;
    @Schema(description = "Correo electronico", example = "ana@mail.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;
    @Schema(description = "Telefono de contacto", example = "+5491199988877", requiredMode = Schema.RequiredMode.REQUIRED)
    private String telefono;
    @Schema(description = "Fecha de registro", example = "2024-03-15T09:30:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime fecha_registro;
}
