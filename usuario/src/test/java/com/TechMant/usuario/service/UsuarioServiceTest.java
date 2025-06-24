package com.TechMant.usuario.service;

import static org.junit.jupiter.api.Assertions.*;
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

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

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
    void getAllUsuariosByRol_returnsList() {
        List<Usuario> usuarios = Arrays.asList(usuario);
        when(usuarioRepository.findByIdRol(2)).thenReturn(usuarios);

        List<Usuario> result = usuarioService.getAllUsuariosByRol(2);

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
    void getUsuarioById_notFound_throwsException() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            usuarioService.getUsuarioById(1L);
        });

        assertEquals("Usuario no encontrado", exception.getMessage());
    }

    @Test
    void createUsuario_savesAndReturns() {
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario result = usuarioService.createUsuario(usuario);

        assertNotNull(result);
        assertEquals("Juan", result.getNombre());
    }

    @Test
    void updateUsuario_successful() {
        Usuario updated = new Usuario(1L, "Juan Editado", "juan@test.com", "654321", 2);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(updated);

        Usuario result = usuarioService.updateUsuario(1L, updated);

        assertEquals("Juan Editado", result.getNombre());
        assertEquals("654321", result.getPassword());
    }

    @Test
    void updateUsuario_notFound_throwsException() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            usuarioService.updateUsuario(1L, usuario);
        });

        assertEquals("Usuario no encontrado", exception.getMessage());
    }

    @Test
    void deleteUsuario_successful() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        doNothing().when(usuarioRepository).delete(usuario);

        assertDoesNotThrow(() -> usuarioService.deleteUsuario(1L));

        verify(usuarioRepository, times(1)).delete(usuario);
    }

    @Test
    void deleteUsuario_notFound_throwsException() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            usuarioService.deleteUsuario(1L);
        });

        assertEquals("Usuario no encontrado", exception.getMessage());
    }
}

