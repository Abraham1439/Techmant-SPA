package com.techmant.categoria.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.techmant.categoria.model.Categoria;
import com.techmant.categoria.repository.CategoriaRepository;

//Con esto se crea un elemento simulado para poder hacer la prueba 
@ExtendWith(MockitoExtension.class)
public class CategoriaServiceTest {

    //creamos una copia del repository con el mock ya que se necesita el acceso 
    @Mock
    private CategoriaRepository repository;

    //Con esto creamos una copia de objetos simulados para poder ejecutar 
    @InjectMocks
    private CategoriaService service;

    //Empezamos a crear las pruebas unitarias 
    @Test
    void agregar_returnsAgregarCategoria() {
        //Se crea un objeto de ejemplo
        Categoria nuevaCategoria = new Categoria(1L, "Diagnóstico", "Evaluación inicial del dispositivo para detectar fallas");

        //Configurar el comportamiento del repositorio 
        when(repository.save(nuevaCategoria)).thenReturn(nuevaCategoria);

        //ejecutamo el metodo dle servicio que vamos a comprobar
        Categoria result = service.agregarCategoria(nuevaCategoria);

        //comparo que se devuelva el mismo objeto
        assertThat(result).isSameAs(nuevaCategoria);
    }
    //esta prueba unitaria es de agregar una categoria 
    



    //Prueba para ver todas las categorias 
    @Test
    void obtener_rereturnsListOfCategorias() {
        //creamos el objeto de ejemplo 
        List<Categoria> listarCategorias = Arrays.asList(new Categoria(1L, "Diagnóstico", "Evaluación inicial del dispositivo"));
        //configuro el servicio simulado (comportamiento)
        when(repository.findAll()).thenReturn(listarCategorias);

        List<Categoria> result = service.obtenCategorias();

        //comparo que se devuelva el mismo objeto
        assertThat(result).isEqualTo(listarCategorias);
    }


    
    //Prueba para ver todas las categorias obtenerCategoriaPorId
    @Test
    void obtenerPorId_returnsCategoria () {
        Categoria catIdCategoria = new Categoria(10L, "Diagnostico", "Evaluación inicial");
        
        //configuro el servicio simulado (comportamiento)
        when(repository.findById(10L)).thenReturn(java.util.Optional.of(catIdCategoria));

        Categoria result = service.obtenerCategoriaPorId(10L);

        //comparo que se devuelva el mismo objeto
        assertThat(result).isEqualTo(catIdCategoria);
    }


    //Prueba para eliminar una categoria 
    @Test 
    void eliminarPorId_returnsDeleteCategoria () {
        Long id = 5L;

        service.elminarCategoria(id);

        verify(repository).deleteById(id);

    }

}
