package com.techmant.Gestion_resena.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.techmant.Gestion_resena.model.Resena;
import com.techmant.Gestion_resena.repository.ResenaRepository;

@ExtendWith(MockitoExtension.class)
public class ResenaServiceTest {

      @Mock
    private ResenaRepository resenaRepository;

    @InjectMocks
    private ResenaService resenaService;

    private Resena resena;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        Date fecha = new SimpleDateFormat("dd/MM/yyyy").parse("24/06/2025");
        resena = new Resena(1L, "Buen servicio", 5, fecha, 100L);
    }

    @Test
    void crearResena_devuelveResenaGuardada() {
        when(resenaRepository.save(any(Resena.class))).thenReturn(resena);

        Resena resultado = resenaService.crearResena(resena);

        assertNotNull(resultado);
        assertEquals(resena.getIdResena(), resultado.getIdResena());
        verify(resenaRepository).save(resena);
    }

    @Test
    void obtenerTodasLasResenas_devuelveLista() {
        List<Resena> lista = Arrays.asList(resena);
        when(resenaRepository.findAll()).thenReturn(lista);

        List<Resena> resultado = resenaService.obtenerTodasLasResenas();

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        verify(resenaRepository).findAll();
    }

    @Test
    void obtenerResenaPorId_existente_devuelveResena() {
        when(resenaRepository.findById(1L)).thenReturn(Optional.of(resena));

        Resena resultado = resenaService.obtenerResenaPorId(1L);

        assertNotNull(resultado);
        assertEquals(resena.getIdResena(), resultado.getIdResena());
    }

    @Test
    void obtenerResenaPorId_noExistente_lanzaExcepcion() {
        when(resenaRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException excepcion = assertThrows(RuntimeException.class, () -> {
            resenaService.obtenerResenaPorId(99L);
        });

        assertEquals("Resena no encontrada con el ID: 99", excepcion.getMessage());
    }

    @Test
    void actualizarResena_existente_devuelveResenaActualizada() {
        when(resenaRepository.existsById(1L)).thenReturn(true);
        when(resenaRepository.save(any(Resena.class))).thenReturn(resena);

        Date nuevaFecha = new Date();
        Resena nueva = new Resena(null, "Actualizada", 4, nuevaFecha, 100L);

        Resena resultado = resenaService.actualizarResena(1L, nueva);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdResena());
        verify(resenaRepository).save(nueva);
    }

    @Test
    void actualizarResena_noExistente_lanzaExcepcion() {
        when(resenaRepository.existsById(99L)).thenReturn(false);

        Resena nueva = new Resena(null, "Intento fallido", 3, new Date(), 101L);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            resenaService.actualizarResena(99L, nueva);
        });

        assertEquals("Resena no encontrada con el ID: 99", ex.getMessage());
        verify(resenaRepository, never()).save(any());
    }

    @Test
    void eliminarResena_existente_eliminaCorrectamente() {
        when(resenaRepository.existsById(1L)).thenReturn(true);
        doNothing().when(resenaRepository).deleteById(1L);

        resenaService.eliminarResena(1L);

        verify(resenaRepository).deleteById(1L);
    }

    @Test
    void eliminarResena_noExistente_lanzaExcepcion() {
        when(resenaRepository.existsById(99L)).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            resenaService.eliminarResena(99L);
        });

        assertEquals("Resena no encontrada con el ID: 99", ex.getMessage());
        verify(resenaRepository, never()).deleteById(anyLong());
    }
}
