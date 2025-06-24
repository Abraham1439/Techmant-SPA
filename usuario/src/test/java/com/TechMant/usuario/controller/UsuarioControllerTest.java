package com.TechMant.usuario.controller;

import com.TechMant.usuario.client.RolServiceClient;
import com.TechMant.usuario.dto.RolDTO;
import com.TechMant.usuario.model.Usuario;
import com.TechMant.usuario.service.UsuarioService;

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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @MockBean
    private RolServiceClient rolServiceClient;

    private Usuario usuario;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        usuario = new Usuario(1L, "Juan", "juan@test.com", "123456", 2);
    }

    @Test
    void getUserById_found() throws Exception {
        when(usuarioService.getUsuarioById(1L)).thenReturn(usuario);

        mockMvc.perform(get("/api/v1/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idUsuario").value(1L))
                .andExpect(jsonPath("$.nombre").value("Juan"));
    }

    @Test
    void getUserById_notFound() throws Exception {
        when(usuarioService.getUsuarioById(99L)).thenReturn(null);

        mockMvc.perform(get("/api/v1/usuarios/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllUsuarios_returnsList() throws Exception {
        List<Usuario> usuarios = Arrays.asList(
                new Usuario(1L, "Juan", "juan@test.com", "123456", 2),
                new Usuario(2L, "Ana", "ana@test.com", "abc123", 3));

        when(usuarioService.getAllUsuarios()).thenReturn(usuarios);

        mockMvc.perform(get("/api/v1/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idUsuario").value(1L))
                .andExpect(jsonPath("$[1].nombre").value("Ana"));
    }

    @Test
    void getAllUsuariosByRol_found() throws Exception {
        List<Usuario> usuarios = Arrays.asList(
                new Usuario(1L, "Juan", "juan@test.com", "123456", 2),
                new Usuario(2L, "Ana", "ana@test.com", "abc123", 2));

        when(usuarioService.getAllUsuariosByRol(2)).thenReturn(usuarios);

        mockMvc.perform(get("/api/v1/usuarios/rol/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idUsuario").value(1L))
                .andExpect(jsonPath("$[1].correo").value("ana@test.com"));
    }

    @Test
    void createUsuario_rolPermitidoYValido() throws Exception {
        // Simular que el rol existe en el microservicio de roles
        RolDTO rolDTO = new RolDTO(2L, "Cliente");
        when(rolServiceClient.getRolById(2)).thenReturn(rolDTO);

        when(usuarioService.createUsuario(any())).thenReturn(usuario);

        mockMvc.perform(post("/api/v1/usuarios?idRolSolicitante=1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Juan"))
                .andExpect(jsonPath("$.correo").value("juan@test.com"));
    }

    @Test
    void updateUsuario_successfully() throws Exception {
        Usuario actualizado = new Usuario(1L, "Juan Editado", "juan@test.com", "654321", 2);

        when(usuarioService.updateUsuario(1L, actualizado)).thenReturn(actualizado);

        mockMvc.perform(put("/api/v1/usuarios/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(actualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan Editado"))
                .andExpect(jsonPath("$.password").value("654321"));
    }

    @Test
    void deleteUsuario_successfully() throws Exception {
        mockMvc.perform(delete("/api/v1/usuarios/1"))
                .andExpect(status().isOk());
    }
}
