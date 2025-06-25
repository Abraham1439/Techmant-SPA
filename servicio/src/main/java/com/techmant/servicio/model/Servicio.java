package com.techmant.servicio.model;

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
@Table(name = "Servicio")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidad que representa un servicio ofrecido, con su nombre, descripción, precio y categoría")
public class Servicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del servicio", example = "1")
    private long idServicio;
    
    @Column(name = "nombre_servicio", nullable = false)
    @Schema(description = "Nombre del servicio", example = "Reparación de equipo")
    private String nombreServicio;

    @Column(name = "descripcion", nullable = false)
    @Schema(description = "Descripción detallada del servicio", example = "Servicio de reparación para equipos electrónicos")
    private String descripcion;

    @Column(name = "precio", nullable = false)
    @Schema(description = "Precio del servicio en formato decimal", example = "150000")
    private float precio;

    //llaves foraneas 
    @Column (nullable = false)
    @Schema(description = "ID de la categoría a la que pertenece el servicio", example = "2")
    private Long idCategoria; 

}
