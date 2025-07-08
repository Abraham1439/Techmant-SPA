package com.techmant.Gestion_tecnico.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.techmant.Gestion_tecnico.model.Tecnico;
import com.techmant.Gestion_tecnico.repository.TecnicoRepository;

@ExtendWith(MockitoExtension.class)
public class TecnicoServiceTest {
    
     
    @Mock
    private TecnicoRepository tecnicoRepository;

    @InjectMocks
    private TecnicoService tecnicoService;

    private Tecnico tecnico;

    @BeforeEach
    void setUp() {
        // Especialidad relevante para técnicos
        tecnico = new Tecnico(1L, "Juan Pérez", "Reparación de hardware");
    }

    @Test
    void obtenerTodosLosTecnicos_returnsList() {
        List<Tecnico> tecnicos = Arrays.asList(tecnico, 
            new Tecnico(2L, "Ana Gómez", "Instalación de software"),
            new Tecnico(3L, "Pedro Sánchez", "Soporte técnico general"));
        when(tecnicoRepository.findAll()).thenReturn(tecnicos);

        List<Tecnico> result = tecnicoService.obtenerTodosLosTecnicos();

        assertEquals(3, result.size());
        assertEquals("Juan Pérez", result.get(0).getNombre());
        assertEquals("Reparación de hardware", result.get(0).getEspecialidad());
        assertEquals("Ana Gómez", result.get(1).getNombre());
        assertEquals("Instalación de software", result.get(1).getEspecialidad());
        assertEquals("Pedro Sánchez", result.get(2).getNombre());
        assertEquals("Soporte técnico general", result.get(2).getEspecialidad());
    }

    @Test
    void obtenerTecnicoPorId_found() {
        when(tecnicoRepository.findById(1L)).thenReturn(Optional.of(tecnico));

        Tecnico result = tecnicoService.obtenerTecnicoPorId(1L);

        assertNotNull(result);
        assertEquals("Juan Pérez", result.getNombre());
        assertEquals("Reparación de hardware", result.getEspecialidad());
    }

    @Test
    void crearTecnico_savesAndReturns() {
        when(tecnicoRepository.save(tecnico)).thenReturn(tecnico);

        Tecnico result = tecnicoService.crearTecnico(tecnico);

        assertNotNull(result);
        assertEquals("Juan Pérez", result.getNombre());
        assertEquals("Reparación de hardware", result.getEspecialidad());
    }

    @Test
    void actualizarTecnico_successful() {
        Tecnico actualizado = new Tecnico(1L, "Juan Pérez Actualizado", "Instalación de software");

        when(tecnicoRepository.existsById(1L)).thenReturn(true);
        when(tecnicoRepository.save(actualizado)).thenReturn(actualizado);

        Tecnico result = tecnicoService.actualizarTecnico(1L, actualizado);

        assertEquals("Juan Pérez Actualizado", result.getNombre());
        assertEquals("Instalación de software", result.getEspecialidad());
    }

    @Test
    void actualizarTecnico_notFound_returnsNull() {
        when(tecnicoRepository.existsById(1L)).thenReturn(false);

        Tecnico result = tecnicoService.actualizarTecnico(1L, tecnico);

        assertNull(result);
    }

    @Test
    void eliminarTecnico_successful() {
        when(tecnicoRepository.existsById(1L)).thenReturn(true);
        doNothing().when(tecnicoRepository).deleteById(1L);

        assertDoesNotThrow(() -> tecnicoService.eliminarTecnico(1L));

        verify(tecnicoRepository, times(1)).deleteById(1L);
    }

    @Test
    void eliminarTecnico_notFound_doesNothing() {
        when(tecnicoRepository.existsById(1L)).thenReturn(false);

        assertDoesNotThrow(() -> tecnicoService.eliminarTecnico(1L));

        verify(tecnicoRepository, never()).deleteById(any(Long.class));
    }
}