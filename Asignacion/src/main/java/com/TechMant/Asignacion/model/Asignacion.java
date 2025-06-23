package com.TechMant.Asignacion.model;

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
public class Asignacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAsignacion;

    @Column(name = "nombre_asignado") //nombre del tecnico al que fue asignado 
    private String nombreAsignado;

    @Column(name = "nombre_caso") //tipo de caso que se le asigno 
    private String nombreCaso;

    

}
