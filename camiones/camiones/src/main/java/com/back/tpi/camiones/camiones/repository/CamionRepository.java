package com.back.tpi.camiones.camiones.repository;

import com.back.tpi.camiones.camiones.entity.Camion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CamionRepository extends JpaRepository<Camion, Long> {
    
    Optional<Camion> findByPatente(String patente);
    
    boolean existsByPatente(String patente);
    
    List<Camion> findByTransportistaId(Long transportistaId);
    
    List<Camion> findByDisponible(Boolean disponible);
    
    @Query("SELECT c FROM Camion c WHERE c.disponible = true")
    List<Camion> findCamionesDisponibles();
}