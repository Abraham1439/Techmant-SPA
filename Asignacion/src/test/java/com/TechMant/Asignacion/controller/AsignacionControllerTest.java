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

    //crear las pruebas unitarias
    @Test
    void listarAsignaciones_deberiaRetornarListaDeAsignaciones() {
        try {
            List<Asignacion> lista = Arrays.asList(new Asignacion(1L, "Pedro González", "Caso de red", 1L));
            when(asignacionService.obtenerAsignaciones()).thenReturn(lista);

            mockMvc.perform(get("/api/v1/asignaciones")).andExpect(status().isOk()).andExpect(jsonPath("$[0].nombreAsignado").value("Pedro González")).andExpect(jsonPath("$[0].nombreCaso").value("Caso de red"));
        
        }catch (Exception ex) {

        }
    }



    //Prueba para para agregar Asignaciones
    @Test
    void agregarAsignacion_deberiaCrearAsignacion() {
        try {
            Asignacion nueva = new Asignacion(3L, "Juan Díaz", "Caso de software", 3L);

            when(asignacionService.saveAsignacion(any(Asignacion.class))).thenReturn(nueva);

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(nueva);

            mockMvc.perform(post("/api/v1/asignaciones").contentType("application/json").content(json)).andExpect(status().isCreated()).andExpect(jsonPath("$.nombreAsignado").value("Juan Díaz")).andExpect(jsonPath("$.nombreCaso").value("Caso de software"));
        }catch (Exception ex) {

        }
    }


    //Prueba para obtener Asignacion por ID
    @Test
    void obtenerAsignacionporId_deberiaRetornarAsignacionPorId() {
        try {
            Asignacion asignacion = new Asignacion(2L, "Lucía Rojas", "Caso de impresora", 2L);
            
            when(asignacionService.obtenerAsignacionporId(2L)).thenReturn(asignacion);

            mockMvc.perform(get("/api/v1/asignaciones/2")).andExpect(status().isOk()).andExpect(jsonPath("$.nombreAsignado").value("Lucía Rojas")).andExpect(jsonPath("$.nombreCaso").value("Caso de impresora"));
        
        }catch (Exception ex) {

        }
    }



    //Prueba para Actualizar Asignaciones
    @Test
    void modificarAsignacion_deberiaActualizarAsignacion() {
        try {
            // Datos originales y modificados
        Asignacion asignacionExistente = new Asignacion(1L, "Luis Rojas", "Caso X", 2L);
        Asignacion asignacionActualizada = new Asignacion(1L, "Luis Rojas", "Caso Y", 2L);

        when(asignacionService.obtenerAsignacionporId(1L)).thenReturn(asignacionExistente);
        when(asignacionService.saveAsignacion(any(Asignacion.class))).thenReturn(asignacionActualizada);

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(put("/api/v1/asignaciones/1").contentType("application/json").content(objectMapper.writeValueAsString(asignacionActualizada))).andExpect(status().isOk()).andExpect(jsonPath("$.nombreAsignado").value("Luis Rojas")).andExpect(jsonPath("$.nombreCaso").value("Caso Y")).andExpect(jsonPath("$.idTecnico").value(2));
        
        }catch (Exception ex) {

        }

        
    }




    //Prueba para eliminar Asignaciones
    @Test
    void eliminarAsignacionPorId_deberiaEliminarAsignacion()  {
        try {
            doNothing().when(asignacionService).eliminarAsignacionPorId(1L);

            mockMvc.perform(delete("/api/v1/asignaciones/1")).andExpect(status().isNoContent());

            verify(asignacionService).eliminarAsignacionPorId(1L);
        
        }catch (Exception ex) {

        }
    }


}
