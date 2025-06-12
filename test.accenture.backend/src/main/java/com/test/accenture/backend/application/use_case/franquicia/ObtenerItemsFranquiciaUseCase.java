package com.test.accenture.backend.application.use_case.franquicia;

import com.test.accenture.backend.application.dto.FranquiciaItemsDTO;
import java.util.List;

public interface ObtenerItemsFranquiciaUseCase {
    List<FranquiciaItemsDTO> ejecutar(Long franquiciaId);
} 