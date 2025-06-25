package com.techmant.Modelo.model;

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
@Table(name = "Modelo")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidad que representa un modelo de un producto o dispositivo, incluyendo su marca y número de serie")
public class Modelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único de la modelo", example = "1")
    private Long idModelo; // Identificador único de la modelo

    @Schema(description = "Marca del modelo del producto o dispositivo", example = "Samsung")
    @Column(nullable = false)
    private String marca;

    @Schema(description = "Número de serie del modelo, útil para identificar dispositivos específicos", example = "SN123456789XYZ")
    @Column(nullable = false ,  length = 20)
    private String numeroDeSerie;

}
