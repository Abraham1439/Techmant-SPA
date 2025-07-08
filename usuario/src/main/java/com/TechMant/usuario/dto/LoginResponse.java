package com.TechMant.usuario.dto;

import lombok.Data;

@Data
public class LoginResponse{

    private Long idUsuario;
    private String nombre;
    private String correo;
    private String rol;


}
