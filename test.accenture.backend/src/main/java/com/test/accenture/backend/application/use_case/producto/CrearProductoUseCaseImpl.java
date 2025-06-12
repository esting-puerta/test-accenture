package com.test.accenture.backend.application.use_case.producto;

import com.test.accenture.backend.application.dto.ProductoDTO;
import com.test.accenture.backend.domain.model.Producto;
import com.test.accenture.backend.domain.repository.ProductoRepository;
import com.test.accenture.backend.domain.repository.SucursalRepository;
import com.test.accenture.backend.domain.service.DuplicadoException;
import com.test.accenture.backend.domain.service.EntidadNoEncontradaException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CrearProductoUseCaseImpl implements CrearProductoUseCase {

    private final ProductoRepository productoRepository;
    private final SucursalRepository sucursalRepository;

    @Override
    public ProductoDTO ejecutar(ProductoDTO productoDTO) {
        var sucursal = sucursalRepository.findById(productoDTO.getSucursalId())
                .orElseThrow(() -> new EntidadNoEncontradaException("Sucursal no encontrada con ID: " + productoDTO.getSucursalId()));
        var exists = productoRepository.findByNombreAndSucursalId(productoDTO.getNombre(), sucursal.getId()).isPresent();
        if (exists) {
            throw new DuplicadoException("Ya existe un producto con el nombre: " + productoDTO.getNombre() + " en la sucursal");
        }
        Producto producto = Producto.builder()
                .nombre(productoDTO.getNombre())
                .sucursal(sucursal)
                .stock(productoDTO.getStock() != null ? productoDTO.getStock() : 0)
                .precio(productoDTO.getPrecio())
                .descripcion(productoDTO.getDescripcion())
                .codigoProducto(productoDTO.getCodigoProducto())
                .activo(true)
                .build();
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