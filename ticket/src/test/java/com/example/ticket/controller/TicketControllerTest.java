package com.example.ticket.controller;

import com.example.ticket.client.UsuarioClient;
import com.example.ticket.dto.UsuarioDTO;
import com.example.ticket.model.Ticket;
import com.example.ticket.services.TicketService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(TicketController.class)
public class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketService ticketService;

    @MockBean
    private UsuarioClient usuarioClient;

    private Ticket ticket;
    private UsuarioDTO usuario;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();

        ticket = new Ticket(
                1L,
                LocalDate.of(2025, 6, 23),
                "Descripción ejemplo",
                1L,
                16L);

        usuario = new UsuarioDTO();
        usuario.setIdRol(16L);
        usuario.setNombre("Juan");
        usuario.setCorreo("juan@test.com");
        usuario.setIdRol(2L); // Rol permitido
    }

    @Test
    void createTicket_usuarioValido_creaTicket() throws Exception {
        when(usuarioClient.getUsuarioById(16L)).thenReturn(usuario);
        when(ticketService.crearTicket(any(Ticket.class))).thenReturn(ticket);

        mockMvc.perform(post("/api/v1/tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ticket)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.descripcion").value("Descripción ejemplo"));
    }

    @Test
    void createTicket_usuarioSinPermiso_retornaForbidden() throws Exception {
        // Seteo usuario con rol no permitido
        usuario.setIdRol(3L);

        // Seteo el usuarioId en el ticket para que el controlador pueda usarlo
        ticket.setUsuarioId(16L);

        // Mockear llamada al cliente para obtener usuario
        when(usuarioClient.getUsuarioById(16L)).thenReturn(usuario);

        // Ejecutar petición POST para crear ticket
        mockMvc.perform(post("/api/v1/tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ticket)))
                .andExpect(status().isForbidden())
                .andExpect(content().string("Error: El usuario no tiene permiso para crear tickets."));
    }

    @Test
    void createTicket_usuarioNoExiste_retornaBadRequest() throws Exception {
        // Simula que no se encontró el usuario
        when(usuarioClient.getUsuarioById(16L)).thenReturn(null);

        mockMvc.perform(post("/api/v1/tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ticket)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error: El usuario con ID 16 no existe"));
    }

    @Test
    void createTicket_usuarioIdNulo_retornaBadRequest() throws Exception {
        // Simular ticket con usuarioId nulo
        ticket.setUsuarioId(null);

        mockMvc.perform(post("/api/v1/tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ticket)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error: El usuario con IDnull no puede ser nulo."));
    }

    @Test
    void obtenerTodosLosTickets_retornaLista() throws Exception {
        List<Ticket> lista = List.of(ticket); // Lista con un solo ticket simulado
        when(ticketService.obtenerTodosLosTickets()).thenReturn(lista);

        mockMvc.perform(get("/api/v1/tickets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id_ticket").value(1L))
                .andExpect(jsonPath("$[0].descripcion").value("Descripción ejemplo"));
    }

    @Test
    void obtenerTicketPorId_existe_retornaTicket() throws Exception {
        when(ticketService.obtenerTicketPorId(1L)).thenReturn(ticket);

        mockMvc.perform(get("/api/v1/tickets/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_ticket").value(1L))
                .andExpect(jsonPath("$.descripcion").value("Descripción ejemplo"));
    }

    @Test
    void obtenerTicketPorId_noExiste_retornaNotFound() throws Exception {
        when(ticketService.obtenerTicketPorId(99L))
                .thenThrow(new RuntimeException("Ticket no encontrado"));

        mockMvc.perform(get("/api/v1/tickets/99"))
                .andExpect(status().isNotFound());
    }

}
