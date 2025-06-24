package com.techmant.servicio.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techmant.servicio.model.Servicio;
import com.techmant.servicio.service.ServicioService;

//Esto es para cargar el controlador a probar
@WebMvcTest(ServicioController.class)
public class ServicioControllerTest {

    @MockBean
    private ServicioService servicioService;

    @Autowired
    private MockMvc mockMvc;

    //crear las pruebas unitarias 
    @Test
    void listarServicios_deberiaRetornarListaServicios() {
        try {
            List<Servicio> lista = Arrays.asList(new Servicio(1L, "Diagnóstico", "Revisión inicial", 10000f, 5L), new Servicio(2L, "Formateo", "Formateo completo", 15000f, 6L));

            when(servicioService.obtenerServicios()).thenReturn(lista);

            mockMvc.perform(get("/api/v1/servicios")).andExpect(status().isOk()).andExpect(jsonPath("$.size()").value(2)).andExpect(jsonPath("$[0].nombreServicio").value("Diagnóstico")).andExpect(jsonPath("$[1].nombreServicio").value("Formateo"));
        
        }catch (Exception ex) {

        }
    }


    //Prueba para Crear nuevo servicio

    @Test
    void agregarServicio_deberiaCrearServicio_usandoObjectMapper() {
        try {
        Servicio nuevo = new Servicio(2L, "Formateo", "Reinstalación del sistema", 15000f, 5L);
        Servicio guardado = new Servicio(1L, "Formateo", "Reinstalación del sistema", 15000f, 5L);

        when(servicioService.saveServicio(nuevo)).thenReturn(guardado);

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post("/api/v1/servicios").contentType("application/json").content(objectMapper.writeValueAsString(nuevo))).andExpect(status().isCreated()).andExpect(jsonPath("$.idServicio").value(1)).andExpect(jsonPath("$.nombreServicio").value("Formateo"));
        } catch (Exception ex) {

        }
    }


    //Obtener por ID
    @Test
    void obtenerServicioPorId_deberiaRetornarServicio() {
        try {
        Servicio servicio = new Servicio(1L, "Diagnóstico", "Revisión general", 10000f, 5L);
        
        when(servicioService.obtenerServicioPorId(1L)).thenReturn(servicio);

        mockMvc.perform(get("/api/v1/servicios/1")).andExpect(status().isOk()).andExpect(jsonPath("$.nombreServicio").value("Diagnóstico")).andExpect(jsonPath("$.precio").value(10000f));
    }catch (Exception ex) {

        }
    }


    //Prueba para modificar un Servicio
    @Test
void modificarServicio_deberiaActualizarServicio_usandoObjectMapper() {
    try {
        // Datos originales y modificados
        Servicio servicioExistente = new Servicio(1L, "Formateo", "Sistema viejo", 15000f, 5L);
        Servicio servicioActualizado = new Servicio(1L, "Formateo Avanzado", "Sistema actualizado con drivers", 18000f, 5L);

        when(servicioService.obtenerServicioPorId(1L)).thenReturn(servicioExistente);
        when(servicioService.saveServicio(any(Servicio.class))).thenReturn(servicioActualizado);

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(put("/api/v1/servicios/1").contentType("application/json").content(objectMapper.writeValueAsString(servicioActualizado))).andExpect(status().isOk()).andExpect(jsonPath("$.nombreServicio").value("Formateo Avanzado")).andExpect(jsonPath("$.descripcion").value("Sistema actualizado con drivers")).andExpect(jsonPath("$.precio").value(18000f));
        }catch (Exception ex) {

        }
    }


    //Prueba para modificar un Servicio eliminar un Servicio por ID
    @Test
    void eliminarServicioPorId_deberiaRetornarNoContent() {
        try {
        // No es necesario mockear eliminación si no lanza excepción
        mockMvc.perform(delete("/api/v1/servicios/1")).andExpect(status().isNoContent()); // 204
        }catch (Exception ex) {

        }
    }

}
