package com.test.accenture.backend.application.use_case.producto;

import com.test.accenture.backend.application.dto.ProductoMaxStockDTO;
import java.util.List;

public interface ObtenerProductosMaxStockUseCase {
    List<ProductoMaxStockDTO> ejecutar(Long franquiciaId);
} 