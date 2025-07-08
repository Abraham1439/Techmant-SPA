package com.techmant.Gestion_tecnico.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.eq;

import java.util.Arrays;
import java.util.List;

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
        tecnico = new Tecnico(1L, "Juan Pérez", "Reparación de teléfonos móviles");
    }

    @Test
    void crearTecnico_successfully() throws Exception {
        when(tecnicoService.crearTecnico(any())).thenReturn(tecnico);

        mockMvc.perform(post("/api/v1/tecnicos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tecnico)))
                .andExpect(status().isCreated()) // Cambiar a 201 si el controlador lo retorna
                .andExpect(jsonPath("$.nombre").value("Juan Pérez"))
                .andExpect(jsonPath("$.especialidad").value("Reparación de teléfonos móviles"));
    }

    @Test
    void obtenerTodosLosTecnicos_returnsList() throws Exception {
        List<Tecnico> tecnicos = Arrays.asList(
                new Tecnico(1L, "Juan Pérez", "Reparación de teléfonos móviles"),
                new Tecnico(2L, "Ana Gómez", "Soporte técnico de tablets"));

        when(tecnicoService.obtenerTodosLosTecnicos()).thenReturn(tecnicos);

        mockMvc.perform(get("/api/v1/tecnicos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idTecnico").value(1L))
                .andExpect(jsonPath("$[1].nombre").value("Ana Gómez"));
    }

    @Test
    void obtenerTecnicoPorId_found() throws Exception {
        when(tecnicoService.obtenerTecnicoPorId(1L)).thenReturn(tecnico);

        mockMvc.perform(get("/api/v1/tecnicos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idTecnico").value(1L))
                .andExpect(jsonPath("$.nombre").value("Juan Pérez"));
    }

    @Test
    void actualizarTecnico_successfully() throws Exception {
        Tecnico actualizado = new Tecnico(1L, "Juan Pérez Actualizado", "Soporte técnico de laptops");

        when(tecnicoService.actualizarTecnico(eq(1L), any())).thenReturn(actualizado);

        mockMvc.perform(put("/api/v1/tecnicos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(actualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan Pérez Actualizado"))
                .andExpect(jsonPath("$.especialidad").value("Soporte técnico de laptops"));
    }

    @Test
    void eliminarTecnico_successfully() throws Exception {
        when(tecnicoService.obtenerTecnicoPorId(1L)).thenReturn(tecnico);

        mockMvc.perform(delete("/api/v1/tecnicos/1"))
                .andExpect(status().isNoContent()); // Cambiado a 204
    }

    @Test
    void eliminarTecnico_notFound() throws Exception {
        when(tecnicoService.obtenerTecnicoPorId(1L)).thenReturn(null);

        mockMvc.perform(delete("/api/v1/tecnicos/1"))
                .andExpect(status().isNotFound()); // Verifica que se devuelve 404
    }
}