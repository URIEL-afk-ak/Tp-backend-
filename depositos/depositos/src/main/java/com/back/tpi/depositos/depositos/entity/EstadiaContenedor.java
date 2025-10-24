package com.back.tpi.depositos.depositos.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "estadia_contenedores")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadiaContenedor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "contenedor_id", nullable = false)
    private Long contenedorId;
    
    @Column(name = "deposito_id", nullable = false)
    private Long depositoId;
    
    @Column(name = "fecha_entrada", nullable = false)
    private LocalDateTime fechaEntrada;
    
    @Column(name = "fecha_salida")
    private LocalDateTime fechaSalida;
    
    @Column(name = "dias_estadia")
    private Integer diasEstadia;
    
    @Column(name = "costo_calculado")
    private Double costoCalculado;
    
    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;
    
    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        if (fechaEntrada == null) {
            fechaEntrada = LocalDateTime.now();
        }
    }
}