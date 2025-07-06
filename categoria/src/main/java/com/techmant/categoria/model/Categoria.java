package com.techmant.categoria.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Categoria")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Tipos de categorias que se hay disponibles para los servicios de los dispositivos")
public class Categoria {

    @Id
    @Schema(description = "ID unico para los tipos de categorias", example = "1")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCategoria;

    @Schema(description = "Nombres de las categorias que estan disponibles")
    @Column (name = "nombre_categoria")
    private String nombreCategoria;

    @Schema(description = "Descripcion de las categorias que sirve para explicar brevemente que servicios se asocian a esa categoria")
    @Column (name = "descripcion")
    private String descripcion; 

}
