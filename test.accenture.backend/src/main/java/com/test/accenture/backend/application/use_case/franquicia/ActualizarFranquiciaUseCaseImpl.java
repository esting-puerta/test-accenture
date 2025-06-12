package com.test.accenture.backend.application.use_case.franquicia;

import com.test.accenture.backend.application.dto.FranquiciaDTO;
import com.test.accenture.backend.application.dto.ActualizarFranquiciaDTO;
import com.test.accenture.backend.domain.model.Franquicia;
import com.test.accenture.backend.domain.repository.FranquiciaRepository;
import com.test.accenture.backend.domain.service.DuplicadoException;
import com.test.accenture.backend.domain.service.EntidadNoEncontradaException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ActualizarFranquiciaUseCaseImpl implements ActualizarFranquiciaUseCase {

    private final FranquiciaRepository franquiciaRepository;

    @Override
    @Transactional
    public FranquiciaDTO ejecutar(ActualizarFranquiciaDTO actualizarFranquiciaDTO) {
        Franquicia franquicia = franquiciaRepository.findById(actualizarFranquiciaDTO.getId())
                .orElseThrow(() -> new EntidadNoEncontradaException("Franquicia no encontrada con ID: " + actualizarFranquiciaDTO.getId()));

        // Verificar si el nuevo nombre ya existe en otra franquicia
        franquiciaRepository.findByNombre(actualizarFranquiciaDTO.getNombre())
                .ifPresent(existingFranquicia -> {
                    if (!existingFranquicia.getId().equals(actualizarFranquiciaDTO.getId())) {
                        throw new DuplicadoException("Ya existe una franquicia con el nombre: " + actualizarFranquiciaDTO.getNombre());
                    }
                });

        franquicia.setNombre(actualizarFranquiciaDTO.getNombre());
        franquicia = franquiciaRepository.save(franquicia);

        return FranquiciaDTO.builder()
                .id(franquicia.getId())
                .nombre(franquicia.getNombre())
                .activo(franquicia.getActivo())
                .build();
    }
} 