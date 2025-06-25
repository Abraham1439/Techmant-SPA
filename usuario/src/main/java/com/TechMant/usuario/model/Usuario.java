package com.TechMant.usuario.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa a un usuario en el sistema.
 * Contiene la información básica de autenticación y perfil del usuario.
 */
@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Modelo que representa un usuario en el sistema")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    @Schema(description = "ID único del usuario", example = "1")
    private Long idUsuario;
    
    @Column(nullable = false)
    @Schema(description = "Nombre completo del usuario", example = "Juan Pérez")
    private String nombre;
    
    @Column(nullable = false, unique = true)
    @Schema(description = "Correo electrónico del usuario (debe ser único)", 
             example = "usuario@example.com", 
             required = true)
    private String correo;
    
    @Column(nullable = false)
    @Schema(description = "Contraseña del usuario (debe estar encriptada)")
    private String password;

    @Column(nullable = false, name = "id_rol")
    @Schema(description = "ID del rol asignado al usuario", example = "1", required = true)
    private Integer idRol;
}

