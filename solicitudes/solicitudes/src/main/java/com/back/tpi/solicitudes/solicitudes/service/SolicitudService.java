package com.back.tpi.solicitudes.solicitudes.service;

import com.back.tpi.solicitudes.solicitudes.dto.SolicitudDTO;
import com.back.tpi.solicitudes.solicitudes.entity.EstadoSolicitud;
import com.back.tpi.solicitudes.solicitudes.entity.Solicitud;
import com.back.tpi.solicitudes.solicitudes.repository.SolicitudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SolicitudService {

    private final SolicitudRepository repo;

    public SolicitudService(SolicitudRepository repo) {
        this.repo = repo;
    }

    public SolicitudDTO crearSolicitud(SolicitudDTO dto) {
        Solicitud s = dto.toEntity();
        if (s.getEstado() == null) s.setEstado(EstadoSolicitud.PENDIENTE);
        Solicitud saved = repo.save(s);
        return SolicitudDTO.fromEntity(saved);
    }

    @Transactional(readOnly = true)
    public SolicitudDTO obtenerPorId(Long id) {
        return repo.findById(id).map(SolicitudDTO::fromEntity).orElse(null);
    }

    public SolicitudDTO actualizarEstado(Long id, EstadoSolicitud nuevoEstado) {
        Solicitud s = repo.findById(id).orElseThrow(() -> new RuntimeException("Solicitud no encontrada: " + id));
        s.setEstado(nuevoEstado);
        Solicitud saved = repo.save(s);
        return SolicitudDTO.fromEntity(saved);
    }

    @Transactional(readOnly = true)
    public List<SolicitudDTO> listarPorEstado(EstadoSolicitud estado) {
        return repo.findByEstado(estado).stream().map(SolicitudDTO::fromEntity).collect(Collectors.toList());
    }
}