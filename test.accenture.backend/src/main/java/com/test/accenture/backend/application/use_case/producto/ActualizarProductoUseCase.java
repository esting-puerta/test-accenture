package com.test.accenture.backend.application.use_case.producto;

import com.test.accenture.backend.application.dto.ProductoDTO;
import com.test.accenture.backend.application.dto.ActualizarProductoDTO;

public interface ActualizarProductoUseCase {
    ProductoDTO ejecutar(ActualizarProductoDTO actualizarProductoDTO);
} 