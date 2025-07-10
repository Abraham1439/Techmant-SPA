package com.techmant.solicitud.model;

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
@Table(name = "solicitud")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Solicitud {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único de la solicitud", example = "1")
    private Long idSolicitud; // Asegúrate de definir un identificador
    
    @Column(nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Schema(description = "Fecha en la que se realiza la solicitud", example = "24/06/2025")
    private Date fechasolicitud;

    @Column(nullable = true) 
    @Schema(description = "Comentario adicional que puede incluir el cliente", example = "Aumento de memoria Ram")
    private String comentario; // Comentarios adicionales
    
    @Column(nullable = false)
    @Schema(description = "Total a pagar por la solicitud", example = "30000")
    private int total; // Total de la solicitud
}  

