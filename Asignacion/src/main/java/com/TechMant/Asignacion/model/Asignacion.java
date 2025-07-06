package com.TechMant.Asignacion.model;

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
@Table(name = "Asignacion" )
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entidad que representa la asignación de un caso a un técnico")
public class Asignacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único de la asignación", example = "1")
    private Long idAsignacion;

    @Column(name = "nombre_asignado") //nombre del tecnico al que fue asignado 
    @Schema(description = "Nombre del técnico asignado al caso", example = "Juan López")
    private String nombreAsignado;

    @Column(name = "nombre_caso") //tipo de caso que se le asigno
    @Schema(description = "Nombre del servicio asignado", example = "Reparación de Laptop")
    private String nombreServicio;

    //llaves foraneas
    @Column(nullable = false)
    @Schema(description = "ID del técnico asignado", example = "1")
    private Long idTecnico; 

}
