package com.TechMant.Asignacion.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.TechMant.Asignacion.model.Asignacion;
import com.TechMant.Asignacion.service.AsignacionService;
import com.fasterxml.jackson.databind.ObjectMapper;

//Esto es para cargar el controlador a probar
@WebMvcTest(AsignacionController.class)
public class AsignacionControllerTest {

    @MockBean
    private AsignacionService asignacionService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    //crear las pruebas unitarias
    @Test
    void listarAsignaciones_deberiaRetornarLista() throws Exception {
        List<Asignacion> lista = Arrays.asList(new Asignacion(1L, "Pedro González", "Caso de red", 1L));
        when(asignacionService.obtenerAsignaciones()).thenReturn(lista);

        mockMvc.perform(get("/api/v1/asignaciones")).andExpect(status().isOk()).andExpect(jsonPath("$[0].nombreAsignado").value("Pedro González")).andExpect(jsonPath("$[0].nombreServicio").value("Caso de red"));
    }




    //Prueba para para agregar Asignaciones
    @Test
    void agregarAsignacion_deberiaCrearAsignacion() throws Exception {
        Asignacion nueva = new Asignacion(null, "Juan Díaz", "Caso de software", 3L);
        Asignacion guardada = new Asignacion(3L, "Juan Díaz", "Caso de software", 3L);

        when(asignacionService.saveAsignacion(any(Asignacion.class))).thenReturn(guardada);

        mockMvc.perform(post("/api/v1/asignaciones").contentType("application/json").content(objectMapper.writeValueAsString(nueva)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombreAsignado").value("Juan Díaz"))
                .andExpect(jsonPath("$.nombreServicio").value("Caso de software"));
    }



    //Prueba para obtener Asignacion por ID
    @Test
    void obtenerAsignacionporId_deberiaRetornarAsignacion() throws Exception {
        Asignacion asignacion = new Asignacion(2L, "Lucía Rojas", "Caso de impresora", 2L);
        when(asignacionService.obtenerAsignacionporId(2L)).thenReturn(asignacion);

        mockMvc.perform(get("/api/v1/asignaciones/2")).andExpect(status().isOk()).andExpect(jsonPath("$.nombreAsignado").value("Lucía Rojas")).andExpect(jsonPath("$.nombreServicio").value("Caso de impresora"));
    }



    //Prueba para Actualizar Asignaciones
    @Test
    void modificarAsignacion_deberiaActualizar() throws Exception {
        Asignacion existente = new Asignacion(1L, "Luis Rojas", "Caso X", 2L);
        Asignacion actualizado = new Asignacion(1L, "Luis Rojas", "Caso Y", 2L);

        when(asignacionService.obtenerAsignacionporId(1L)).thenReturn(existente);
        when(asignacionService.saveAsignacion(any(Asignacion.class))).thenReturn(actualizado);

        mockMvc.perform(put("/api/v1/asignaciones/1").contentType("application/json").content(objectMapper.writeValueAsString(actualizado))).andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreAsignado").value("Luis Rojas"))
                .andExpect(jsonPath("$.nombreServicio").value("Caso Y"))
                .andExpect(jsonPath("$.idTecnico").value(2));
    }




    //Prueba para eliminar Asignaciones
    @Test
    void eliminarAsignacionPorId_deberiaEliminarConExito() throws Exception {
        doNothing().when(asignacionService).eliminarAsignacionPorId(1L);

        mockMvc.perform(delete("/api/v1/asignaciones/1")).andExpect(status().isNoContent());

        verify(asignacionService).eliminarAsignacionPorId(1L);
    }


}
