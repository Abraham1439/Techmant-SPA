package com.techmant.solicitud.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.techmant.solicitud.model.Solicitud;
import com.techmant.solicitud.repository.SolicitudRepository;

@ExtendWith(MockitoExtension.class)
public class SolicitudServiceTest {
    
    @InjectMocks
    private SolicitudService solicitudService;

    @Mock
    private SolicitudRepository solicitudRepository;

    private Solicitud solicitud;

        @BeforeEach
        void setUp() {
        solicitud = new Solicitud(1L, new Date(), "Comentario de prueba", 100000);
    }

    @Test
    void crearSolicitud_successfully() {
        when(solicitudRepository.save(any(Solicitud.class))).thenReturn(solicitud);

        Solicitud nuevaSolicitud = solicitudService.crearSolicitud(solicitud);

        assertNotNull(nuevaSolicitud);
        assertEquals("Comentario de prueba", nuevaSolicitud.getComentario());
        assertEquals(100000, nuevaSolicitud.getTotal());
        verify(solicitudRepository, times(1)).save(solicitud);
    }

    @Test
    void obtenerTodasSolicitudes_returnsList() {
        List<Solicitud> solicitudes = Arrays.asList(
                new Solicitud(1L, new Date(), "Comentario 1", 50000),
                new Solicitud(2L, new Date(), "Comentario 2", 75000)
        );

        when(solicitudRepository.findAll()).thenReturn(solicitudes);

        List<Solicitud> resultado = solicitudService.obtenerTodasSolicitudes();

        assertEquals(2, resultado.size());
        assertEquals("Comentario 1", resultado.get(0).getComentario());
        verify(solicitudRepository, times(1)).findAll();
    }

    @Test
    void obtenerSolicitudPorId_found() {
        when(solicitudRepository.findById(1L)).thenReturn(Optional.of(solicitud));

        Solicitud resultado = solicitudService.obtenerSolicitudPorId(1L);

        assertNotNull(resultado);
        assertEquals("Comentario de prueba", resultado.getComentario());
        verify(solicitudRepository, times(1)).findById(1L);
    }

    @Test
    void obtenerSolicitudPorId_notFound() {
        when(solicitudRepository.findById(99L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            solicitudService.obtenerSolicitudPorId(99L);
        });

        assertEquals("No value present", exception.getMessage());
    }

    @Test
    void actualizarSolicitud_successfully() {
        Solicitud solicitudActualizada = new Solicitud(1L, new Date(), "Comentario actualizado", 150000);

        when(solicitudRepository.existsById(1L)).thenReturn(true);
        when(solicitudRepository.save(any(Solicitud.class))).thenReturn(solicitudActualizada);

        Solicitud resultado = solicitudService.actualizarSolicitud(1L, solicitudActualizada);

        assertNotNull(resultado);
        assertEquals("Comentario actualizado", resultado.getComentario());
        assertEquals(150000, resultado.getTotal());
        verify(solicitudRepository, times(1)).save(solicitudActualizada);
    }

    @Test
    void actualizarSolicitud_notFound() {
        Solicitud solicitudActualizada = new Solicitud(1L, new Date(), "Comentario actualizado", 150000);

        when(solicitudRepository.existsById(1L)).thenReturn(false);

        Solicitud resultado = solicitudService.actualizarSolicitud(1L, solicitudActualizada);

        assertNull(resultado);
        verify(solicitudRepository, never()).save(any(Solicitud.class));
    }

    @Test
    void eliminarSolicitud_successfully() {
        when(solicitudRepository.existsById(1L)).thenReturn(true);

        solicitudService.eliminarSolicitud(1L);

        verify(solicitudRepository, times(1)).deleteById(1L);
    }

    @Test
    void eliminarSolicitud_notFound() {
        when(solicitudRepository.existsById(99L)).thenReturn(false);

        solicitudService.eliminarSolicitud(99L);

        verify(solicitudRepository, never()).deleteById(99L);
    }
}