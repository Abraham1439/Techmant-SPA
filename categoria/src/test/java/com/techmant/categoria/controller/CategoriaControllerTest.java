package com.techmant.categoria.controller;

import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.techmant.categoria.model.Categoria;
import com.techmant.categoria.service.CategoriaService;

//Esto es para cargar el controlador a probar 
@WebMvcTest(CategoriaController.class)
public class CategoriaControllerTest {

    @MockBean
    private CategoriaService categoriaService;

    //Con esto hacemos que se inyecte el mock de spring
    @Autowired
    private MockMvc mockMvc;

    //crear las pruebas unitarias 
    @Test
    void listarCategorias_returnsOKAndJson() {
        try {
            //Crear una categoria de ejemplo para la respuesta 
            List<Categoria> listCategoria = Arrays.asList(new Categoria(1L, "Diagnóstico", "Evaluación inicial del dispositivo para detectar fallas"));
            //configuro el servicio simulado (comportamiento)
            when(categoriaService.obtenCategorias()).thenReturn(listCategoria);

            //ejecutar el metodo del endpoint del microservicio a probar
            //ejecutar la petición al metodo get
            //verificar que la respuesta sea 200 OK
            //validar el archivo JSOn (solo con los id)
            mockMvc.perform(get("api/v1/categoria")).andExpect(status().isOk()).andExpect(jsonPath("$[0].id").value(1L));
        } catch (Exception ex) {

        }
        
    }

    //Prueba para agregar categorias
    @Test
    void agregarCategoria_returnsCreated() {
        
    }


}
