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
    private String codigo;
    private String tipo;
    private Double capacidad;
    private String ubicacion;
    private EstadoContenedor estado;
    private Long clienteId;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    private LocalDateTime fechaEntrega;
    private String observaciones;
}
