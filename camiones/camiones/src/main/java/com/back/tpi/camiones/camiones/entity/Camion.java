package com.back.tpi.camiones.camiones.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "camiones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Camion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "transportista_id", nullable = false)
    private Long transportistaId;
    
    @Column(nullable = false, unique = true)
    private String patente;
    
    @Column(name = "capacidad_peso_kg", nullable = false)
    private Double capacidadPesoKg;
    
    @Column(name = "capacidad_volumen_m3", nullable = false)
    private Double capacidadVolumenM3;
    
    @Column(name = "costo_base_km", nullable = false)
    private Double costoBaseKm;
    
    @Column(nullable = false)
    private Boolean disponible;
    
    @PrePersist
    protected void onCreate() {
        if (disponible == null) {
            disponible = true;
        }
    }
}