package com.test.accenture.backend.application.use_case.sucursal;

import com.test.accenture.backend.application.dto.SucursalDTO;
import com.test.accenture.backend.domain.model.Franquicia;
import com.test.accenture.backend.domain.model.Sucursal;
import com.test.accenture.backend.domain.repository.FranquiciaRepository;
import com.test.accenture.backend.domain.repository.SucursalRepository;
import com.test.accenture.backend.domain.service.DuplicadoException;
import com.test.accenture.backend.domain.service.EntidadNoEncontradaException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CrearSucursalUseCaseImpl implements CrearSucursalUseCase {

    private final SucursalRepository sucursalRepository;
    private final FranquiciaRepository franquiciaRepository;

    @Override
    public SucursalDTO ejecutar(SucursalDTO sucursalDTO) {
        Franquicia franquicia = franquiciaRepository.findById(sucursalDTO.getFranquiciaId())
                .orElseThrow(() -> new EntidadNoEncontradaException("Franquicia no encontrada con ID: " + sucursalDTO.getFranquiciaId()));
        boolean exists = sucursalRepository.findByNombreAndFranquiciaId(sucursalDTO.getNombre(), franquicia.getId()).isPresent();
        if (exists) {
            throw new DuplicadoException("Ya existe una sucursal con el nombre: " + sucursalDTO.getNombre() + " en la franquicia");
        }
        Sucursal sucursal = Sucursal.builder()
                .nombre(sucursalDTO.getNombre())
                .franquicia(franquicia)
                .direccion(sucursalDTO.getDireccion())
                .telefono(sucursalDTO.getTelefono())
                .email(sucursalDTO.getEmail())
                .activo(true)
                .build();
        sucursal = sucursalRepository.save(sucursal);
        return SucursalDTO.builder()
                .id(sucursal.getId())
                .nombre(sucursal.getNombre())
                .franquiciaId(sucursal.getFranquicia().getId())
                .direccion(sucursal.getDireccion())
                .telefono(sucursal.getTelefono())
                .email(sucursal.getEmail())
                .activo(sucursal.getActivo())
                .build();
    }
} 