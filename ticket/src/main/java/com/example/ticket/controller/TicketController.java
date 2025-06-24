package com.example.ticket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ticket.client.UsuarioClient;
import com.example.ticket.dto.UsuarioDTO;
import com.example.ticket.model.Ticket;
import com.example.ticket.services.TicketService;

@RestController
@RequestMapping("/api/v1/tickets")
public class TicketController {
    @Autowired
    private TicketService ticketService;

    @Autowired
    private UsuarioClient usuarioClient;

    // Crear un nuevo ticket
    @PostMapping
    public ResponseEntity<?> createTicket(@RequestBody Ticket ticket) {
        // Validar ID de usuario
        if (ticket.getUsuarioId() == null) {
            return ResponseEntity.badRequest()
                    .body("Error: El usuario con ID" + ticket.getUsuarioId() + " no puede ser nulo.");
        }

        // Obtener usuario desde el microservicio
        UsuarioDTO usuario = usuarioClient.getUsuarioById(ticket.getUsuarioId());
        if (usuario == null) {
            return ResponseEntity.badRequest().body("Error: El usuario con ID " + ticket.getUsuarioId() + " no existe");
        }

        Long idRol = usuario.getIdRol();
        if (idRol == null || (!idRol.equals(2L) && !idRol.equals(5L))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Error: El usaurio no tiene permiso para crear tickets.");
        }
        Ticket ticketGuardado = ticketService.crearTicket(ticket);
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketGuardado);
    }

    // Obtener todos los tickets
    @GetMapping
    public ResponseEntity<List<Ticket>> obtenerTodosLosTickets() {
        return ResponseEntity.ok(ticketService.obtenerTodosLosTickets());
    }

    // Obtener un ticket por ID
    @GetMapping("/{id}")
    public ResponseEntity<Ticket> obtenerTicketPorId(@PathVariable Long id) {
        try {
            Ticket ticket = ticketService.obtenerTicketPorId(id);
            return ResponseEntity.ok(ticket);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Actualizar un ticket por ID
    @PutMapping("/{id}")
    public ResponseEntity<Ticket> actualizarTicket(@PathVariable Long id, @RequestBody Ticket ticket) {
        Ticket actualizado = ticketService.actualizarTicket(id, ticket); // Lanza excepción si no existe
        return ResponseEntity.ok(actualizado);
    }

    // Eliminar un ticket por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarTicket(@PathVariable Long id) {
        ticketService.eliminarTicket(id); // Lanza excepción si no existe
        return ResponseEntity.ok("Ticket con ID " + id + " eliminado correctamente.");
    }
}
