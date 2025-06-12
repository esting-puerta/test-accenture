package com.test.accenture.backend.domain.repository;

import com.test.accenture.backend.domain.model.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface SucursalRepository extends JpaRepository<Sucursal, Long> {
    Optional<Sucursal> findByNombreAndFranquiciaId(String nombre, Long franquiciaId);
} 