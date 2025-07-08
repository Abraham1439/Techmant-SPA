package com.TechMant.usuario.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.TechMant.usuario.model.Usuario;
import com.TechMant.usuario.repository.UsuarioRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario(1L, "Juan", "juan@test.com", "123456", 2);
    }

    @Test
    void getAllUsuarios_returnsList() {
        List<Usuario> usuarios = Arrays.asList(usuario);
        when(usuarioRepository.findAll()).thenReturn(usuarios);

        List<Usuario> result = usuarioService.getAllUsuarios();

        assertEquals(1, result.size());
        assertEquals("Juan", result.get(0).getNombre());
    }

    @Test
    void getUsuarioById_found() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Usuario result = usuarioService.getUsuarioById(1L);

        assertNotNull(result);
        assertEquals("Juan", result.getNombre());
    }

    @Test
    void getUsuarioById_notFound() {
        when(usuarioRepository.findById(2L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            usuarioService.getUsuarioById(2L);
        });

        assertTrue(exception.getMessage().contains("Usuario no encontrado"));
    }

    @Test
    void createUsuario_success() {
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario result = usuarioService.createUsuario(usuario);

        assertNotNull(result);
        assertEquals("Juan", result.getNombre());
    }

    @Test
    void updateUsuario_success() {
        Usuario updatedUsuario = new Usuario(1L, "Juan Actualizado", "juan@test.com", "654321", 2);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(updatedUsuario);

        Usuario result = usuarioService.updateUsuario(1L, updatedUsuario);

        assertNotNull(result);
        assertEquals("Juan Actualizado", result.getNombre());
    }

    @Test
    void deleteUsuario_success() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        assertDoesNotThrow(() -> usuarioService.deleteUsuario(1L));
    }

    @Test
    void deleteUsuario_notFound() {
        when(usuarioRepository.findById(2L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            usuarioService.deleteUsuario(2L);
        });

        assertTrue(exception.getMessage().contains("Usuario no encontrado"));
    }
}
