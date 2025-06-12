package com.test.accenture.backend.domain.repository;

import com.test.accenture.backend.domain.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    Optional<Producto> findByNombreAndSucursalId(String nombre, Long sucursalId);
    
    @Query("""
        SELECT p FROM Producto p
        JOIN p.sucursal s
        WHERE s.franquicia.id = :franquiciaId
        AND p.activo = true
        AND p.stock = (
            SELECT MAX(p2.stock)
            FROM Producto p2
            JOIN p2.sucursal s2
            WHERE s2.id = s.id
            AND p2.activo = true
        )
        ORDER BY s.nombre, p.stock DESC
        """)
    List<Producto> findProductosMaxStockByFranquicia(@Param("franquiciaId") Long franquiciaId);
    
    @Query("""
        SELECT p FROM Producto p
        JOIN p.sucursal s
        WHERE s.franquicia.id = :franquiciaId
        AND p.activo = true
        ORDER BY s.nombre, p.nombre
        """)
    List<Producto> findProductosByFranquicia(@Param("franquiciaId") Long franquiciaId);
} 