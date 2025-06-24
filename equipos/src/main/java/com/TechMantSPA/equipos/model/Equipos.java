package com.TechMantSPA.equipos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Equipos")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Equipos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_equipo")
    private Long idEquipo;
    @Column(name = "tipo_de_dispositivo")
private String tipoDeDispositivo;
    @Column
    private String marca;
    @Column(name="nro_serie")
    private String nroSerie;
    @Column
    private String descripcion;
    // Cambio de nombre para que relacione con el usuario 
    @Column(name="id_usuario")
    private Long idUsuario;

    // Se elimino la columna id_tipo porque no tiene relación con ninguna tabla
}
