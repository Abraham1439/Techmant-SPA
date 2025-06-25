package com.TechMantSPA.equipos.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.TechMantSPA.equipos.model.Equipos;
import com.TechMantSPA.equipos.repository.EquiposRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
@ExtendWith(MockitoExtension.class)
public class EquipoServicesTest {

    @Mock
    private EquiposRepository equipoRepository;

    @InjectMocks
    private EquipoServices equipoServices;

    private Equipos equipo1;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        equipo1 = new Equipos(1L, "Laptop", "Dell", "12345", "Equipo de prueba", 2L);
    }

    @Test
    void getAllEquipos_returnsList() {
        when(equipoRepository.findAll()).thenReturn(Arrays.asList(equipo1));

        List<Equipos> equipos = equipoServices.getAllEquipos();

        assertNotNull(equipos);
        assertEquals(1, equipos.size());
        verify(equipoRepository).findAll();
    }

    @Test
    void getEquipoById_found() {
        when(equipoRepository.findById(1L)).thenReturn(Optional.of(equipo1));

        Equipos equipo = equipoServices.getEquipoById(1L);

        assertNotNull(equipo);
        assertEquals("Laptop", equipo.getTipoDeDispositivo());
        verify(equipoRepository).findById(1L);
    }

    @Test
    void getEquipoById_notFound_throwsException() {
        when(equipoRepository.findById(2L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            equipoServices.getEquipoById(2L);
        });

        assertEquals("Equipo no encontrado", exception.getMessage());
        verify(equipoRepository).findById(2L);
    }

    @Test
    void createEquipo_validEquipo_creaEquipo() {
        Equipos nuevoEquipo = new Equipos(null, "Laptop", "Dell", "123ABC", "Equipo para desarrollo", 1L);
        Equipos equipoGuardado = new Equipos(1L, "Laptop", "Dell", "123ABC", "Equipo para desarrollo", 1L);

        when(equipoRepository.save(nuevoEquipo)).thenReturn(equipoGuardado);

        Equipos resultado = equipoServices.createEquipo(nuevoEquipo);

        assertNotNull(resultado);
        assertEquals(equipoGuardado.getIdEquipo(), resultado.getIdEquipo());
        assertEquals(equipoGuardado.getMarca(), resultado.getMarca());
    }

    @Test
    void updateEquipo_existingId_updatesEquipo() {
        Long id = 1L;
        Equipos equipoExistente = new Equipos(id, "Laptop", "Dell", "123ABC", "Equipo viejo", 1L);
        Equipos equipoActualizado = new Equipos(id, "Laptop", "Dell", "123ABC", "Equipo actualizado", 1L);

        when(equipoRepository.findById(id)).thenReturn(Optional.of(equipoExistente));
        when(equipoRepository.save(any(Equipos.class))).thenReturn(equipoActualizado);

        Equipos resultado = equipoServices.updateEquipo(id, equipoActualizado);

        assertNotNull(resultado);
        assertEquals(equipoActualizado.getDescripcion(), resultado.getDescripcion());
    }

    @Test
    void updateEquipo_notExistingId_throwsException() {
        Long id = 1L;
        Equipos equipoActualizado = new Equipos(id, "Laptop", "Dell", "123ABC", "Equipo actualizado", 1L);

        when(equipoRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            equipoServices.updateEquipo(id, equipoActualizado);
        });

        assertEquals("Equipo no encontrado", thrown.getMessage());
    }

    @Test
void deleteEquipo_existingId_deletesEquipo() {
    Long equipoId = 1L;

    // Simula que el equipo con ID 1 sí existe
    when(equipoRepository.existsById(equipoId)).thenReturn(true);

    // Llamada al método que queremos testear
    equipoServices.deleteEquipo(equipoId);

    // Verifica que se llamó a deleteById
    verify(equipoRepository).deleteById(equipoId);
}

    @Test
    void deleteEquipo_notExistingId_doesNothing() {
        Long id = 2L;

        // mockear existsById en lugar de findById
        when(equipoRepository.existsById(id)).thenReturn(false);

        equipoServices.deleteEquipo(id);

        verify(equipoRepository, never()).deleteById(id);
    }

}
