package com.back.tpi.camiones.camiones.entityDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos básicos del transportista")
public class TransportistaDTO {
    
    @Schema(description = "Identificador del transportista", example = "3", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    @Schema(description = "Nombre completo", example = "Logistica Sur SRL")
    private String nombre;
    @Schema(description = "Código interno o legajo", example = "TR-1020")
    private String codigo;
    @Schema(description = "Persona de contacto", example = "Carlos Gomez")
    private String contacto;
    @Schema(description = "Telefono de contacto", example = "+549112223334")
    private String telefono;
    @Schema(description = "Correo electrónico", example = "contacto@logisticasur.com")
    private String email;
    @Schema(description = "Si el transportista está activo", example = "true")
    private Boolean activo;
}
