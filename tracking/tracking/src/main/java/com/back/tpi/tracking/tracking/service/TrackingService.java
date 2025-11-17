// Paquete: com.back.tpi.tracking.tracking.service
// Archivo: TrackingService.java

package com.back.tpi.tracking.tracking.service;

import com.back.tpi.tracking.tracking.entity.TrackingEvento;
import com.back.tpi.tracking.tracking.repository.TrackingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class TrackingService {
    private final TrackingRepository repo;

    /**
     * Registra un nuevo evento de tracking.
     */
    public TrackingEvento registrarEvento(TrackingEvento e) { 
        if (e.getCreatedAt() == null) {
            e.setCreatedAt(LocalDateTime.now());
        }
        // Se podría agregar validación para asegurar que contenedorId exista.
        return repo.save(e); 
    }

    /**
     * Lista todos los eventos de tracking para un contenedor, ordenados por fecha descendente.
     */
    @Transactional(readOnly = true)
    public List<TrackingEvento> listarEventos(Long contenedorId) { 
        return repo.findByContenedorIdOrderByCreatedAtDesc(contenedorId); 
    }
}