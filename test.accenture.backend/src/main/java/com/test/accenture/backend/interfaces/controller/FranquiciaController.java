package com.test.accenture.backend.interfaces.controller;

import com.test.accenture.backend.application.dto.FranquiciaDTO;
import com.test.accenture.backend.application.dto.FranquiciaItemsDTO;
import com.test.accenture.backend.application.dto.ActualizarFranquiciaDTO;
import com.test.accenture.backend.application.use_case.franquicia.CrearFranquiciaUseCase;
import com.test.accenture.backend.application.use_case.franquicia.ObtenerItemsFranquiciaUseCase;
import com.test.accenture.backend.application.use_case.franquicia.ActualizarFranquiciaUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/franquicias")
@RequiredArgsConstructor
public class FranquiciaController {

    private final CrearFranquiciaUseCase crearFranquiciaUseCase;
    private final ObtenerItemsFranquiciaUseCase obtenerItemsFranquiciaUseCase;
    private final ActualizarFranquiciaUseCase actualizarFranquiciaUseCase;

    @PostMapping
    public ResponseEntity<FranquiciaDTO> crearFranquicia(@RequestBody FranquiciaDTO franquiciaDTO) {
        try {
            FranquiciaDTO result = crearFranquiciaUseCase.ejecutar(franquiciaDTO);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}/items")
    public ResponseEntity<List<FranquiciaItemsDTO>> obtenerItemsFranquicia(@PathVariable Long id) {
        try {
            List<FranquiciaItemsDTO> result = obtenerItemsFranquiciaUseCase.ejecutar(id);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping
    public ResponseEntity<FranquiciaDTO> actualizarFranquicia(@RequestBody ActualizarFranquiciaDTO actualizarFranquiciaDTO) {
        try {
            FranquiciaDTO result = actualizarFranquiciaUseCase.ejecutar(actualizarFranquiciaDTO);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
} 


