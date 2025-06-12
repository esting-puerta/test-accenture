package com.test.accenture.backend.interfaces.controller;

import com.test.accenture.backend.application.dto.SucursalDTO;
import com.test.accenture.backend.application.dto.ActualizarSucursalDTO;
import com.test.accenture.backend.application.use_case.sucursal.CrearSucursalUseCase;
import com.test.accenture.backend.application.use_case.sucursal.ActualizarSucursalUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sucursales")
@RequiredArgsConstructor
public class SucursalController {

    private final CrearSucursalUseCase crearSucursalUseCase;
    private final ActualizarSucursalUseCase actualizarSucursalUseCase;

    @PostMapping
    public ResponseEntity<SucursalDTO> crearSucursal(@RequestBody SucursalDTO sucursalDTO) {
        try {
            SucursalDTO result = crearSucursalUseCase.ejecutar(sucursalDTO);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping
    public ResponseEntity<SucursalDTO> actualizarSucursal(@RequestBody ActualizarSucursalDTO actualizarSucursalDTO) {
        try {
            SucursalDTO result = actualizarSucursalUseCase.ejecutar(actualizarSucursalDTO);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
} 


