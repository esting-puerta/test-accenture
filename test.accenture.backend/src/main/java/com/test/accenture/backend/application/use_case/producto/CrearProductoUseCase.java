package com.test.accenture.backend.application.use_case.producto;

import com.test.accenture.backend.application.dto.ProductoDTO;

public interface CrearProductoUseCase {
    ProductoDTO ejecutar(ProductoDTO productoDTO);
} 