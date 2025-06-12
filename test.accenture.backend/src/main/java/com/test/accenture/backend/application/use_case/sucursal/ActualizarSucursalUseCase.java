package com.test.accenture.backend.application.use_case.sucursal;

import com.test.accenture.backend.application.dto.SucursalDTO;
import com.test.accenture.backend.application.dto.ActualizarSucursalDTO;

public interface ActualizarSucursalUseCase {
    SucursalDTO ejecutar(ActualizarSucursalDTO actualizarSucursalDTO);
} 