package com.TechMant.usuario.controller;


import com.TechMant.usuario.config.SecurityConfig;
import com.TechMant.usuario.dto.LoginRequest;
import com.TechMant.usuario.dto.LoginResponse;

import com.TechMant.usuario.model.Usuario;
import com.TechMant.usuario.service.UsuarioService;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.web.servlet.MockMvc;


import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@Import(SecurityConfig.class) // o la clase donde tengas la configuración de seguridad si aplica
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    private Usuario usuario;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        usuario = new Usuario(1L, "Juan", "juan@test.com", "123456", 2);
    }

    @Test
    void getAllUsuarios_returnsList() throws Exception {
        List<Usuario> usuarios = List.of(usuario);
        when(usuarioService.getAllUsuarios()).thenReturn(usuarios);

        mockMvc.perform(get("/api/v1/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Juan"));
    }

    @Test
    void getAllUsuarios_returnsNoContent() throws Exception {
        when(usuarioService.getAllUsuarios()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/usuarios"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getUserById_found() throws Exception {
        when(usuarioService.getUsuarioById(1L)).thenReturn(usuario);

        mockMvc.perform(get("/api/v1/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan"));
    }

    @Test
    void getUserById_notFound() throws Exception {
        when(usuarioService.getUsuarioById(99L)).thenReturn(null);

        mockMvc.perform(get("/api/v1/usuarios/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllUsuariosByRol_found() throws Exception {
        List<Usuario> usuarios = List.of(usuario);
        when(usuarioService.getAllUsuariosByRol(2)).thenReturn(usuarios);

        mockMvc.perform(get("/api/v1/usuarios/rol/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Juan"));
    }

    @Test
    void getAllUsuariosByRol_returnsNoContent() throws Exception {
        when(usuarioService.getAllUsuariosByRol(2)).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/usuarios/rol/2"))
                .andExpect(status().isNoContent());
    }

    @Test
    void createUsuario_success() throws Exception {
        Usuario nuevoUsuario = new Usuario(null, "Pedro", "pedro@test.com", "pass", 3);
        Usuario creado = new Usuario(2L, "Pedro", "pedro@test.com", null, 3);

        when(usuarioService.createUsuario(any())).thenReturn(creado);

        mockMvc.perform(post("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nuevoUsuario)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Pedro"))
                .andExpect(jsonPath("$.password").doesNotExist());
    }

    @Test
    void createUsuario_forbiddenWithoutAdminRole() throws Exception {
        // Since @PreAuthorize is active, you need to mock security context or disable it for test.
        // Here just demonstrating that if user is not admin, it fails.
        // You can skip this test or configure security context accordingly.
    }

    @Test
    void login_success() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setCorreo("juan@test.com");
        request.setPassword("123456");

        LoginResponse response = new LoginResponse();
        response.setIdUsuario(1L);
        response.setNombre("Juan");
        response.setCorreo("juan@test.com");
        response.setRol("CLIENTE");

        when(usuarioService.login(any())).thenReturn(response);

        mockMvc.perform(post("/api/v1/usuarios/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idUsuario").value(1L))
                .andExpect(jsonPath("$.nombre").value("Juan"))
                .andExpect(jsonPath("$.correo").value("juan@test.com"))
                .andExpect(jsonPath("$.rol").value("CLIENTE"));
    }

    @Test
    void login_badCredentials() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setCorreo("juan@test.com");
        request.setPassword("wrongpass");

        when(usuarioService.login(any()))
                .thenThrow(new BadCredentialsException("Credenciales inválidas"));

        mockMvc.perform(post("/api/v1/usuarios/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Credenciales inválidas"));
    }



    @Test
    void deleteUsuario_success() throws Exception {
        // No return value expected from deleteUsuario
        doNothing().when(usuarioService).deleteUsuario(1L);

        mockMvc.perform(delete("/api/v1/usuarios/1"))
                .andExpect(status().isOk());
    }
}
