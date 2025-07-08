package com.TechMantSPA.equipos.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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
public class EquipoServiceTest {

    @Mock
    private EquiposRepository equiposRepository;

    @InjectMocks
    private EquipoServices equipoServices;

    private Equipos equipo;

    @BeforeEach
    void setUp() {
        equipo = new Equipos();
        equipo.setIdEquipo(1L);
        equipo.setTipoDeDispositivo("Laptop");
        equipo.setMarca("Dell");
        equipo.setNroSerie("ABC123");
        equipo.setDescripcion("Equipo de prueba");
        equipo.setIdDuenoEquipo(4L);
        equipo.setIdUsuarioRegistro(2L);
    }

    @Test
    void getAllEquipos_returnsList() {
        List<Equipos> equipos = Arrays.asList(equipo);
        when(equiposRepository.findAll()).thenReturn(equipos);

        List<Equipos> result = equipoServices.getAllEquipos();

        assertEquals(1, result.size());
        assertEquals("Laptop", result.get(0).getTipoDeDispositivo());
    }

    @Test
    void getEquipoById_found() {
        when(equiposRepository.findById(1L)).thenReturn(Optional.of(equipo));

        Equipos result = equipoServices.getEquipoById(1L);

        assertNotNull(result);
        assertEquals("Laptop", result.getTipoDeDispositivo());
    }

    @Test
    void getEquipoById_notFound() {
        when(equiposRepository.findById(2L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            equipoServices.getEquipoById(2L);
        });

        assertTrue(exception.getMessage().contains("Equipo no encontrado"));
    }

    @Test
    void createEquipo_success() {
        when(equiposRepository.save(any(Equipos.class))).thenReturn(equipo);

        Equipos result = equipoServices.createEquipo(equipo);

        assertNotNull(result);
        assertEquals("Laptop", result.getTipoDeDispositivo());
    }

    @Test
    void updateEquipo_success() {
        Equipos updated = new Equipos();
        updated.setTipoDeDispositivo("PC Escritorio");
        updated.setMarca("HP");

        when(equiposRepository.findById(1L)).thenReturn(Optional.of(equipo));
        when(equiposRepository.save(any(Equipos.class))).thenReturn(updated);

        Equipos result = equipoServices.updateEquipo(1L, updated);

        assertNotNull(result);
        assertEquals("PC Escritorio", result.getTipoDeDispositivo());
        assertEquals("HP", result.getMarca());
    }

    @Test
    void deleteEquipo_success() {
        Long equipoId = 1L;

        when(equiposRepository.existsById(equipoId)).thenReturn(true);

        equipoServices.deleteEquipo(equipoId);

        verify(equiposRepository, times(1)).deleteById(equipoId);
    }

    @Test
    void deleteEquipo_notFound() {
        Long equipoId = 99L;

        when(equiposRepository.existsById(equipoId)).thenReturn(false);

        equipoServices.deleteEquipo(equipoId);

        // deleteById nunca se debe invocar si no existe
        verify(equiposRepository, never()).deleteById(anyLong());
    }
}
