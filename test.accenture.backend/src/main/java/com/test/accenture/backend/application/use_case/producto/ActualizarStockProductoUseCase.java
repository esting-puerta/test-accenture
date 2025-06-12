package com.test.accenture.backend.application.use_case.producto;

import com.test.accenture.backend.application.dto.ActualizacionStockDTO;
import com.test.accenture.backend.application.dto.ProductoDTO;

public interface ActualizarStockProductoUseCase {
    ProductoDTO ejecutar(ActualizacionStockDTO actualizacionStockDTO);
} 