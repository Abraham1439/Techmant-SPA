package com.TechMantSPA.equipos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropietarioDTO {

    private Long idUsuario;
    private String nombre;
    private String correo;
}
