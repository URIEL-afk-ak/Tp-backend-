package com.back.tpi.depositos.depositos.entityDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepositoDTO {
    
    private Long id;
    private String nombre;
    private String direccion;
    private String ubicacionGeografica;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
}