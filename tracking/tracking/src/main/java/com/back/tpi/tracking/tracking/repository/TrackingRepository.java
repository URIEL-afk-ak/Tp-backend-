// Paquete: com.back.tpi.tracking.tracking.repository
// Archivo: TrackingRepository.java

package com.back.tpi.tracking.tracking.repository;

import com.back.tpi.tracking.tracking.entity.TrackingEvento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrackingRepository extends JpaRepository<TrackingEvento, Long> {
    /**
     * Busca todos los eventos para un contenedor específico, ordenados del más reciente al más antiguo.
     */
    List<TrackingEvento> findByContenedorIdOrderByCreatedAtDesc(Long contenedorId);
}