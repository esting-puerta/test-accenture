package com.test.accenture.backend.domain.repository;

import com.test.accenture.backend.domain.model.Franquicia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface FranquiciaRepository extends JpaRepository<Franquicia, Long> {
    Optional<Franquicia> findByNombre(String nombre);
} 