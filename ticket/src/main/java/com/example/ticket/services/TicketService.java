package com.example.ticket.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ticket.model.Ticket;
import com.example.ticket.repository.TicketRepository;

@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;

    // Crear un nuevo ticket
    public Ticket crearTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    // Obtener todos los tickets
    public List<Ticket> obtenerTodosLosTickets() {
        return ticketRepository.findAll();
    }
    
    //Buscar una tickets por ID
    public Ticket obtenerTicketPorId(Long id) {
        return ticketRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Ticket no encontrado"));
    }

    // Actualizar un ticket
    public Ticket actualizarTicket(Long id, Ticket ticketActualizado) {
        if (ticketRepository.existsById(id)) {
            ticketActualizado.setId_ticket(id);
            return ticketRepository.save(ticketActualizado);
        }
        return null;
    }

    // Eliminar un ticket
    public void eliminarTicket(Long id) {
        if (ticketRepository.existsById(id)) {
            ticketRepository.deleteById(id);
        }
    }
}
