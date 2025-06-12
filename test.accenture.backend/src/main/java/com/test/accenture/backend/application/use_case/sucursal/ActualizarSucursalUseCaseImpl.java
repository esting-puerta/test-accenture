package com.test.accenture.backend.application.use_case.sucursal;

import com.test.accenture.backend.application.dto.SucursalDTO;
import com.test.accenture.backend.application.dto.ActualizarSucursalDTO;
import com.test.accenture.backend.domain.model.Sucursal;
import com.test.accenture.backend.domain.repository.SucursalRepository;
import com.test.accenture.backend.domain.service.DuplicadoException;
import com.test.accenture.backend.domain.service.EntidadNoEncontradaException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ActualizarSucursalUseCaseImpl implements ActualizarSucursalUseCase {

    private final SucursalRepository sucursalRepository;

    @Override
    @Transactional
    public SucursalDTO ejecutar(ActualizarSucursalDTO actualizarSucursalDTO) {
        Sucursal sucursal = sucursalRepository.findById(actualizarSucursalDTO.getId())
                .orElseThrow(() -> new EntidadNoEncontradaException("Sucursal no encontrada con ID: " + actualizarSucursalDTO.getId()));

        // Verificar si el nuevo nombre ya existe en la misma franquicia
        sucursalRepository.findByNombreAndFranquiciaId(actualizarSucursalDTO.getNombre(), sucursal.getFranquicia().getId())
                .ifPresent(existingSucursal -> {
                    if (!existingSucursal.getId().equals(actualizarSucursalDTO.getId())) {
                        throw new DuplicadoException("Ya existe una sucursal con el nombre: " + actualizarSucursalDTO.getNombre() + " en la franquicia");
                    }
                });

        sucursal.setNombre(actualizarSucursalDTO.getNombre());
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