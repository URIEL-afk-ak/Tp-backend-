package com.back.tpi.camiones.camiones.entityDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransportistaDTO {
    
    private Long id;
    private String nombre;
    private String codigo;
    private String contacto;
    private String telefono;
    private String email;
    private Boolean activo;
}