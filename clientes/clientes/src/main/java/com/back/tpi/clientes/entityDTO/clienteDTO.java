package com.back.tpi.clientes.entityDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class clienteDTO {
    
    private Long id;
    private String nombre_completo;
    private String email;
    private String telefono;
    private LocalDateTime fecha_registro;
}
