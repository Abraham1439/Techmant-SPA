package com.techmant.solicitud.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.http.MediaType;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import java.util.Date;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techmant.solicitud.model.Solicitud;
import com.techmant.solicitud.service.SolicitudService;

@WebMvcTest(SolicitudController.class)
public class SolicitudControllerTest {
     
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SolicitudService solicitudService;

    private Solicitud solicitud;
    private ObjectMapper objectMapper;

  
    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        solicitud = new Solicitud(1L, new Date(), "Comentario de prueba", 100000);
    }

    @Test
    void crearSolicitud_successfully() throws Exception {
        when(solicitudService.crearSolicitud(any(Solicitud.class))).thenReturn(solicitud);

        mockMvc.perform(post("/api/v1/solicitudes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(solicitud)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.comentario").value("Comentario de prueba"))
                .andExpect(jsonPath("$.total").value(100000));
    }

    @Test
    void obtenerTodasLasSolicitudes_returnsList() throws Exception {
        List<Solicitud> solicitudes = Arrays.asList(
                new Solicitud(1L, new Date(), "Comentario 1", 50000),
                new Solicitud(2L, new Date(), "Comentario 2", 75000)
        );

        when(solicitudService.obtenerTodasSolicitudes()).thenReturn(solicitudes);

        mockMvc.perform(get("/api/v1/solicitudes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].comentario").value("Comentario 1"))
                .andExpect(jsonPath("$[0].total").value(50000))
                .andExpect(jsonPath("$[1].comentario").value("Comentario 2"))
                .andExpect(jsonPath("$[1].total").value(75000));
    }

    @Test
    void obtenerSolicitudPorId_found() throws Exception {
        when(solicitudService.obtenerSolicitudPorId(1L)).thenReturn(solicitud);

        mockMvc.perform(get("/api/v1/solicitudes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idSolicitud").value(1))
                .andExpect(jsonPath("$.comentario").value("Comentario de prueba"))
                .andExpect(jsonPath("$.total").value(100000));
    }

    @Test
    void actualizarSolicitud_successfully() throws Exception {
        Solicitud solicitudActualizada = new Solicitud(1L, new Date(), "Comentario actualizado", 150000);

        when(solicitudService.actualizarSolicitud(eq(1L), any(Solicitud.class))).thenReturn(solicitudActualizada);

        mockMvc.perform(put("/api/v1/solicitudes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(solicitudActualizada)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.comentario").value("Comentario actualizado"))
                .andExpect(jsonPath("$.total").value(150000));
    }

    @Test
    void eliminarSolicitud_successfully() throws Exception {
        doNothing().when(solicitudService).eliminarSolicitud(1L);

        mockMvc.perform(delete("/api/v1/solicitudes/1"))
                .andExpect(status().isNoContent());
    }
}