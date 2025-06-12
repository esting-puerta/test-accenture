package com.test.accenture.backend.interfaces.controller;

import com.test.accenture.backend.application.dto.ActualizacionStockDTO;
import com.test.accenture.backend.application.dto.ProductoDTO;
import com.test.accenture.backend.application.dto.ProductoMaxStockDTO;
import com.test.accenture.backend.application.dto.ActualizarProductoDTO;
import com.test.accenture.backend.application.use_case.producto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final CrearProductoUseCase crearProductoUseCase;
    private final EliminarProductoUseCase eliminarProductoUseCase;
    private final ActualizarStockProductoUseCase actualizarStockProductoUseCase;
    private final ObtenerProductosMaxStockUseCase obtenerProductosMaxStockUseCase;
    private final ActualizarProductoUseCase actualizarProductoUseCase;

    @PostMapping
    public ResponseEntity<ProductoDTO> crearProducto(@RequestBody ProductoDTO productoDTO) {
        try {
            ProductoDTO result = crearProductoUseCase.ejecutar(productoDTO);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        try {
            eliminarProductoUseCase.ejecutar(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/stock")
    public ResponseEntity<ProductoDTO> actualizarStock(@RequestBody ActualizacionStockDTO actualizacionStockDTO) {
        try {
            ProductoDTO result = actualizarStockProductoUseCase.ejecutar(actualizacionStockDTO);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/max-stock/{franquiciaId}")
    public ResponseEntity<List<ProductoMaxStockDTO>> obtenerProductosMaxStock(@PathVariable Long franquiciaId) {
        try {
            List<ProductoMaxStockDTO> result = obtenerProductosMaxStockUseCase.ejecutar(franquiciaId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping
    public ResponseEntity<ProductoDTO> actualizarProducto(@RequestBody ActualizarProductoDTO actualizarProductoDTO) {
        try {
            ProductoDTO result = actualizarProductoUseCase.ejecutar(actualizarProductoDTO);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
} 


