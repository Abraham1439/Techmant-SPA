package com.example.ticket.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.ticket.model.Ticket;
import com.example.ticket.repository.TicketRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketService ticketService;

    private Ticket ticket;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ticket = new Ticket(1L, LocalDate.of(2025, 6, 23), "Descripción ejemplo", 1L, 1L);
    }

    @Test
    void crearTicket_retornaTicketGuardado() {
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

        Ticket resultado = ticketService.crearTicket(ticket);

        assertNotNull(resultado);
        assertEquals(ticket.getId_ticket(), resultado.getId_ticket());
        verify(ticketRepository).save(ticket);
    }

    @Test
    void obtenerTodosLosTickets_retornaLista() {
        List<Ticket> listaTickets = Arrays.asList(ticket);
        when(ticketRepository.findAll()).thenReturn(listaTickets);

        List<Ticket> resultado = ticketService.obtenerTodosLosTickets();

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        verify(ticketRepository).findAll();
    }

    @Test
    void obtenerTicketPorId_existe_retornaTicket() {
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));

        Ticket resultado = ticketService.obtenerTicketPorId(1L);

        assertNotNull(resultado);
        assertEquals(ticket.getId_ticket(), resultado.getId_ticket());
    }

    @Test
    void obtenerTicketPorId_noExiste_lanzaExcepcion() {
        when(ticketRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            ticketService.obtenerTicketPorId(99L);
        });

        assertEquals("Ticket no encontrado", thrown.getMessage());
    }

    @Test
    void actualizarTicket_existente_retornaTicketActualizado() {
        when(ticketRepository.existsById(1L)).thenReturn(true);
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

        Ticket ticketActualizado = new Ticket(null, LocalDate.of(2025, 6, 24), "Nueva descripción", 1L, 1L);

        Ticket resultado = ticketService.actualizarTicket(1L, ticketActualizado);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId_ticket());
        verify(ticketRepository).save(ticketActualizado);
    }

    @Test
    void actualizarTicket_noExiste_retornaNull() {
        when(ticketRepository.existsById(99L)).thenReturn(false);

        Ticket ticketActualizado = new Ticket(null, LocalDate.of(2025, 6, 24), "Nueva descripción", 1L, 1L);

        Ticket resultado = ticketService.actualizarTicket(99L, ticketActualizado);

        assertNull(resultado);
        verify(ticketRepository, never()).save(any());
    }

    @Test
    void eliminarTicket_existente_llamaDelete() {
        when(ticketRepository.existsById(1L)).thenReturn(true);
        doNothing().when(ticketRepository).deleteById(1L);

        ticketService.eliminarTicket(1L);

        verify(ticketRepository).deleteById(1L);
    }

    @Test
    void eliminarTicket_noExiste_noHaceNada() {
        when(ticketRepository.existsById(99L)).thenReturn(false);

        ticketService.eliminarTicket(99L);

        verify(ticketRepository, never()).deleteById(anyLong());
    }
}

