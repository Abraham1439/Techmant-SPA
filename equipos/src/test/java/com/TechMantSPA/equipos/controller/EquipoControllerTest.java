package com.TechMantSPA.equipos.controller;

import com.TechMantSPA.equipos.client.UsuarioClient;
import com.TechMantSPA.equipos.dto.EquipoRequestDTO;
import com.TechMantSPA.equipos.dto.UsuarioDTO;
import com.TechMantSPA.equipos.model.Equipos;
import com.TechMantSPA.equipos.services.EquipoServiceTest;
import com.TechMantSPA.equipos.services.EquipoServices;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

@WebMvcTest(EquipoController.class)
public class EquipoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EquipoServices equipoServices;

    @MockBean
    private UsuarioClient usuarioClient;

    private Equipos equipo;
    private UsuarioDTO propietario;
    private UsuarioDTO usuarioRegistro;

    @BeforeEach
    void setUp() {
        equipo = new Equipos();
        equipo.setIdEquipo(1L);
        equipo.setTipoDeDispositivo("Laptop");
        equipo.setMarca("Dell");
        equipo.setNroSerie("ABC123");
        equipo.setDescripcion("Prueba");
        equipo.setIdDuenoEquipo(2L);
        equipo.setIdUsuarioRegistro(3L);

        propietario = new UsuarioDTO();
        propietario.setIdUsuario(2L);
        propietario.setIdRol(3L); // Cliente
        propietario.setNombre("Juan Propietario");
        propietario.setCorreo("propietario@test.com");

        usuarioRegistro = new UsuarioDTO();
        usuarioRegistro.setIdUsuario(3L);
        usuarioRegistro.setIdRol(1L); // Admin
        usuarioRegistro.setNombre("Maria Registro");
        usuarioRegistro.setCorreo("registro@test.com");
    }

    @Test
    void getAll_returnsList() throws Exception {
        when(equipoServices.getAllEquipos()).thenReturn(Arrays.asList(equipo));

        mockMvc.perform(get("/api/v1/equipos"))
                .andExpect(status().isOk());
    }

    @Test
    void getAll_noContent() throws Exception {
        when(equipoServices.getAllEquipos()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/equipos"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getEquipoById_found() throws Exception {
        when(equipoServices.getEquipoById(1L)).thenReturn(equipo);
        when(usuarioClient.getUsuarioById(2L)).thenReturn(propietario);
        when(usuarioClient.getUsuarioById(3L)).thenReturn(usuarioRegistro);

        mockMvc.perform(get("/api/v1/equipos/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getEquipoById_notFound() throws Exception {
        when(equipoServices.getEquipoById(1L)).thenReturn(null);

        mockMvc.perform(get("/api/v1/equipos/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getEquiposPorTipo_returnsList() throws Exception {
        when(equipoServices.getEquiposPorTipo("Laptop")).thenReturn(Arrays.asList(equipo));

        mockMvc.perform(get("/api/v1/equipos/tipo/Laptop"))
                .andExpect(status().isOk());
    }

    @Test
    void getEquiposPorUsuario_returnsList() throws Exception {
        when(equipoServices.getEquiposPorUsuario(3L)).thenReturn(Arrays.asList(equipo));

        mockMvc.perform(get("/api/v1/equipos/usuario/3"))
                .andExpect(status().isOk());
    }

    @Test
    void getEquiposPorTipoYUsuario_returnsList() throws Exception {
        when(equipoServices.getEquiposPorTipoYUsuario("Laptop", 3L)).thenReturn(Arrays.asList(equipo));

        mockMvc.perform(get("/api/v1/equipos/tipo/Laptop/usuario/3"))
                .andExpect(status().isOk());
    }

    @Test
void createEquipo_success() throws Exception {
    // Simular respuestas del cliente por correo
    when(usuarioClient.getUsuarioByCorreo("cliente@example.com")).thenReturn(propietario);
    when(usuarioClient.getUsuarioByCorreo("tecnico@example.com")).thenReturn(usuarioRegistro);
    when(equipoServices.createEquipo(Mockito.any())).thenReturn(equipo);

    // Crear el DTO que se enviará en el body
    EquipoRequestDTO equipoRequest = new EquipoRequestDTO();
    equipoRequest.setTipoDeDispositivo("Laptop");
    equipoRequest.setMarca("Dell");
    equipoRequest.setNroSerie("ABC123456");
    equipoRequest.setDescripcion("Laptop Dell XPS 15, 16GB RAM, 512GB SSD");
    equipoRequest.setCorreoDuenoEquipo("cliente@example.com");
    equipoRequest.setCorreoUsuarioRegistro("tecnico@example.com");

    mockMvc.perform(post("/api/v1/equipos")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(equipoRequest)))
            .andExpect(status().isCreated());
}

    @Test
    void createEquipo_propietarioInvalido() throws Exception {
        when(usuarioClient.getUsuarioById(2L)).thenReturn(null);

        mockMvc.perform(post("/api/v1/equipos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(equipo)))
                .andExpect(status().isBadRequest());
    }


    @Test
    void deleteEquipo_success() throws Exception {
        doNothing().when(equipoServices).deleteEquipo(1L);

        mockMvc.perform(delete("/api/v1/equipos/1"))
                .andExpect(status().isNoContent());
    }
}
