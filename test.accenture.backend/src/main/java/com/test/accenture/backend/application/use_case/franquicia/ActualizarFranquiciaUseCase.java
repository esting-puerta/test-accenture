package com.test.accenture.backend.application.use_case.franquicia;

import com.test.accenture.backend.application.dto.FranquiciaDTO;
import com.test.accenture.backend.application.dto.ActualizarFranquiciaDTO;

public interface ActualizarFranquiciaUseCase {
    FranquiciaDTO ejecutar(ActualizarFranquiciaDTO actualizarFranquiciaDTO);
} 