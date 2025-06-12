package com.test.accenture.backend.domain.repository;

import com.test.accenture.backend.domain.model.HistorialStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistorialStockRepository extends JpaRepository<HistorialStock, Long> {
} 