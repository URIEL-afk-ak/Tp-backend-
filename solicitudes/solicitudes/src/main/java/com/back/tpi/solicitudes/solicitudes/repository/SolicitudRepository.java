// Paquete: com.back.tpi.solicitudes.solicitudes.repository
// Archivo: SolicitudRepository.java

package com.back.tpi.solicitudes.solicitudes.repository;

import com.back.tpi.solicitudes.solicitudes.entity.Solicitud; // Asumo que existe
import com.back.tpi.solicitudes.solicitudes.entity.EstadoSolicitud; // Asumo que existe
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {
    List<Solicitud> findByEstado(EstadoSolicitud estado);
}