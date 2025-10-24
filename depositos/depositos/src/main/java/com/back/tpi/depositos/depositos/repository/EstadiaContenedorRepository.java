package com.back.tpi.depositos.depositos.repository;

import com.back.tpi.depositos.depositos.entity.EstadiaContenedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstadiaContenedorRepository extends JpaRepository<EstadiaContenedor, Long> {
    
    List<EstadiaContenedor> findByDepositoId(Long depositoId);
    
    List<EstadiaContenedor> findByContenedorId(Long contenedorId);
    
    @Query("SELECT e FROM EstadiaContenedor e WHERE e.contenedorId = :contenedorId AND e.fechaSalida IS NULL")
    Optional<EstadiaContenedor> findEstadiaActivaByContenedorId(@Param("contenedorId") Long contenedorId);
    
    @Query("SELECT e FROM EstadiaContenedor e WHERE e.depositoId = :depositoId AND e.fechaSalida IS NULL")
    List<EstadiaContenedor> findEstadiasActivasByDepositoId(@Param("depositoId") Long depositoId);
}