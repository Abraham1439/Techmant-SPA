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
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.Collections;
import com.techmant.Gestion_resena.model.Resena;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.techmant.Gestion_resena.repository.ResenaRepository;
import com.techmant.Gestion_resena.webusuario.UsuarioCat;

@ExtendWith(MockitoExtension.class)
public class ResenaServiceTest {

 
    
    @Mock
    private ResenaRepository resenaRepository;

    @Mock
    private UsuarioCat usuarioCat;

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
    void agregarResena_usuarioExiste_devuelveResena() {
        when(usuarioCat.getUsuarioById(100L)).thenReturn(Map.of("id", 100L, "nombre", "Sebas"));
        when(resenaRepository.save(any(Resena.class))).thenReturn(resena);

        Resena resultado = resenaService.agregarResena(resena);

        assertNotNull(resultado);
        assertEquals(resena.getIdResena(), resultado.getIdResena());

        verify(usuarioCat).getUsuarioById(100L);
        verify(resenaRepository).save(any(Resena.class));
    }

    @Test
    void agregarResena_usuarioNoExiste_lanzaExcepcion() {
        when(usuarioCat.getUsuarioById(100L)).thenReturn(Collections.emptyMap());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            resenaService.agregarResena(resena);
        });

        assertEquals("Usuario no encontrado. No se puede guardar la reseña.", ex.getMessage());
        verify(usuarioCat).getUsuarioById(100L);
        verify(resenaRepository, never()).save(any());
    }

    @Test
    void getResenas_devuelveLista() {
        List<Resena> lista = Arrays.asList(resena);
        when(resenaRepository.findAll()).thenReturn(lista);

        List<Resena> resultado = resenaService.getResenas();

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        verify(resenaRepository).findAll();
    }

    @Test
    void getResenaById_existente_devuelveResena() {
        when(resenaRepository.findById(1L)).thenReturn(Optional.of(resena));

        Resena resultado = resenaService.getResenaById(1L);

        assertNotNull(resultado);
        assertEquals(resena.getIdResena(), resultado.getIdResena());
        verify(resenaRepository).findById(1L);
    }

    @Test
    void getResenaById_noExistente_lanzaExcepcion() {
        when(resenaRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException excepcion = assertThrows(RuntimeException.class, () -> {
            resenaService.getResenaById(99L);
        });

        assertEquals("Lo sentimos, la reseña no pudo ser encontrada.", excepcion.getMessage());
        verify(resenaRepository).findById(99L);
    }

    @Test
    void eliminarResena_existente_eliminaCorrectamente() {
        when(resenaRepository.existsById(1L)).thenReturn(true);
        doNothing().when(resenaRepository).deleteById(1L);

        assertDoesNotThrow(() -> resenaService.eliminarResena(1L));

        verify(resenaRepository).existsById(1L);
        verify(resenaRepository).deleteById(1L);
    }

    @Test
    void eliminarResena_noExistente_lanzaExcepcion() {
        when(resenaRepository.existsById(99L)).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            resenaService.eliminarResena(99L);
        });

        assertEquals("Resena no encontrada con el ID: 99", ex.getMessage());
        verify(resenaRepository).existsById(99L);
        verify(resenaRepository, never()).deleteById(anyLong());
    }
}