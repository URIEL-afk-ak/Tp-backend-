package com.back.tpi.depositos.depositos.repository;

import com.back.tpi.depositos.depositos.entity.Deposito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepositoRepository extends JpaRepository<Deposito, Long> {
    
    List<Deposito> findByNombreContainingIgnoreCase(String nombre);
}