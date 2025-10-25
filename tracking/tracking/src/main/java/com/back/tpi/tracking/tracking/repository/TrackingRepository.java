package com.back.tpi.tracking.tracking.repository;

import com.back.tpi.tracking.tracking.entity.TrackingEvento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrackingRepository extends JpaRepository<TrackingEvento, Long> {
    List<TrackingEvento> findByContenedorIdOrderByCreatedAtDesc(Long contenedorId);
}