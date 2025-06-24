package com.techmant.servicio.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.techmant.servicio.model.Servicio;
import com.techmant.servicio.repository.ServicioRepository;
import com.techmant.servicio.webcategoria.CategoriaCat;

@ExtendWith(MockitoExtension.class)
public class ServicioServiceTest {

    //creamos una copia del repository con el mock ya que se necesita el acceso 
    @Mock
    private ServicioRepository servicioRepository;

    @Mock
    private CategoriaCat categoriaCat;

    //Con esto creamos una copia de objetos simulados para poder ejecutar 
    @InjectMocks
    private ServicioService servicioService;

    @Test
    void saveServicio_deberiaGuardarServicioSiCategoriaExiste() {

        Servicio nuevo = new Servicio(1L, "Formateo", "Reinstala SO", 15000, 5L);

        Map<String, Object> mockCategoria = new HashMap<>();
        mockCategoria.put("idCategoria", 5L);
        mockCategoria.put("nombreCategoria", "Software");

        when(categoriaCat.obtenerCategoriaPorId(5L)).thenReturn(mockCategoria);
        when(servicioRepository.save(nuevo)).thenReturn(nuevo);

        Servicio result = servicioService.saveServicio(nuevo);

        assertThat(result).isEqualTo(nuevo);
    }



    //Para cuando la cuando la categoría no existe :P
    @Test 
    void saveServicio_lanzaExcepcionSiCategoriaNoExiste() {
        Servicio nuevo = new Servicio(1L, "Formateo", "Reinstala SO", 15000, 4L);

        when(categoriaCat.obtenerCategoriaPorId(99L)).thenReturn(null);

        try {
        servicioService.saveServicio(nuevo);
        assert false : "Se esperaba una excepción";
        }catch (Exception ex) {

        }
    }

    //Prueba para Obtener servicios
    @Test
    void obtenerServicios_deberiaRetornarListaDeServicios() {
        Servicio s1 = new Servicio(1L, "Cambio de pantalla", "LCD dañada", 35000, 6L);
        Servicio s2 = new Servicio(2L, "Formateo", "Reinstala SO", 15000, 5L);

        when(servicioRepository.findAll()).thenReturn(List.of(s1, s2));

        List<Servicio> servicios = servicioService.obtenerServicios();

        assertThat(servicios).hasSize(2).contains(s1, s2);
    }


    //Prueba para obtener Servicios por ID
    @Test
    void obtenerServicioPorId_deberiaRetornarServicio() {
    Servicio servicio = new Servicio(1L, "Diagnóstico", "Revisión general", 25000, 5L);

    when(servicioRepository.findById(1L)).thenReturn(java.util.Optional.of(servicio));

    Servicio result = servicioService.obtenerServicioPorId(1L);

    assertThat(result).isEqualTo(servicio);
    }

    //Prueba para eliminar Servicio Por ID
    @Test
    void eliminarServicioPorId_deberiaEliminarServicio() {
    Servicio servicio = new Servicio(1L, "Diagnóstico", "Revisión", 25000, 5L);

    when(servicioRepository.findById(1L)).thenReturn(java.util.Optional.of(servicio));

    servicioService.eliminarServicioPorId(1L);

    verify(servicioRepository).deleteById(1L);
    }

}
