// Paquete: com.back.tpi.solicitudes.solicitudes.service
// Archivo: SolicitudService.java

package com.back.tpi.solicitudes.solicitudes.service;

import com.back.tpi.solicitudes.solicitudes.dto.SolicitudDTO;
import com.back.tpi.solicitudes.solicitudes.entity.EstadoSolicitud;
import com.back.tpi.solicitudes.solicitudes.entity.Solicitud; // Asumo que existe
import com.back.tpi.solicitudes.solicitudes.repository.SolicitudRepository; // Asumo que existe
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SolicitudService {

    private final SolicitudRepository solicitudRepository;
    // Asumo que existe un SolicitudMapper para convertir a DTO (no implementado aquí)

    // MÉTODOS EXISTENTES (Simulados)
    public SolicitudDTO crearSolicitud(SolicitudDTO dto) { 
        // Lógica de creación (incluye creación de Contenedor y registro de estado BORRADOR)
        return dto; 
    }
    public SolicitudDTO obtenerPorId(Long id) { /* ... */ return new SolicitudDTO(); }
    
    public SolicitudDTO actualizarEstado(Long id, EstadoSolicitud nuevoEstado) { 
        Solicitud s = solicitudRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));
        s.setEstado(nuevoEstado);
        // return SolicitudMapper.toDTO(solicitudRepository.save(s)); 
        return new SolicitudDTO(); // Placeholder
    }
    
    @Transactional(readOnly = true)
    public List<SolicitudDTO> listarPorEstado(EstadoSolicitud estado) { 
        // return solicitudRepository.findByEstado(estado).stream().map(SolicitudMapper::toDTO).collect(Collectors.toList());
        return List.of(); // Placeholder
    }
    
    /**
     * Actualiza el estado a ENTREGADA y registra el tiempo y costo real. (Req. 8)
     */

    public SolicitudDTO finalizarSolicitud(Long id, Double tiempoRealHoras, Double costoRealTotal) {
        Solicitud solicitud = solicitudRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Solicitud no encontrada con ID: " + id));
        
        // Verificar que la solicitud esté en un estado que pueda ser finalizado
        if (solicitud.getEstado() != EstadoSolicitud.EN_CURSO && 
            solicitud.getEstado() != EstadoSolicitud.EN_TRANSITO) {
            throw new RuntimeException("Solo se pueden finalizar solicitudes EN_CURSO o EN_TRANSITO.");
        }

        // 1. Registrar valores reales
        solicitud.setTiempoRealHoras(tiempoRealHoras);
        solicitud.setCostoRealTotal(costoRealTotal);
        solicitud.setEstado(EstadoSolicitud.COMPLETADA); // Cambia el estado final

        Solicitud actualizada = solicitudRepository.save(solicitud);
        // Asumiendo un mapper: return SolicitudMapper.toDTO(actualizada);
        return new SolicitudDTO(); // Placeholder - reemplazar con tu implementación real
    }
}