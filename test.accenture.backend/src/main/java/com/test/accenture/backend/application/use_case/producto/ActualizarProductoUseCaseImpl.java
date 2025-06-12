package com.test.accenture.backend.application.use_case.producto;

import com.test.accenture.backend.application.dto.ProductoDTO;
import com.test.accenture.backend.application.dto.ActualizarProductoDTO;
import com.test.accenture.backend.domain.model.Producto;
import com.test.accenture.backend.domain.repository.ProductoRepository;
import com.test.accenture.backend.domain.service.DuplicadoException;
import com.test.accenture.backend.domain.service.EntidadNoEncontradaException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ActualizarProductoUseCaseImpl implements ActualizarProductoUseCase {

    private final ProductoRepository productoRepository;

    @Override
    @Transactional
    public ProductoDTO ejecutar(ActualizarProductoDTO actualizarProductoDTO) {
        Producto producto = productoRepository.findById(actualizarProductoDTO.getId())
                .orElseThrow(() -> new EntidadNoEncontradaException("Producto no encontrado con ID: " + actualizarProductoDTO.getId()));

        // Verificar si el nuevo nombre ya existe en la misma sucursal
        productoRepository.findByNombreAndSucursalId(actualizarProductoDTO.getNombre(), producto.getSucursal().getId())
                .ifPresent(existingProducto -> {
                    if (!existingProducto.getId().equals(actualizarProductoDTO.getId())) {
                        throw new DuplicadoException("Ya existe un producto con el nombre: " + actualizarProductoDTO.getNombre() + " en la sucursal");
                    }
                });

        producto.setNombre(actualizarProductoDTO.getNombre());
        producto = productoRepository.save(producto);

        return ProductoDTO.builder()
                .id(producto.getId())
                .nombre(producto.getNombre())
                .sucursalId(producto.getSucursal().getId())
                .stock(producto.getStock())
                .precio(producto.getPrecio())
                .descripcion(producto.getDescripcion())
                .codigoProducto(producto.getCodigoProducto())
                .activo(producto.getActivo())
                .build();
    }
} 