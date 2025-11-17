// Paquete: com.back.tpi.rutas_tramos.rutas_tramos.repository
// Archivo: TramoRepository.java

package com.back.tpi.rutas_tramos.rutas_tramos.repository;

import com.back.tpi.rutas_tramos.rutas_tramos.entity.EstadoTramo;
import com.back.tpi.rutas_tramos.rutas_tramos.entity.Tramo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TramoRepository extends JpaRepository<Tramo, Long> {
    List<Tramo> findByTransportistaId(Long transportistaId);
    List<Tramo> findByEstado(EstadoTramo estado);
    
    /**
     * Busca todos los tramos asociados a una solicitud (Req. 7)
     */
    List<Tramo> findBySolicitudId(Long solicitudId);
}