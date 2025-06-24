package com.TechMant.rol.service;

import com.TechMant.rol.model.Rol;
import com.TechMant.rol.repository.RolRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RolServiceTest {

    @Mock
    private RolRepository rolRepository;

    @InjectMocks
    private RolService rolService;

    private Rol rol;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        rol = new Rol(1L, "Administrador");
    }

    @Test
    void getAllRoles_returnsList() {
        List<Rol> roles = Arrays.asList(
                new Rol(1L, "Administrador"),
                new Rol(2L, "Cliente")
        );

        when(rolRepository.findAll()).thenReturn(roles);

        List<Rol> result = rolService.getAllRoles();

        assertEquals(2, result.size());
        verify(rolRepository, times(1)).findAll();
    }

    @Test
    void getRolById_found() {
        when(rolRepository.findById(1L)).thenReturn(Optional.of(rol));

        Rol result = rolService.getRolById(1L);

        assertNotNull(result);
        assertEquals("Administrador", result.getNombreRol());
        verify(rolRepository, times(1)).findById(1L);
    }

    @Test
    void getRolById_notFound() {
        when(rolRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            rolService.getRolById(99L);
        });

        assertEquals("Rol no encontrado", exception.getMessage());
        verify(rolRepository, times(1)).findById(99L);
    }

    @Test
    void createRol_success() {
        when(rolRepository.save(rol)).thenReturn(rol);

        Rol result = rolService.createRol(rol);

        assertEquals("Administrador", result.getNombreRol());
        verify(rolRepository, times(1)).save(rol);
    }

    @Test
    void updateRol_success() {
        Rol updatedRol = new Rol(1L, "Super Admin");

        when(rolRepository.findById(1L)).thenReturn(Optional.of(rol));
        when(rolRepository.save(any(Rol.class))).thenReturn(updatedRol);

        Rol result = rolService.updateRol(1L, updatedRol);

        assertEquals("Super Admin", result.getNombreRol());
        verify(rolRepository, times(1)).findById(1L);
        verify(rolRepository, times(1)).save(any(Rol.class));
    }

    @Test
    void updateRol_notFound() {
        Rol updatedRol = new Rol(99L, "No Existe");

        when(rolRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            rolService.updateRol(99L, updatedRol);
        });

        assertEquals("Rol no encontrado", exception.getMessage());
        verify(rolRepository, times(1)).findById(99L);
        verify(rolRepository, never()).save(any(Rol.class));
    }

    @Test
    void deleteRol_success() {
        when(rolRepository.findById(1L)).thenReturn(Optional.of(rol));
        doNothing().when(rolRepository).deleteById(1L);

        rolService.deleteRol(1L);

        verify(rolRepository, times(1)).deleteById(1L);
    }
}

