package com.techmant.Modelo.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.doNothing;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.techmant.Modelo.model.Modelo;
import com.techmant.Modelo.service.ModeloService;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ModeloController.class)
public class ModeloControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ModeloService modeloService;

    private Modelo modelo;

    @BeforeEach
    public void setup() {
        modelo = new Modelo(1L, "Samsung", "SN123456789XYZ");
    }

    @Test
    public void testCrearModelo() throws Exception {
        when(modeloService.crearModelo(any(Modelo.class))).thenReturn(modelo);

        mockMvc.perform(post("/api/v1/modelos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(modelo)))
                .andExpect(status().isCreated()) // Cambiado a 201
                .andExpect(jsonPath("$.marca").value("Samsung"))
                .andExpect(jsonPath("$.numeroDeSerie").value("SN123456789XYZ"));
    }

    @Test
    public void testObtenerTodosLosModelos() throws Exception {
        List<Modelo> modelos = Arrays.asList(
                new Modelo(1L, "Samsung", "SN123456789XYZ"),
                new Modelo(2L, "LG", "LG987654321ZYX")
        );

        when(modeloService.obtenerTodosLosModelos()).thenReturn(modelos);

        mockMvc.perform(get("/api/v1/modelos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].marca").value("Samsung"))
                .andExpect(jsonPath("$[1].marca").value("LG"));
    }

    @Test
    public void testObtenerModeloPorId() throws Exception {
        when(modeloService.obtenerModeloPorId(1L)).thenReturn(modelo);

        mockMvc.perform(get("/api/v1/modelos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.marca").value("Samsung"));
    }

    @Test
    public void testActualizarModelo() throws Exception {
        Modelo actualizado = new Modelo(1L, "Sony", "SONY111222333");
        when(modeloService.actualizarModelo(Mockito.eq(1L), any(Modelo.class))).thenReturn(actualizado);

        mockMvc.perform(put("/api/v1/modelos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(actualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.marca").value("Sony"))
                .andExpect(jsonPath("$.numeroDeSerie").value("SONY111222333"));
    }

    @Test
    public void testEliminarModelo() throws Exception {
        // Simular la eliminación del modelo
        doNothing().when(modeloService).eliminarModelo(1L);

        mockMvc.perform(delete("/api/v1/modelos/1"))
                .andExpect(status().isNoContent()); // Cambiado a 204
    }
}