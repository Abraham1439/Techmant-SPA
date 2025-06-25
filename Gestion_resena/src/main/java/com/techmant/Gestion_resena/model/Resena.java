package com.techmant.Gestion_resena.model;

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
@Table(name = "resena")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidad que representa una resena realizada por un usuario sobre un servicio de un producto")
public class Resena {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único de la resena", example = "1")
    private Long idResena; // Identificador único de la resena

     @Column(nullable = false, length = 1000)
    @Schema(description = "Contenido de la resena", example = "Excelente servicio, lo recomiendo.")
    private String Comentario; // Contenido de la resena

    @Column(nullable = false)
    @Schema(description = "Calificación de la resena (1 a 5)", example = "5")
    private int calificacion; // Calificación de la resena

    @Column(nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Schema(description = "Fecha en la que se realizó la resena", example = "24/06/2025")
    private Date fechaCreacion; // Fecha de creación de la resena


    //llave forania 
    @Column(nullable = false) 
    private Long idUsuario;   
}



