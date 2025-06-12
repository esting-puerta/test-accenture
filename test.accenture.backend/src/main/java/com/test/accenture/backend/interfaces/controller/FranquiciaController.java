package com.test.accenture.backend.interfaces.controller;

import com.test.accenture.backend.application.dto.FranquiciaDTO;
import com.test.accenture.backend.application.dto.FranquiciaItemsDTO;
import com.test.accenture.backend.application.dto.ActualizarFranquiciaDTO;
import com.test.accenture.backend.application.use_case.franquicia.CrearFranquiciaUseCase;
import com.test.accenture.backend.application.use_case.franquicia.ObtenerItemsFranquiciaUseCase;
import com.test.accenture.backend.application.use_case.franquicia.ActualizarFranquiciaUseCase;
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
import java.util.List;

@RestController
@RequestMapping("/api/franquicias")
@RequiredArgsConstructor
@Tag(name = "Franquicias", description = "API para la gestión de franquicias")
public class FranquiciaController {

    private final CrearFranquiciaUseCase crearFranquiciaUseCase;
    private final ObtenerItemsFranquiciaUseCase obtenerItemsFranquiciaUseCase;
    private final ActualizarFranquiciaUseCase actualizarFranquiciaUseCase;

    @Operation(summary = "Crear una nueva franquicia", description = "Crea una nueva franquicia en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Franquicia creada exitosamente",
            content = @Content(schema = @Schema(implementation = FranquiciaDTO.class))),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PostMapping
    public ResponseEntity<FranquiciaDTO> crearFranquicia(
            @Parameter(description = "Datos de la franquicia a crear", required = true)
            @RequestBody FranquiciaDTO franquiciaDTO) {
        try {
            FranquiciaDTO result = crearFranquiciaUseCase.ejecutar(franquiciaDTO);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Obtener items de una franquicia", description = "Obtiene todos los items (productos) de una franquicia específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Items obtenidos exitosamente",
            content = @Content(schema = @Schema(implementation = FranquiciaItemsDTO.class))),
        @ApiResponse(responseCode = "404", description = "Franquicia no encontrada")
    })
    @GetMapping("/{id}/items")
    public ResponseEntity<List<FranquiciaItemsDTO>> obtenerItemsFranquicia(
            @Parameter(description = "ID de la franquicia", required = true)
            @PathVariable Long id) {
        try {
            List<FranquiciaItemsDTO> result = obtenerItemsFranquiciaUseCase.ejecutar(id);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Actualizar una franquicia existente", description = "Actualiza los datos de una franquicia existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Franquicia actualizada exitosamente",
            content = @Content(schema = @Schema(implementation = FranquiciaDTO.class))),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "404", description = "Franquicia no encontrada")
    })
    @PutMapping
    public ResponseEntity<FranquiciaDTO> actualizarFranquicia(
            @Parameter(description = "Datos de la franquicia a actualizar", required = true)
            @RequestBody ActualizarFranquiciaDTO actualizarFranquiciaDTO) {
        try {
            FranquiciaDTO result = actualizarFranquiciaUseCase.ejecutar(actualizarFranquiciaDTO);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
} 


