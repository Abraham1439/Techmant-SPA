package com.techmant.agendamiento.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
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

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    @Test
    void listarAgendamientos_deberiaRetornarLista() throws Exception {

        //formato de la fecha
        Date fecha = sdf.parse("09/07/2025");
        List<Agendamiento> lista = Arrays.asList(new Agendamiento(1L, "Pendiente", fecha, "Revisión de equipo", 1L));

        when(agendamientoService.getAgendamientos()).thenReturn(lista);

        mockMvc.perform(get("/api/v1/agendamiento")).andExpect(status().isOk()).andExpect(jsonPath("$[0].estado").value("Pendiente"));
    }

    //Retornar una lista vacia
    @Test
    void listarAgendamientos_listaVacia_deberiaRetornar204() throws Exception {
        when(agendamientoService.getAgendamientos()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/agendamiento")).andExpect(status().isNoContent());
    }



    //Prueba para para buscar por ID
    @Test
    void buscarAgendaPorId_deberiaRetornarAgendamiento() throws Exception {
        Date fecha = sdf.parse("09/07/2025");
        Agendamiento agendamiento = new Agendamiento(1L, "Completado", fecha, "Entregado", 2L);

        when(agendamientoService.getAgendamientoById(1L)).thenReturn(agendamiento);

        mockMvc.perform(get("/api/v1/agendamiento/1")).andExpect(status().isOk()).andExpect(jsonPath("$.estado").value("Completado"));
    }



    //Prueba para para agregar un agendamiento
    @Test
    void agregarAgendamiento_deberiaCrearAgendamiento() throws Exception {
        
        Date fecha = sdf.parse("09/07/2025");
        Agendamiento nuevo = new Agendamiento(null, "Pendiente", fecha, "Cliente solicita revisión", 3L);
        Agendamiento guardado = new Agendamiento(1L, "Pendiente", fecha, "Cliente solicita revisión", 3L);

        when(agendamientoService.agregarAgendamiento(any(Agendamiento.class))).thenReturn(guardado);

        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(sdf);

        mockMvc.perform(post("/api/v1/agendamiento").contentType("application/json").content(mapper.writeValueAsString(nuevo))).andExpect(status().isCreated())
            .andExpect(jsonPath("$.estado").value("Pendiente"))
            .andExpect(jsonPath("$.observaciones").value("Cliente solicita revisión"));
    }


    //Retornar error si el ID del ususario no existe 
    @Test
    void agregarAgendamiento_usuarioNoExiste_deberiaRetornarError404() throws Exception {
        Date fecha = sdf.parse("09/07/2025");
        Agendamiento nuevo = new Agendamiento(null, "Pendiente", fecha, "Cliente nuevo", 999L);

        when(agendamientoService.agregarAgendamiento(any(Agendamiento.class)))
            .thenThrow(new RuntimeException("Usuario no encontrada. No se puede guardar la agenda"));

        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(sdf);

        mockMvc.perform(post("/api/v1/agendamiento").contentType("application/json").content(mapper.writeValueAsString(nuevo)))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$").value("Usuario no encontrada. No se puede guardar la agenda"));
    }


    //Prueba para eliminar Asignaciones
    @Test
    void eliminarAgendamiento_deberiaEliminarConExito() throws Exception {
        doNothing().when(agendamientoService).eliminarAgendamiento(1L);

        mockMvc.perform(delete("/api/v1/agendamiento/1")).andExpect(status().isNoContent());
    }

    
    //Prueba para Actualizar Asignaciones
    @Test
    void actualizarAgendamiento_deberiaActualizarYRetornar() throws Exception {
        Date fecha = sdf.parse("09/07/2025");
        Agendamiento agendamientoExistente = new Agendamiento(1L, "Pendiente", fecha, "Primera revisión", 5L);
        Agendamiento agendamientoActualizado = new Agendamiento(1L, "Confirmado", fecha, "Cliente confirmó la cita", 5L);

        when(agendamientoService.getAgendamientoById(1L)).thenReturn(agendamientoExistente);
        when(agendamientoService.agregarAgendamiento(any(Agendamiento.class))).thenReturn(agendamientoActualizado);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(sdf);

        mockMvc.perform(put("/api/v1/agendamiento/1").contentType("application/json").content(objectMapper.writeValueAsString(agendamientoActualizado)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.estado").value("Confirmado"))
            .andExpect(jsonPath("$.observaciones").value("Cliente confirmó la cita"))
            .andExpect(jsonPath("$.idUsuario").value(5));
    }

}