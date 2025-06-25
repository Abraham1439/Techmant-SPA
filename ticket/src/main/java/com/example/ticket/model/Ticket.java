package com.example.ticket.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ticket")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Modelo que representa un ticket de soporte técnico")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del ticket", example = "1")
    private Long id_ticket;

    @Column(nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Schema(description = "Fecha de creación del ticket", example = "24/06/2023")
    private LocalDate fecha_ticket;

    @Column(nullable = false)
    @Schema(description = "Descripción detallada del problema o solicitud", 
             example = "No puedo iniciar sesión en la plataforma")
    private String descripcion;

    @Column(nullable = false)
    @Schema(description = "ID del motivo del ticket", example = "2")
    private Long motivo_id;

    @Column(nullable = false)
    @Schema(description = "ID del usuario que creó el ticket", example = "123")
    private Long usuarioId; // Relacion con usuario
}
