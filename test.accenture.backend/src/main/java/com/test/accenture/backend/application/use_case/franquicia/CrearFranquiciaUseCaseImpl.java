package com.test.accenture.backend.application.use_case.franquicia;

import com.test.accenture.backend.application.dto.FranquiciaDTO;
import com.test.accenture.backend.domain.model.Franquicia;
import com.test.accenture.backend.domain.repository.FranquiciaRepository;
import com.test.accenture.backend.domain.service.DuplicadoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class CrearFranquiciaUseCaseImpl implements CrearFranquiciaUseCase {

    private final FranquiciaRepository franquiciaRepository;

    @Override
    public FranquiciaDTO ejecutar(FranquiciaDTO franquiciaDTO) {
        if (franquiciaDTO == null || !StringUtils.hasText(franquiciaDTO.getNombre())) {
            throw new IllegalArgumentException("El nombre de la franquicia es obligatorio");
        }

        boolean exists = franquiciaRepository.findByNombre(franquiciaDTO.getNombre()).isPresent();
        if (exists) {
            throw new DuplicadoException("Ya existe una franquicia con el nombre: " + franquiciaDTO.getNombre());
        }

        Franquicia franquicia = Franquicia.builder()
                .nombre(franquiciaDTO.getNombre())
                .activo(true)
                .build();

        franquicia = franquiciaRepository.save(franquicia);
        return FranquiciaDTO.builder()
                .id(franquicia.getId())
                .nombre(franquicia.getNombre())
                .activo(franquicia.getActivo())
                .build();
    }
} 