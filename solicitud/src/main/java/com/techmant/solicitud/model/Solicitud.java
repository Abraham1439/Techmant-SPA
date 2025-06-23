package com.techmant.solicitud.model;

import com.fasterxml.jackson.annotation.JsonFormat;

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
    private Long idSolicitud; // Asegúrate de definir un identificador
    
    @Column(nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private javax.xml.crypto.Data fecha_solicitud; //DD/MM/YYYY

    @Column(nullable = true) 
    private String comentario; // Comentarios adicionales
    
    @Column(nullable = false)
    private String total; // Total de la solicitud
}

