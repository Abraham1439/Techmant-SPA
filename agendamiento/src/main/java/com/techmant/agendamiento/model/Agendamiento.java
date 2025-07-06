package com.techmant.agendamiento.model;

import java.util.Date;

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
@Table(name = "Agendamiento")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Agendamiento programado para una solicitud de servicio")
public class Agendamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del agendamiento", example = "1")
    private Long idAgendamiento;

    @Schema(description = "Estado actual del agendamiento (por ejemplo: 'pendiente', 'completado')", example = "pendiente")
    @Column(name = "estado", nullable = false)
    private String estado;

    @Schema(description = "Fecha programada para la cita", example = "2025/07/08")
    @Column(name = "fechaCita", nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date fechaCita; 

    @Schema(description = "Observaciones adicionales relacionadas con el agendamiento", example = "Cliente solicita atención urgente")
    @Column(name = "observaciones", nullable = false)
    private String observaciones;


    //llaves foraneas
    @Schema(description = "ID de la solicitud asociada a este agendamiento", example = "5")
    @Column(nullable = false)
    private Long idSolicitud;

}
