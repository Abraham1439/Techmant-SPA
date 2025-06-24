package com.techmant.Gestion_tecnico.model;

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
@Table(name = "tecnico")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidad que representa a un técnico encargado de realizar los servicios")
public class Tecnico {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del técnico", example = "1")
    private Long idTecnico;

    @Column(nullable = false)
    @Schema(description = "Nombre completo del técnico", example = "Carlos Pérez")
    private String nombre;

    @Column(nullable = false)
    @Schema(description = "Especialidad del técnico", example = "Soporte de redes")
    private String especialidad;
    
}
