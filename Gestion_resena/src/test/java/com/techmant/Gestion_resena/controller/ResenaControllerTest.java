package com.techmant.Gestion_resena.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*; 
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;    

import org.springframework.http.MediaType;  



import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techmant.Gestion_resena.model.Resena;
import com.techmant.Gestion_resena.service.ResenaService;

@WebMvcTest(ResenaController.class)
public class ResenaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ResenaService resenaService;

    private Resena resena;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() throws Exception {
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();

        Date fecha = new SimpleDateFormat("dd/MM/yyyy").parse("24/06/2025");

        resena = new Resena();
        resena.setIdResena(1L);
        resena.setComentario("Muy buen servicio");
        resena.setCalificacion(5);
        resena.setFechaCreacion(fecha);
        resena.setIdUsuario(100L);
    }

    @Test
    void agregarResena_exito() throws Exception {
        when(resenaService.agregarResena(any(Resena.class))).thenReturn(resena);

        mockMvc.perform(post("/api/v1/resena")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(resena)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/v1/resena/1"))
                .andExpect(jsonPath("$.comentario").value("Muy buen servicio"))
                .andExpect(jsonPath("$.calificacion").value(5));
    }

    @Test
    void agregarResena_usuarioNoValido_badRequest() throws Exception {
        when(resenaService.agregarResena(any(Resena.class)))
            .thenThrow(new RuntimeException("Usuario no encontrado. No se puede guardar la reseña."));

        mockMvc.perform(post("/api/v1/resena")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(resena)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Usuario no encontrado. No se puede guardar la reseña."));
    }

    @Test
    void listarResenas_exito() throws Exception {
        List<Resena> lista = Arrays.asList(resena);
        when(resenaService.getResenas()).thenReturn(lista);

        mockMvc.perform(get("/api/v1/resena"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idResena").value(1L))
                .andExpect(jsonPath("$[0].comentario").value("Muy buen servicio"));
    }

    @Test
    void buscarResenaPorId_existe() throws Exception {
        when(resenaService.getResenaById(1L)).thenReturn(resena);

        mockMvc.perform(get("/api/v1/resena/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idResena").value(1L))
                .andExpect(jsonPath("$.comentario").value("Muy buen servicio"));
    }

    @Test
    void buscarResenaPorId_noExiste_notFound() throws Exception {
        when(resenaService.getResenaById(99L)).thenThrow(new RuntimeException("Lo sentimos, la reseña no pudo ser encontrada."));

        mockMvc.perform(get("/api/v1/resena/99"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Lo sentimos, la reseña no pudo ser encontrada."));
    }

    @Test
    void actualizarResena_exito() throws Exception {
        Resena resenaActualizada = new Resena();
        resenaActualizada.setIdResena(1L);
        resenaActualizada.setComentario("Servicio actualizado");
        resenaActualizada.setCalificacion(4);
        resenaActualizada.setFechaCreacion(resena.getFechaCreacion());
        resenaActualizada.setIdUsuario(100L);

        when(resenaService.getResenaById(1L)).thenReturn(resena);
        when(resenaService.agregarResena(any(Resena.class))).thenReturn(resenaActualizada);

        mockMvc.perform(put("/api/v1/resena/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(resenaActualizada)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.comentario").value("Servicio actualizado"))
                .andExpect(jsonPath("$.calificacion").value(4));
    }

    @Test
    void actualizarResena_noExiste_notFound() throws Exception {
        when(resenaService.getResenaById(anyLong())).thenThrow(new RuntimeException("Reseña no encontrada."));

        Resena resenaParaActualizar = new Resena();
        resenaParaActualizar.setComentario("Actualización");

        mockMvc.perform(put("/api/v1/resena/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(resenaParaActualizar)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Reseña no encontrada."));
    }

    @Test
    void eliminarResena_existe_noContent() throws Exception {
        when(resenaService.getResenaById(1L)).thenReturn(resena);
        doNothing().when(resenaService).eliminarResena(1L);

        mockMvc.perform(delete("/api/v1/resena/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void eliminarResena_noExiste_notFound() throws Exception {
        when(resenaService.getResenaById(99L)).thenThrow(new RuntimeException("Reseña no encontrada."));

        mockMvc.perform(delete("/api/v1/resena/99"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Reseña no encontrada."));
    }
}