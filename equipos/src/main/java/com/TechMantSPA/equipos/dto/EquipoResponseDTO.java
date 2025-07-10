package com.TechMantSPA.equipos.dto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquipoResponseDTO {

    private Long idEquipo;
    private String tipoDeDispositivo;
    private String marca;
    private String nroSerie;
    private String descripcion;

    private PropietarioDTO propietario;      // Dueño del equipo
    private PropietarioDTO usuarioRegistro;

}


