package com.techmant.Gestion_tecnico.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techmant.Gestion_tecnico.model.Tecnico;
import com.techmant.Gestion_tecnico.service.TecnicoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;




@WebMvcTest(TecnicoController.class)
public class TecnicoControllerTest {
@Autowired
    private MockMvc mockMvc;

    @MockBean
    private TecnicoService tecnicoService;

    private Tecnico tecnico;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        tecnico = new Tecnico(1L, "Juan Pérez", "Electricidad");
    }

    @Test
    void crearTecnico_successfully() throws Exception {
        when(tecnicoService.crearTecnico(any())).thenReturn(tecnico);

        mockMvc.perform(post("/api/v1/tecnicos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tecnico)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan Pérez"))
                .andExpect(jsonPath("$.especialidad").value("Electricidad"));
    }

    @Test
    void obtenerTodosLosTecnicos_returnsList() throws Exception {
        List<Tecnico> tecnicos = Arrays.asList(
                new Tecnico(1L, "Juan Pérez", "Electricidad"),
                new Tecnico(2L, "Ana Gómez", "Fontanería"));

        when(tecnicoService.obtenerTodosLosTecnicos()).thenReturn(tecnicos);

        mockMvc.perform(get("/api/v1/tecnicos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].nombre").value("Ana Gómez"));
    }

    @Test
    void obtenerTecnicoPorId_found() throws Exception {
        when(tecnicoService.obtenerTecnicoPorId(1L)).thenReturn(tecnico);

        mockMvc.perform(get("/api/v1/tecnicos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Juan Pérez"));
    }


    

    @Test
    void actualizarTecnico_successfully() throws Exception {
        Tecnico actualizado = new Tecnico(1L, "Juan Pérez Actualizado", "Fontanería");

        when(tecnicoService.actualizarTecnico(1L, actualizado)).thenReturn(actualizado);

        mockMvc.perform(put("/api/v1/tecnicos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(actualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan Pérez Actualizado"))
                .andExpect(jsonPath("$.especialidad").value("Fontanería"));
    }

    @Test
    void eliminarTecnico_successfully() throws Exception {
        mockMvc.perform(delete("/api/v1/tecnicos/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Técnico con ID 1 eliminado correctamente."));
    }
}