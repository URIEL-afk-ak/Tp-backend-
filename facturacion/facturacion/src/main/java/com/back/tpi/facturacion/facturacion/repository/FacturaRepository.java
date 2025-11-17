package com.back.tpi.facturacion.facturacion.repository;

import com.back.tpi.facturacion.facturacion.entity.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> { 
    
    /**
     * NUEVO: Busca una Factura asociada a una Solicitud específica.
     */
    Optional<Factura> findBySolicitudId(Long solicitudId);
}