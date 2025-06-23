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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import com.fasterxml.jackson.databind.ObjectMapper;
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
    void agregarCategoria_returnsCreatedAndJson() {
        try {
            Categoria nuevaCategoria = new Categoria(1L, "Diagnostico", "Evaluación inicial del dispositivo");

            //simular que el servicio retorna la categoría guardada
            when(categoriaService.agregarCategoria(nuevaCategoria)).thenReturn(nuevaCategoria);

            //ejecutar el metodo del endpoint del microservicio a probar
            mockMvc.perform(post("api/v1/categoria").contentType("application/json").content(new ObjectMapper().writeValueAsString(nuevaCategoria))).andExpect(status().isCreated()).andExpect(jsonPath("$.idCategoria").value(1L)).andExpect(jsonPath("$.nombreCategoria").value("Mantenimiento"));
        }catch (Exception ex) {

        }
        
    }


    //prueba para buscar por su id 
    @Test 
    void listarCategoriasPorId_returnsOk() {
        try {
            Categoria categoria = new Categoria(2L, "Reparacion", "Reparación de hardware");

            when(categoriaService.obtenerCategoriaPorId(2L)).thenReturn(categoria);

            mockMvc.perform(get("api/v1/categoria/2")).andExpect(status().isOk()).andExpect(jsonPath("$.idCategoria").value(2L)).andExpect(jsonPath("$.nombreCategoria").value("Reparación"));
        }catch (Exception ex) {

        }
    }


    //Prueba para actualizar Por ID 
    @Test
    void modificarCategoria_returnsUpdatedCategoria() {
        try {
            Long id = 1L;

            // Objeto que ya existe en la BD (simulación)
            Categoria categoriaExistente = new Categoria(id, "Antigua", "Antigua descripción");
            // Objeto actualizado
            Categoria categoriaActualizada = new Categoria(id, "Nueva", "Nueva descripción");

            // Simulamos que al buscar por ID, se retorna la categoría existente
            when(categoriaService.obtenerCategoriaPorId(id)).thenReturn(categoriaExistente);
            // Simulamos que al guardar, se retorna la categoría actualizada
            when(categoriaService.agregarCategoria(categoriaExistente)).thenReturn(categoriaExistente);

            // Ejecutar la petición PUT
            mockMvc.perform(put("api/v1/categoria/{id}", id).contentType("application/json").content(new ObjectMapper().writeValueAsString(categoriaActualizada))).andExpect(status().isOk()).andExpect(jsonPath("$.nombreCategoria").value("Nueva")).andExpect(jsonPath("$.descripcion").value("Descripción nueva"));
        }catch(Exception ex) {

        }
    }


    //Prueba para eliminar una categoria por ID
    @Test
    void eliminarCategoria_returnsOK() {
        try {
            mockMvc.perform(delete("api/v1/categoria/5")).andExpect(status().isNotFound());
        }catch (Exception ex) {

        }
    }







}
