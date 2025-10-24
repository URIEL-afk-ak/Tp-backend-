package com.back.tpi.contenedores.contenedores.repository;

import com.back.tpi.contenedores.contenedores.entity.Contenedor;
import com.back.tpi.contenedores.contenedores.entity.EstadoContenedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface contenedorRepository extends JpaRepository<Contenedor, Long> {
    
    // CORREGIDO: Usar camelCase
    Optional<Contenedor> findByNumeroIdentificacion(String numeroIdentificacion);
    
    boolean existsByNumeroIdentificacion(String numeroIdentificacion);
    
    // CORREGIDO: Usar camelCase
    List<Contenedor> findByClienteId(Long clienteId);
    
    List<Contenedor> findByEstado(EstadoContenedor estado);
    
    // CORREGIDO: Usar camelCase en las queries
    @Query("SELECT c FROM Contenedor c WHERE c.estado = :estado AND c.fechaCreacion >= :fechaDesde")
    List<Contenedor> findPendientesConFiltros(@Param("estado") EstadoContenedor estado, 
                                             @Param("fechaDesde") LocalDateTime fechaDesde);
    
    @Query("SELECT c FROM Contenedor c WHERE c.estado = 'PENDIENTE'")
    List<Contenedor> findContenedoresPendientes();
    
    @Query("SELECT c FROM Contenedor c WHERE c.estado = 'PENDIENTE' AND c.clienteId = :clienteId")
    List<Contenedor> findContenedoresPendientesByCliente(@Param("clienteId") Long clienteId);
}