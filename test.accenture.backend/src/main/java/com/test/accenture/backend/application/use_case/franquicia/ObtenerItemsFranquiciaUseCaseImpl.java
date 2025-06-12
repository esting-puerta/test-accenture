package com.test.accenture.backend.application.use_case.franquicia;

import com.test.accenture.backend.application.dto.FranquiciaItemsDTO;
import com.test.accenture.backend.domain.model.Producto;
import com.test.accenture.backend.domain.repository.ProductoRepository;
import com.test.accenture.backend.domain.service.EntidadNoEncontradaException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ObtenerItemsFranquiciaUseCaseImpl implements ObtenerItemsFranquiciaUseCase {

    private final ProductoRepository productoRepository;

    @Override
    public List<FranquiciaItemsDTO> ejecutar(Long franquiciaId) {
        List<Producto> productos = productoRepository.findProductosByFranquicia(franquiciaId);
        
        if (productos.isEmpty()) {
            throw new EntidadNoEncontradaException("No se encontraron productos para la franquicia con ID: " + franquiciaId);
        }
        
        return productos.stream()
                .map(producto -> FranquiciaItemsDTO.builder()
                        .franquiciaId(franquiciaId)
                        .franquiciaNombre(producto.getSucursal().getFranquicia().getNombre())
                        .sucursalId(producto.getSucursal().getId())
                        .sucursalNombre(producto.getSucursal().getNombre())
                        .productoId(producto.getId())
                        .productoNombre(producto.getNombre())
                        .stock(producto.getStock())
                        .precio(producto.getPrecio())
                        .descripcion(producto.getDescripcion())
                        .codigoProducto(producto.getCodigoProducto())
                        .activo(producto.getActivo())
                        .build())
                .collect(Collectors.toList());
    }
} 