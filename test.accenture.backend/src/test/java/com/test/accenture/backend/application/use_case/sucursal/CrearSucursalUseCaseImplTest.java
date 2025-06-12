package com.test.accenture.backend.application.use_case.sucursal;

import com.test.accenture.backend.application.dto.SucursalDTO;
import com.test.accenture.backend.domain.model.Franquicia;
import com.test.accenture.backend.domain.model.Sucursal;
import com.test.accenture.backend.domain.repository.FranquiciaRepository;
import com.test.accenture.backend.domain.repository.SucursalRepository;
import com.test.accenture.backend.domain.service.DuplicadoException;
import com.test.accenture.backend.domain.service.EntidadNoEncontradaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CrearSucursalUseCaseImplTest {

    @Mock
    private SucursalRepository sucursalRepository;

    @Mock
    private FranquiciaRepository franquiciaRepository;

    @InjectMocks
    private CrearSucursalUseCaseImpl crearSucursalUseCase;

    private Franquicia franquicia;
    private SucursalDTO sucursalDTO;

    @BeforeEach
    void setUp() {
        franquicia = Franquicia.builder()
                .id(1L)
                .nombre("Franquicia Test")
                .activo(true)
                .build();

        sucursalDTO = SucursalDTO.builder()
                .nombre("Sucursal Test")
                .franquiciaId(1L)
                .direccion("Dirección Test")
                .telefono("1234567890")
                .email("test@test.com")
                .build();
    }

    @Test
    void cuandoCrearSucursalExitoso_entoncesRetornaSucursalDTO() {
        when(franquiciaRepository.findById(1L)).thenReturn(Optional.of(franquicia));
        when(sucursalRepository.findByNombreAndFranquiciaId(anyString(), anyLong()))
                .thenReturn(Optional.empty());
        when(sucursalRepository.save(any(Sucursal.class))).thenAnswer(i -> i.getArgument(0));

        SucursalDTO resultado = crearSucursalUseCase.ejecutar(sucursalDTO);

        assertNotNull(resultado);
        assertEquals(sucursalDTO.getNombre(), resultado.getNombre());
        assertEquals(sucursalDTO.getFranquiciaId(), resultado.getFranquiciaId());
        assertEquals(sucursalDTO.getDireccion(), resultado.getDireccion());
        assertEquals(sucursalDTO.getTelefono(), resultado.getTelefono());
        assertEquals(sucursalDTO.getEmail(), resultado.getEmail());
        assertTrue(resultado.getActivo());

        verify(franquiciaRepository).findById(1L);
        verify(sucursalRepository).findByNombreAndFranquiciaId(sucursalDTO.getNombre(), franquicia.getId());
        verify(sucursalRepository).save(any(Sucursal.class));
    }

    @Test
    void cuandoFranquiciaNoExiste_entoncesLanzaEntidadNoEncontradaException() {
        when(franquiciaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntidadNoEncontradaException.class, () -> crearSucursalUseCase.ejecutar(sucursalDTO));
        verify(franquiciaRepository).findById(1L);
        verify(sucursalRepository, never()).save(any(Sucursal.class));
    }

    @Test
    void cuandoSucursalYaExiste_entoncesLanzaDuplicadoException() {
        Sucursal sucursalExistente = Sucursal.builder()
                .id(1L)
                .nombre("Sucursal Test")
                .franquicia(franquicia)
                .build();

        when(franquiciaRepository.findById(1L)).thenReturn(Optional.of(franquicia));
        when(sucursalRepository.findByNombreAndFranquiciaId(anyString(), anyLong()))
                .thenReturn(Optional.of(sucursalExistente));

        assertThrows(DuplicadoException.class, () -> crearSucursalUseCase.ejecutar(sucursalDTO));
        verify(franquiciaRepository).findById(1L);
        verify(sucursalRepository).findByNombreAndFranquiciaId(sucursalDTO.getNombre(), franquicia.getId());
        verify(sucursalRepository, never()).save(any(Sucursal.class));
    }
}