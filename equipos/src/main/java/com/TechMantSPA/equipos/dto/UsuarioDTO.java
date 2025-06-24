package com.TechMantSPA.equipos.dto;

import lombok.Data;

@Data
public class UsuarioDTO {
    private Long idUsuario;
    private String nombre; 
    private String correo;
    private String password;
    private Long idRol;
    private Long idEstado;
}
