package com.TechMant.rol.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rol")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Modelo que representa un rol en el sistema")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    @Schema(description = "ID único del rol", example = "1")
    private Long idRol;
    
    @Column(name = "nombre_rol", nullable = false, length = 50)
    @Schema(description = "Nombre del rol", example = "ADMIN", required = true, maxLength = 50)
    private String nombreRol;
}
