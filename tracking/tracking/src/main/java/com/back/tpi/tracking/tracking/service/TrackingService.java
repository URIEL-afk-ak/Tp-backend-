package com.back.tpi.tracking.tracking.service;

import com.back.tpi.tracking.tracking.entity.TrackingEvento;
import com.back.tpi.tracking.tracking.repository.TrackingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TrackingService {
    private final TrackingRepository repo;

    public TrackingService(TrackingRepository repo) { this.repo = repo; }

    public TrackingEvento registrarEvento(TrackingEvento e) { return repo.save(e); }

    @Transactional(readOnly = true)
    public List<TrackingEvento> listarEventos(Long contenedorId) { return repo.findByContenedorIdOrderByCreatedAtDesc(contenedorId); }
}