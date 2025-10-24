package com.back.tpi.contenedores.contenedores.entityDTO;

import com.back.tpi.contenedores.contenedores.entity.EstadoContenedor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class contenedorDTO {
    
    private Long id;
    private Long clienteId;  // Cambiado a camelCase
    private String numeroIdentificacion;  // Cambiado a camelCase
    private String tipo;
    private Double capacidad;
    private String ubicacion;
    private EstadoContenedor estado;
    private LocalDateTime fechaCreacion;  // Cambiado a camelCase
    private LocalDateTime fechaActualizacion;  // Cambiado a camelCase
    private LocalDateTime fechaEntrega;  // Cambiado a camelCase
    private String observaciones;
}