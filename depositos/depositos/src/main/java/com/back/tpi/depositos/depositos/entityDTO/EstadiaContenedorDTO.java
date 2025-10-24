package com.back.tpi.depositos.depositos.entityDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadiaContenedorDTO {
    
    private Long id;
    private Long contenedorId;
    private Long depositoId;
    private LocalDateTime fechaEntrada;
    private LocalDateTime fechaSalida;
    private Integer diasEstadia;
    private Double costoCalculado;
    private LocalDateTime fechaCreacion;
}