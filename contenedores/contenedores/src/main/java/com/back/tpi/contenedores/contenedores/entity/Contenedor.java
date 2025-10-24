package com.back.tpi.contenedores.contenedores.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "contenedores")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contenedor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "cliente_id", nullable = false)
    private Long clienteId;  // Cambiado a camelCase
    
    @Column(name = "numero_identificacion", nullable = false, unique = true)
    private String numeroIdentificacion;  // Cambiado a camelCase
    
    @Column(nullable = false)
    private String tipo;
    
    @Column(nullable = false)
    private Double capacidad;
    
    @Column(nullable = false)
    private String ubicacion;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoContenedor estado;
    
    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;  // Cambiado a camelCase
    
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;  // Cambiado a camelCase
    
    @Column(name = "fecha_entrega")
    private LocalDateTime fechaEntrega;  // Cambiado a camelCase
    
    @Column(length = 500)
    private String observaciones;
    
    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        fechaActualizacion = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }
}