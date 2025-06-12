package com.test.accenture.backend.application.use_case.sucursal;

import com.test.accenture.backend.application.dto.ActualizarSucursalDTO;
import com.test.accenture.backend.application.dto.SucursalDTO;
import com.test.accenture.backend.domain.model.Franquicia;
import com.test.accenture.backend.domain.model.Sucursal;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ActualizarSucursalUseCaseImplTest {

    @Mock
    private SucursalRepository sucursalRepository;

    @InjectMocks
    private ActualizarSucursalUseCaseImpl actualizarSucursalUseCase;

    private Franquicia franquicia;
    private Sucursal sucursal;
    private ActualizarSucursalDTO actualizarSucursalDTO;

    @BeforeEach
    void setUp() {
        franquicia = Franquicia.builder()
                .id(1L)
                .nombre("Franquicia Test")
                .activo(true)
                .build();

        sucursal = Sucursal.builder()
                .id(1L)
                .nombre("Sucursal Original")
                .franquicia(franquicia)
                .direccion("Dirección Original")
                .telefono("1234567890")
                .email("original@test.com")
                .activo(true)
                .build();

        actualizarSucursalDTO = ActualizarSucursalDTO.builder()
                .id(1L)
                .nombre("Sucursal Actualizada")
                .build();
    }

    @Test
    void cuandoActualizarSucursalExitoso_entoncesRetornaSucursalDTO() {
        when(sucursalRepository.findById(1L)).thenReturn(Optional.of(sucursal));
        when(sucursalRepository.findByNombreAndFranquiciaId(anyString(), anyLong()))
                .thenReturn(Optional.empty());
        when(sucursalRepository.save(any(Sucursal.class))).thenAnswer(i -> i.getArgument(0));

        SucursalDTO resultado = actualizarSucursalUseCase.ejecutar(actualizarSucursalDTO);

        assertNotNull(resultado);
        assertEquals(actualizarSucursalDTO.getNombre(), resultado.getNombre());
        assertEquals(sucursal.getFranquicia().getId(), resultado.getFranquiciaId());
        assertEquals(sucursal.getDireccion(), resultado.getDireccion());
        assertEquals(sucursal.getTelefono(), resultado.getTelefono());
        assertEquals(sucursal.getEmail(), resultado.getEmail());
        assertTrue(resultado.getActivo());

        verify(sucursalRepository).findById(1L);
        verify(sucursalRepository).findByNombreAndFranquiciaId(actualizarSucursalDTO.getNombre(), franquicia.getId());
        verify(sucursalRepository).save(any(Sucursal.class));
    }

    @Test
    void cuandoSucursalNoExiste_entoncesLanzaEntidadNoEncontradaException() {
        when(sucursalRepository.findById(1L)).thenReturn(Optional.empty());

        EntidadNoEncontradaException exception = assertThrows(EntidadNoEncontradaException.class, 
            () -> actualizarSucursalUseCase.ejecutar(actualizarSucursalDTO));
        assertEquals("Sucursal no encontrada con ID: 1", exception.getMessage());
        verify(sucursalRepository).findById(1L);
        verify(sucursalRepository, never()).save(any(Sucursal.class));
    }

    @Test
    void cuandoNuevoNombreYaExiste_entoncesLanzaDuplicadoException() {
        Sucursal sucursalExistente = Sucursal.builder()
                .id(2L)
                .nombre("Sucursal Actualizada")
                .franquicia(franquicia)
                .build();

        when(sucursalRepository.findById(1L)).thenReturn(Optional.of(sucursal));
        when(sucursalRepository.findByNombreAndFranquiciaId(anyString(), anyLong()))
                .thenReturn(Optional.of(sucursalExistente));

        DuplicadoException exception = assertThrows(DuplicadoException.class, 
            () -> actualizarSucursalUseCase.ejecutar(actualizarSucursalDTO));
        assertEquals("Ya existe una sucursal con el nombre: Sucursal Actualizada en la franquicia", exception.getMessage());
        verify(sucursalRepository).findById(1L);
        verify(sucursalRepository).findByNombreAndFranquiciaId(actualizarSucursalDTO.getNombre(), franquicia.getId());
        verify(sucursalRepository, never()).save(any(Sucursal.class));
    }

    @Test
    void cuandoNombreEsNulo_entoncesLanzaIllegalArgumentException() {
        actualizarSucursalDTO.setNombre(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> actualizarSucursalUseCase.ejecutar(actualizarSucursalDTO));
        assertEquals("El nombre de la sucursal es obligatorio", exception.getMessage());
        verify(sucursalRepository, never()).findById(anyLong());
        verify(sucursalRepository, never()).save(any(Sucursal.class));
    }

    @Test
    void cuandoNombreEsVacio_entoncesLanzaIllegalArgumentException() {
        actualizarSucursalDTO.setNombre("");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> actualizarSucursalUseCase.ejecutar(actualizarSucursalDTO));
        assertEquals("El nombre de la sucursal es obligatorio", exception.getMessage());
        verify(sucursalRepository, never()).findById(anyLong());
        verify(sucursalRepository, never()).save(any(Sucursal.class));
    }

    @Test
    void cuandoActualizarSucursalDTOEsNulo_entoncesLanzaIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> actualizarSucursalUseCase.ejecutar(null));
        assertEquals("El nombre de la sucursal es obligatorio", exception.getMessage());
        verify(sucursalRepository, never()).findById(anyLong());
        verify(sucursalRepository, never()).save(any(Sucursal.class));
    }
}