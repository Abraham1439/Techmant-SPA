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
    
    private UsuarioInfo propietario;
    private UsuarioInfo usuarioRegistro;

    @Data
    public static class UsuarioInfo{
        private Long idUsuario;
        private String nombre;
        private String correo;
    }

}


