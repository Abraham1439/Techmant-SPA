package com.techmant.Modelo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.techmant.Modelo.model.Modelo;
import com.techmant.Modelo.repository.ModeloRepository;

@ExtendWith(MockitoExtension.class)
public class ModeloServiceTest {
    
    @Mock
    private ModeloRepository modeloRepository;

    @InjectMocks
    private ModeloService modeloService;

    private Modelo modelo;

    @BeforeEach
    void setUp() {
        modelo = new Modelo(1L, "Samsung", "SN123456789XYZ");
    }

    @Test
    void crearModelo_debeRetornarModeloGuardado() {
        when(modeloRepository.save(any(Modelo.class))).thenReturn(modelo);

        Modelo resultado = modeloService.crearModelo(modelo);

        assertNotNull(resultado);
        assertEquals("Samsung", resultado.getMarca());
        verify(modeloRepository, times(1)).save(modelo);
    }

    @Test
    void obtenerTodosLosModelos_debeRetornarLista() {
        List<Modelo> modelos = Arrays.asList(
                new Modelo(1L, "Samsung", "SN123456789XYZ"),
                new Modelo(2L, "LG", "LG987654321ABC")
        );

        when(modeloRepository.findAll()).thenReturn(modelos);

        List<Modelo> resultado = modeloService.obtenerTodosLosModelos();

        assertEquals(2, resultado.size());
        verify(modeloRepository, times(1)).findAll();
    }

    @Test
    void obtenerModeloPorId_existente() {
        when(modeloRepository.findById(1L)).thenReturn(Optional.of(modelo));

        Modelo resultado = modeloService.obtenerModeloPorId(1L);

        assertNotNull(resultado);
        assertEquals("Samsung", resultado.getMarca());
        verify(modeloRepository, times(1)).findById(1L);
    }

    @Test
    void obtenerModeloPorId_noExistente() {
        when(modeloRepository.findById(99L)).thenReturn(Optional.empty());

        Exception ex = assertThrows(RuntimeException.class, () -> modeloService.obtenerModeloPorId(99L));
        assertEquals("Modelo no encontrado con el ID: 99", ex.getMessage());
    }

    @Test
    void actualizarModelo_existente() {
        Modelo actualizado = new Modelo(1L, "Apple", "APL00000001");
        when(modeloRepository.existsById(1L)).thenReturn(true);
        when(modeloRepository.save(any(Modelo.class))).thenReturn(actualizado);

        Modelo resultado = modeloService.actualizarModelo(1L, actualizado);

        assertNotNull(resultado);
        assertEquals("Apple", resultado.getMarca());
        verify(modeloRepository).save(actualizado);
    }

    @Test
    void actualizarModelo_noExistente() {
        Modelo actualizado = new Modelo(null, "Apple", "APL00000001");
        when(modeloRepository.existsById(99L)).thenReturn(false);

        Exception ex = assertThrows(RuntimeException.class, () -> modeloService.actualizarModelo(99L, actualizado));
        assertEquals("Modelo no encontrado con el ID: 99", ex.getMessage());
    }

    @Test
    void eliminarModelo_existente() {
        when(modeloRepository.existsById(1L)).thenReturn(true);

        modeloService.eliminarModelo(1L);

        verify(modeloRepository).deleteById(1L);
    }

    @Test
    void eliminarModelo_noExistente() {
        when(modeloRepository.existsById(99L)).thenReturn(false);

        Exception ex = assertThrows(RuntimeException.class, () -> modeloService.eliminarModelo(99L));
        assertEquals("Modelo no encontrado con el ID: 99", ex.getMessage());
    }
}
