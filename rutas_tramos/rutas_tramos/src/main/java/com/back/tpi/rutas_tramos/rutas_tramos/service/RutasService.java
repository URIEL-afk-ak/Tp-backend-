package com.back.tpi.rutas_tramos.rutas_tramos.service;

import com.back.tpi.rutas_tramos.rutas_tramos.entity.EstadoTramo;
import com.back.tpi.rutas_tramos.rutas_tramos.entity.Tramo;
import com.back.tpi.rutas_tramos.rutas_tramos.repository.TramoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@Transactional
public class RutasService {

    private final TramoRepository repo;
    private final Random rnd = new Random();

    public RutasService(TramoRepository repo) { this.repo = repo; }

    public Tramo previewRuta(Long solicitudId, String origen, String destino, Double distanciaKm) {
        Tramo t = new Tramo();
        t.setSolicitudId(solicitudId);
        t.setOrigen(origen);
        t.setDestino(destino);
        long dur = Math.max(30, Math.round(distanciaKm * 2)); // minutos estimados simple
        double costo = Math.round(distanciaKm * 50 + rnd.nextDouble() * 200); // costo estimado simple
        t.setDuracionMinutos(dur);
        t.setCostoEstimado(costo);
        t.setInicioEstimado(LocalDateTime.now().plusMinutes(60)); // ejemplo
        t.setFinEstimado(t.getInicioEstimado().plusMinutes(dur));
        return t;
    }

    public Tramo asignarRuta(Tramo tramo) {
        tramo.setEstado(EstadoTramo.PENDIENTE);
        return repo.save(tramo);
    }

    public Tramo actualizarEstado(Long id, EstadoTramo nuevo) {
        Tramo t = repo.findById(id).orElseThrow(() -> new RuntimeException("Tramo no encontrado: " + id));
        t.setEstado(nuevo);
        return repo.save(t);
    }

    @Transactional(readOnly = true)
    public List<Tramo> listarAsignados(Long transportistaId) {
        return repo.findByTransportistaId(transportistaId);
    }
}