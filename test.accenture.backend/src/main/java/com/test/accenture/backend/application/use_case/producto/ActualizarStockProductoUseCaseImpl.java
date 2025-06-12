package com.test.accenture.backend.application.use_case.producto;

import com.test.accenture.backend.application.dto.ActualizacionStockDTO;
import com.test.accenture.backend.application.dto.ProductoDTO;
import com.test.accenture.backend.domain.model.HistorialStock;
import com.test.accenture.backend.domain.model.Producto;
import com.test.accenture.backend.domain.repository.HistorialStockRepository;
import com.test.accenture.backend.domain.repository.ProductoRepository;
import com.test.accenture.backend.domain.service.EntidadNoEncontradaException;
import com.test.accenture.backend.domain.service.StockInsuficienteException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ActualizarStockProductoUseCaseImpl implements ActualizarStockProductoUseCase {

    private final ProductoRepository productoRepository;
    private final HistorialStockRepository historialStockRepository;

    @Override
    @Transactional
    public ProductoDTO ejecutar(ActualizacionStockDTO actualizacionStockDTO) {
        Producto producto = productoRepository.findById(actualizacionStockDTO.getProductoId())
                .orElseThrow(() -> new EntidadNoEncontradaException("Producto no encontrado con ID: " + actualizacionStockDTO.getProductoId()));

        int stockAnterior = producto.getStock();
        int stockNuevo;
        
        switch (actualizacionStockDTO.getTipoMovimiento()) {
            case ENTRADA:
                stockNuevo = stockAnterior + actualizacionStockDTO.getCantidad();
                break;
            case SALIDA:
                if (stockAnterior < actualizacionStockDTO.getCantidad()) {
                    throw new StockInsuficienteException(
                        "Stock insuficiente. Stock actual: " + stockAnterior + 
                        ", Cantidad solicitada: " + actualizacionStockDTO.getCantidad());
                }
                stockNuevo = stockAnterior - actualizacionStockDTO.getCantidad();
                break;
            case AJUSTE:
                stockNuevo = actualizacionStockDTO.getCantidad();
                break;
            default:
                throw new IllegalArgumentException("Tipo de movimiento no válido");
        }
        
        producto.setStock(stockNuevo);
        producto = productoRepository.save(producto);
        
        HistorialStock historialStock = new HistorialStock();
        historialStock.setProducto(producto);
        historialStock.setStockAnterior(stockAnterior);
        historialStock.setStockNuevo(stockNuevo);
        historialStock.setTipoMovimiento(actualizacionStockDTO.getTipoMovimiento());
        historialStockRepository.save(historialStock);
        
        return ProductoDTO.builder()
                .id(producto.getId())
                .nombre(producto.getNombre())
                .stock(producto.getStock())
                .precio(producto.getPrecio())
                .descripcion(producto.getDescripcion())
                .codigoProducto(producto.getCodigoProducto())
                .activo(producto.getActivo())
                .build();
    }
} 