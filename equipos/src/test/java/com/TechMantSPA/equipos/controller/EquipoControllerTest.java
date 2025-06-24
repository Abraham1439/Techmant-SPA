package com.TechMantSPA.equipos.controller;

import com.TechMantSPA.equipos.client.UsuarioClient;
import com.TechMantSPA.equipos.dto.UsuarioDTO;
import com.TechMantSPA.equipos.model.Equipos;
import com.TechMantSPA.equipos.services.EquipoServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EquipoController.class)
class EquipoControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EquipoServices equipoServices;

    @MockBean
    private UsuarioClient usuarioClient;

    private Equipos equipo;
    private List<Equipos> listaEquipos;

    @BeforeEach
    void setUp() {
        Equipos equipo1 = new Equipos(1L, "Laptop", "HP", "12345", "Equipo funcional", 10L);
        Equipos equipo2 = new Equipos(2L, "PC", "Dell", "54321", "Equipo en reparación", 20L);
        listaEquipos = Arrays.asList(equipo1, equipo2);

        objectMapper = new ObjectMapper();
    }

    @Test
    void getAllEquipos_retornaListaCon200() throws Exception {
        when(equipoServices.getAllEquipos()).thenReturn(listaEquipos);

        mockMvc.perform(get("/api/v1/equipos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idEquipo").value(1L))
                .andExpect(jsonPath("$[0].tipoDeDispositivo").value("Laptop"));
    }

    @Test
    void getEquipoById_existente_retornaEquipo() throws Exception {
        Equipos equipo = new Equipos(1L, "Laptop", "HP", "12345", "Equipo funcional", 10L);
        when(equipoServices.getEquipoById(1L)).thenReturn(equipo);

        mockMvc.perform(get("/api/v1/equipos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idEquipo").value(1L))
                .andExpect(jsonPath("$.tipoDeDispositivo").value("Laptop"))
                .andExpect(jsonPath("$.marca").value("HP"));
    }

    @Test
    void getEquiposPorTipo_existente_retornaListaEquipos() throws Exception {
        List<Equipos> equipos = List.of(
                new Equipos(1L, "Laptop", "Dell", "ABC123", "Buen estado", 10L),
                new Equipos(2L, "Laptop", "HP", "XYZ789", "Nuevo", 11L));

        when(equipoServices.getEquiposPorTipo("Laptop")).thenReturn(equipos);

        mockMvc.perform(get("/api/v1/equipos/tipo/Laptop"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].marca").value("Dell"))
                .andExpect(jsonPath("$[1].marca").value("HP"));
    }

    @Test
    void getEquiposPorUsuario_existente_retornaListaEquipos() throws Exception {
        List<Equipos> equipos = List.of(
                new Equipos(1L, "Tablet", "Samsung", "TBL123", "Pantalla rota", 8L),
                new Equipos(2L, "Tablet", "Lenovo", "TBL456", "Funciona correctamente", 8L));

        when(equipoServices.getEquiposPorUsuario(8L)).thenReturn(equipos);

        mockMvc.perform(get("/api/v1/equipos/usuario/8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].marca").value("Samsung"))
                .andExpect(jsonPath("$[1].marca").value("Lenovo"));
    }

    @Test
    void getEquiposPorTipoYUsuario_existente_retornaListaEquipos() throws Exception {
        List<Equipos> equipos = List.of(
                new Equipos(1L, "Laptop", "Dell", "SN123", "Buen estado", 8L),
                new Equipos(2L, "Laptop", "HP", "SN456", "Funciona lento", 8L));

        when(equipoServices.getEquiposPorTipoYUsuario("Laptop", 8L)).thenReturn(equipos);

        mockMvc.perform(get("/api/v1/equipos/tipo/Laptop/usuario/8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].marca").value("Dell"))
                .andExpect(jsonPath("$[1].marca").value("HP"));
    }

    @Test
    void createEquipo_usuarioValido_creaEquipo() throws Exception {
        Equipos nuevoEquipo = new Equipos(null, "Impresora", "HP", "SN789", "Nueva impresora", 10L);
        Equipos equipoCreado = new Equipos(3L, "Impresora", "HP", "SN789", "Nueva impresora", 10L);

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setIdRol(2L); // Rol permitido

        when(usuarioClient.getUsuarioById(10L)).thenReturn(usuarioDTO);
        when(equipoServices.createEquipo(any(Equipos.class))).thenReturn(equipoCreado);

        mockMvc.perform(post("/api/v1/equipos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nuevoEquipo)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idEquipo").value(3L))
                .andExpect(jsonPath("$.tipoDeDispositivo").value("Impresora"));
    }

    @Test
    void updateEquipo_existingId_returnsUpdatedEquipo() throws Exception {
        Equipos equipoActualizado = new Equipos(1L, "Laptop", "Dell", "123ABC", "Equipo actualizado", 2L);

        when(equipoServices.updateEquipo(eq(1L), any(Equipos.class))).thenReturn(equipoActualizado);

        mockMvc.perform(put("/api/v1/equipos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(equipoActualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idEquipo").value(1L))
                .andExpect(jsonPath("$.tipoDeDispositivo").value("Laptop"))
                .andExpect(jsonPath("$.marca").value("Dell"))
                .andExpect(jsonPath("$.nroSerie").value("123ABC"))
                .andExpect(jsonPath("$.descripcion").value("Equipo actualizado"))
                .andExpect(jsonPath("$.idUsuario").value(2));
    }

    @Test
    void deleteEquipo_existingId_returnsNoContent() throws Exception {
        Long idEquipo = 1L;

        mockMvc.perform(delete("/api/v1/equipos/{id}", idEquipo))
                .andExpect(status().isNoContent());
    }

}
