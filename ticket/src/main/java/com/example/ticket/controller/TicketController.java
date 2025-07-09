package com.example.ticket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.ticket.client.UsuarioClient;
import com.example.ticket.dto.UsuarioDTO;
import com.example.ticket.model.Ticket;
import com.example.ticket.services.TicketService;

import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/tickets")
@Tag(name = "Tickets", description = "API para la gestión de tickets de soporte")
public class TicketController {
    
    @Autowired
    private TicketService ticketService;

    @Autowired
    private UsuarioClient usuarioClient;

    @Operation(summary = "Crear un nuevo ticket", description = "Crea un nuevo ticket de soporte")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Ticket creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "403", description = "Usuario no autorizado para crear tickets")
    })
    @PostMapping
    public ResponseEntity<?> createTicket(
            @RequestBody Ticket ticket) {
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
        if (idRol == null || (!idRol.equals(2L) && !idRol.equals(5L)  && !idRol.equals(4L))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Error: El usuario no tiene permiso para crear tickets.");
        }
        Ticket ticketGuardado = ticketService.crearTicket(ticket);
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketGuardado);
    }

    @Operation(summary = "Obtener todos los tickets", description = "Retorna una lista de todos los tickets existentes")
    @ApiResponse(responseCode = "200", description = "Lista de tickets obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<Ticket>> obtenerTodosLosTickets() {
        return ResponseEntity.ok(ticketService.obtenerTodosLosTickets());
    }

    @Operation(summary = "Obtener un ticket por ID", description = "Retorna un ticket específico según su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ticket encontrado"),
        @ApiResponse(responseCode = "404", description = "Ticket no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Ticket> obtenerTicketPorId(
            @PathVariable Long id) {
        try {
            Ticket ticket = ticketService.obtenerTicketPorId(id);
            return ResponseEntity.ok(ticket);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Actualizar un ticket", description = "Actualiza los datos de un ticket existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ticket actualizado exitosamente",
                   content = @Content(schema = @Schema(implementation = Ticket.class))),
        @ApiResponse(responseCode = "404", description = "Ticket no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Ticket> actualizarTicket(
            
            @PathVariable Long id, 
           
            @RequestBody Ticket ticket) {
        Ticket actualizado = ticketService.actualizarTicket(id, ticket); // Lanza excepción si no existe
        return ResponseEntity.ok(actualizado);
    }

    @Operation(summary = "Eliminar un ticket", description = "Elimina un ticket existente por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ticket eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Ticket no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarTicket(
            @PathVariable Long id) {
        ticketService.eliminarTicket(id); // Lanza excepción si no existe
        return ResponseEntity.ok("Ticket con ID " + id + " eliminado correctamente.");
    }
}
