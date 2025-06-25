package com.techmant.agendamiento.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techmant.agendamiento.model.Agendamiento;
import com.techmant.agendamiento.service.AgendamientoService;

//Esto es para cargar el controlador a probar
@WebMvcTest(AgendamientoController.class)
public class AgendamientoControllerTest {

    @MockBean
    private AgendamientoService agendamientoService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void listarAgendamientos_deberiaRetornarLista() {
        try {
            List<Agendamiento> lista = Arrays.asList(new Agendamiento(1L, "Pendiente", new Date(), "Revisión de equipo", 1L));

            when(agendamientoService.getAgendamientos()).thenReturn(lista);

            mockMvc.perform(get("/api/v1/agendamiento")).andExpect(status().isOk()).andExpect(jsonPath("$[0].estado").value("Pendiente"));
        }catch (Exception ex) {

        }
    }

    //Prueba para para buscar por ID
    @Test
    void buscarAgendaPorId_deberiaRetornarAgendamiento() {
        try {
            Agendamiento agendamiento = new Agendamiento(1L, "Completado", new Date(), "Entregado", 2L);
            when(agendamientoService.getAgendamientoById(1L)).thenReturn(agendamiento);

            mockMvc.perform(get("/api/v1/agendamiento/1")).andExpect(status().isOk()).andExpect(jsonPath("$.estado").value("Completado"));
        }catch (Exception ex) {

        }
    }

    //Prueba para para agregar Asignaciones
    @Test
    void agregarAgendamiento_deberiaCrearAgendamiento() {
        try {
        Agendamiento nuevo = new Agendamiento(1L, "Pendiente", new Date(), "Cliente solicita revisión", 3L);

        when(agendamientoService.agregarAgendamiento(any(Agendamiento.class))).thenReturn(nuevo);

        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        String json = mapper.writeValueAsString(nuevo);

        
        mockMvc.perform(post("/api/v1/agendamiento").contentType("application/json").content(json)).andExpect(status().isCreated()).andExpect(jsonPath("$.estado").value("Pendiente")).andExpect(jsonPath("$.observaciones").value("Cliente solicita revisión"));
        }catch (Exception ex) {
        
        }

    }


    //Prueba para eliminar Asignaciones
    @Test
    void eliminarAgendamiento_deberiaEliminarConExito() {
        try {
            mockMvc.perform(delete("/api/v1/agendamiento/1")).andExpect(status().isNoContent());

        }catch (Exception ex) {
        
        }
    }


    //Prueba para Actualizar Asignaciones
    @Test
    void actualizarAgendamiento_deberiaActualizarYRetornar() {
        try {
        
        Agendamiento agendamientoExistente = new Agendamiento(1L, "Pendiente", new Date(), "Primera revisión", 5L);
        Agendamiento agendamientoActualizado = new Agendamiento(1L, "Confirmado", new Date(), "Cliente confirmó la cita", 5L);

        when(agendamientoService.getAgendamientoById(1L)).thenReturn(agendamientoExistente);
        when(agendamientoService.agregarAgendamiento(any(Agendamiento.class))).thenReturn(agendamientoActualizado);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new java.text.SimpleDateFormat("yyyy-MM-dd"));

        mockMvc.perform(put("/api/v1/agendamiento/1").contentType("application/json").content(objectMapper.writeValueAsString(agendamientoActualizado))).andExpect(status().isOk()).andExpect(jsonPath("$.estado").value("Confirmado")).andExpect(jsonPath("$.observaciones").value("Cliente confirmó la cita")).andExpect(jsonPath("$.idSolicitud").value(5));
        }catch (Exception ex) {
        
        }
    }


}