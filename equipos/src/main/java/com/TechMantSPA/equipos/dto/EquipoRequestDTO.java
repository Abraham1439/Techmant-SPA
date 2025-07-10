package com.TechMantSPA.equipos.dto;

import lombok.Data;

@Data
public class EquipoRequestDTO {

    private String tipoDeDispositivo;
    private String marca;
    private String nroSerie;
    private String descripcion;
    private String correoDuenoEquipo;       // correo del dueño
    private String correoUsuarioRegistro;  
}
