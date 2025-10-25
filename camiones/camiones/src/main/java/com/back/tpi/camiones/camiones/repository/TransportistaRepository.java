package com.back.tpi.camiones.camiones.repository;

import com.back.tpi.camiones.camiones.entity.Transportista;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransportistaRepository extends JpaRepository<Transportista, Long> {
    
    Optional<Transportista> findByCodigo(String codigo);
    
    boolean existsByCodigo(String codigo);
    
    List<Transportista> findByActivo(Boolean activo);
}