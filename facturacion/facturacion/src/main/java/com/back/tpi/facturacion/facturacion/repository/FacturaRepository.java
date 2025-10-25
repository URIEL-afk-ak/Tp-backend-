package com.back.tpi.facturacion.facturacion.repository;

import com.back.tpi.facturacion.facturacion.entity.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> { }