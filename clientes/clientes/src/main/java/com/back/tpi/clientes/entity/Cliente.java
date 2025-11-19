package com.back.tpi.clientes.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Entity
@Table(name = "clientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entidad que representa un cliente")
public class Cliente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador del cliente", example = "5", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    
    @Column(nullable = false)
    @Schema(description = "Nombre completo del cliente", example = "Juan Perez")
    private String nombre_completo;
    
    @Column(nullable = false, unique = true)
    @Schema(description = "Correo electronico del cliente", example = "cliente@mail.com")
    private String email;
    
    @Column(nullable = false)
    @Schema(description = "Telefono de contacto", example = "+5491122334455")
    private String telefono;
    
    @Column(nullable = false)
    @Schema(description = "Fecha de registro", example = "2024-03-15T09:30:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime fecha_registro;
}
