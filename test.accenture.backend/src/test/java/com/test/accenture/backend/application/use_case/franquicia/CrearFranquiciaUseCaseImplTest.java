package com.test.accenture.backend.application.use_case.franquicia;

import com.test.accenture.backend.application.dto.FranquiciaDTO;
import com.test.accenture.backend.domain.model.Franquicia;
import com.test.accenture.backend.domain.repository.FranquiciaRepository;
import com.test.accenture.backend.domain.service.DuplicadoException;
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
class CrearFranquiciaUseCaseImplTest {

    @Mock
    private FranquiciaRepository franquiciaRepository;

    @InjectMocks
    private CrearFranquiciaUseCaseImpl crearFranquiciaUseCase;

    private FranquiciaDTO franquiciaDTO;

    @BeforeEach
    void setUp() {
        franquiciaDTO = FranquiciaDTO.builder()
                .nombre("Franquicia Test")
                .build();
    }

    @Test
    void cuandoCrearFranquiciaExitoso_entoncesRetornaFranquiciaDTO() {
        when(franquiciaRepository.findByNombre(anyString())).thenReturn(Optional.empty());
        when(franquiciaRepository.save(any(Franquicia.class))).thenAnswer(i -> i.getArgument(0));

        FranquiciaDTO resultado = crearFranquiciaUseCase.ejecutar(franquiciaDTO);

        assertNotNull(resultado);
        assertEquals(franquiciaDTO.getNombre(), resultado.getNombre());
        assertTrue(resultado.getActivo());

        verify(franquiciaRepository).findByNombre(franquiciaDTO.getNombre());
        verify(franquiciaRepository).save(any(Franquicia.class));
    }

    @Test
    void cuandoFranquiciaYaExiste_entoncesLanzaDuplicadoException() {
        Franquicia franquiciaExistente = Franquicia.builder()
                .id(1L)
                .nombre("Franquicia Test")
                .activo(true)
                .build();

        when(franquiciaRepository.findByNombre(anyString()))
                .thenReturn(Optional.of(franquiciaExistente));

        assertThrows(DuplicadoException.class, () -> crearFranquiciaUseCase.ejecutar(franquiciaDTO));
        verify(franquiciaRepository).findByNombre(franquiciaDTO.getNombre());
        verify(franquiciaRepository, never()).save(any(Franquicia.class));
    }

    @Test
    void cuandoNombreFranquiciaEsNulo_entoncesLanzaIllegalArgumentException() {
        franquiciaDTO.setNombre(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> crearFranquiciaUseCase.ejecutar(franquiciaDTO));
        assertEquals("El nombre de la franquicia es obligatorio", exception.getMessage());
        verify(franquiciaRepository, never()).findByNombre(anyString());
        verify(franquiciaRepository, never()).save(any(Franquicia.class));
    }

    @Test
    void cuandoNombreFranquiciaEsVacio_entoncesLanzaIllegalArgumentException() {
        franquiciaDTO.setNombre("");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> crearFranquiciaUseCase.ejecutar(franquiciaDTO));
        assertEquals("El nombre de la franquicia es obligatorio", exception.getMessage());
        verify(franquiciaRepository, never()).findByNombre(anyString());
        verify(franquiciaRepository, never()).save(any(Franquicia.class));
    }

    @Test
    void cuandoFranquiciaDTOEsNulo_entoncesLanzaIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> crearFranquiciaUseCase.ejecutar(null));
        assertEquals("El nombre de la franquicia es obligatorio", exception.getMessage());
        verify(franquiciaRepository, never()).findByNombre(anyString());
        verify(franquiciaRepository, never()).save(any(Franquicia.class));
    }
}