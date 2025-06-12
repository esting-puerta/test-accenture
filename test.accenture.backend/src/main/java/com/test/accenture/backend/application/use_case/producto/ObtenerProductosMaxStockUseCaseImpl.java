package com.test.accenture.backend.application.use_case.producto;

import com.test.accenture.backend.application.dto.ProductoMaxStockDTO;
import com.test.accenture.backend.domain.model.Producto;
import com.test.accenture.backend.domain.repository.ProductoRepository;
import com.test.accenture.backend.domain.service.EntidadNoEncontradaException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ObtenerProductosMaxStockUseCaseImpl implements ObtenerProductosMaxStockUseCase {

    private final ProductoRepository productoRepository;

    @Override
    public List<ProductoMaxStockDTO> ejecutar(Long franquiciaId) {
        List<Producto> productos = productoRepository.findProductosMaxStockByFranquicia(franquiciaId);
        
        if (productos.isEmpty()) {
            throw new EntidadNoEncontradaException("No se encontraron productos para la franquicia con ID: " + franquiciaId);
        }
        
        return productos.stream()
                .map(producto -> ProductoMaxStockDTO.builder()
                        .franquiciaId(franquiciaId)
                        .franquiciaNombre(producto.getSucursal().getFranquicia().getNombre())
                        .sucursalId(producto.getSucursal().getId())
                        .sucursalNombre(producto.getSucursal().getNombre())
                        .productoId(producto.getId())
                        .productoNombre(producto.getNombre())
                        .stock(producto.getStock())
                        .precio(producto.getPrecio())
                        .descripcion(producto.getDescripcion())
                        .build())
                .collect(Collectors.toList());
    }
} 