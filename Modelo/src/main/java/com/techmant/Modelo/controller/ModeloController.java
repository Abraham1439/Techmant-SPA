package com.techmant.Modelo.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.techmant.Modelo.model.Modelo;
import com.techmant.Modelo.service.ModeloService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


import io.swagger.v3.oas.annotations.responses.ApiResponses;



@RestController
@RequestMapping("api/v1/modelos")
@Tag(name = "Modelos", description = "API para gestionar modelos de productos o dispositivos")
public class ModeloController {

    @Autowired
    private ModeloService modeloService;

     @Operation(
        summary = "Crear un nuevo modelo",
        description = "Registra un nuevo modelo en la base de datos."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Modelo creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @PostMapping
    public ResponseEntity<Modelo> crearModelo(@RequestBody Modelo modelo) {
        try {
            Modelo nuevo = modeloService.crearModelo(modelo);
            // Retorna 201 Created con Location del recurso creado
            return ResponseEntity
                    .created(URI.create("/api/v1/modelos/" + nuevo.getIdModelo()))
                    .body(nuevo);
        } catch (Exception e) {
            // Aquí puedes capturar errores de validación o de negocio y devolver 400 Bad Request
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(
        summary = "Obtener todos los modelos",
        description = "Devuelve una lista con todos los modelos registrados."
    )
    @ApiResponse(responseCode = "200", description = "Lista de modelos obtenida correctamente")
    @GetMapping
    public ResponseEntity<List<Modelo>> obtenerTodosLosModelos() {
        List<Modelo> modelos = modeloService.obtenerTodosLosModelos();
        return ResponseEntity.ok(modelos);
    }

    @Operation(
        summary = "Obtener modelo por ID",
        description = "Busca un modelo específico usando su ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Modelo encontrado"),
        @ApiResponse(responseCode = "404", description = "Modelo no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Modelo> obtenerModeloPorId(@PathVariable Long id) {
        Modelo modelo = modeloService.obtenerModeloPorId(id);
        if (modelo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(modelo);
    }

    @Operation(
        summary = "Actualizar modelo",
        description = "Actualiza los datos de un modelo existente por ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Modelo actualizado correctamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
        @ApiResponse(responseCode = "404", description = "Modelo no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Modelo> actualizarModelo(@PathVariable Long id, @RequestBody Modelo modelo) {
        try {
            Modelo actualizado = modeloService.actualizarModelo(id, modelo);
            if (actualizado == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            // Validación o error en el request
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(
        summary = "Eliminar modelo",
        description = "Elimina un modelo por su ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Modelo eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Modelo no encontrado")
    })
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarModelo(@PathVariable Long id) {
        modeloService.eliminarModelo(id);
        return ResponseEntity.ok("Modelo con ID " + id + " eliminado correctamente.");
    }
}