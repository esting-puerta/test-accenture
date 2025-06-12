package com.test.accenture.backend.interfaces.controller;

import com.test.accenture.backend.application.dto.SucursalDTO;
import com.test.accenture.backend.application.dto.ActualizarSucursalDTO;
import com.test.accenture.backend.application.use_case.sucursal.CrearSucursalUseCase;
import com.test.accenture.backend.application.use_case.sucursal.ActualizarSucursalUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sucursales")
@RequiredArgsConstructor
@Tag(name = "Sucursales", description = "API para la gestión de sucursales")
public class SucursalController {

    private final CrearSucursalUseCase crearSucursalUseCase;
    private final ActualizarSucursalUseCase actualizarSucursalUseCase;

    @Operation(summary = "Crear una nueva sucursal", description = "Crea una nueva sucursal asociada a una franquicia")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sucursal creada exitosamente",
            content = @Content(schema = @Schema(implementation = SucursalDTO.class))),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "404", description = "Franquicia no encontrada")
    })
    @PostMapping
    public ResponseEntity<SucursalDTO> crearSucursal(
            @Parameter(description = "Datos de la sucursal a crear", required = true)
            @RequestBody SucursalDTO sucursalDTO) {
        try {
            SucursalDTO result = crearSucursalUseCase.ejecutar(sucursalDTO);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Actualizar una sucursal existente", description = "Actualiza los datos de una sucursal existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sucursal actualizada exitosamente",
            content = @Content(schema = @Schema(implementation = SucursalDTO.class))),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "404", description = "Sucursal no encontrada")
    })
    @PutMapping
    public ResponseEntity<SucursalDTO> actualizarSucursal(
            @Parameter(description = "Datos de la sucursal a actualizar", required = true)
            @RequestBody ActualizarSucursalDTO actualizarSucursalDTO) {
        try {
            SucursalDTO result = actualizarSucursalUseCase.ejecutar(actualizarSucursalDTO);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
} 


