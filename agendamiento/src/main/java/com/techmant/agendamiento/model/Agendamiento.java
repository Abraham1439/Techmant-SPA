package com.techmant.agendamiento.model;

import java.util.Date;

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
public class Agendamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAgendamiento;

    @Column(name = "estado", nullable = false)
    private String estado;

    @Column(name = "fechaCita", nullable = false)
    private Date fechaCita; 

    @Column(name = "observaciones", nullable = false)
    private String observaciones;

    //llaves foraneas
    @Column(nullable = false)
    private Long idSolicitud;

}
