package com.TechMant.rol.controller;

import com.TechMant.rol.model.Rol;
import com.TechMant.rol.service.RolService;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RolController.class)
public class RolControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RolService rolService;

    private Rol rol;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        rol = new Rol(1L, "Administrador");
    }

    @Test
    void getAllRoles_returnsList() throws Exception {
        List<Rol> roles = Arrays.asList(
                new Rol(1L, "Administrador"),
                new Rol(2L, "Cliente")
        );

        when(rolService.getAllRoles()).thenReturn(roles);

        mockMvc.perform(get("/api/v1/rol"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idRol").value(1L))
                .andExpect(jsonPath("$[1].nombreRol").value("Cliente"));
    }

    @Test
    void getRolById_found() throws Exception {
        when(rolService.getRolById(1L)).thenReturn(rol);

        mockMvc.perform(get("/api/v1/rol/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idRol").value(1L))
                .andExpect(jsonPath("$.nombreRol").value("Administrador"));
    }

    @Test
    void getRolById_notFound() throws Exception {
        when(rolService.getRolById(99L)).thenThrow(new RuntimeException("Rol no encontrado"));

        mockMvc.perform(get("/api/v1/rol/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createRol_success() throws Exception {
        when(rolService.createRol(any(Rol.class))).thenReturn(rol);

        mockMvc.perform(post("/api/v1/rol")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rol)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreRol").value("Administrador"));
    }

    @Test
    void updateRol_success() throws Exception {
        Rol updatedRol = new Rol(1L, "Super Admin");

        when(rolService.updateRol(any(Long.class), any(Rol.class))).thenReturn(updatedRol);

        mockMvc.perform(put("/api/v1/rol/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedRol)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreRol").value("Super Admin"));
    }

    @Test
    void deleteRol_success() throws Exception {
        mockMvc.perform(delete("/api/v1/rol/1"))
                .andExpect(status().isNoContent());
    }
}

