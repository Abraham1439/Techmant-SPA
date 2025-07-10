package com.TechMantSPA.equipos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Entidad que representa un equipo en el sistema
 */
@Entity
@Table(name = "Equipos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Modelo que representa un equipo en el sistema")
public class Equipos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_equipo")
    @Schema(description = "Identificador único del equipo", example = "1")
    private Long idEquipo;
    
    @Column(name = "tipo_de_dispositivo")
    @Schema(description = "Tipo de dispositivo del equipo", example = "Laptop", required = true)
    private String tipoDeDispositivo;
    
    @Column
    @Schema(description = "Marca del equipo", example = "Dell", required = true)
    private String marca;
    
    @Column(name="nro_serie")
    @Schema(description = "Número de serie del equipo", example = "ABC123456", required = true)
    private String nroSerie;
    
    @Column
    @Schema(description = "Descripción detallada del equipo", example = "Laptop Dell XPS 15, 16GB RAM, 512GB SSD")
    private String descripcion;
    
    @Column(name="id_usuario_registro")
    private Long idUsuarioRegistro;

    @Column(name="id_dueno_equipo")
    private Long idDuenoEquipo;

    @Column(name="correo_usuario_registro")
private String correoUsuarioRegistro;

@Column(name="correo_dueno_equipo")
private String correoDuenoEquipo;
    
}
