package com.techmant.Gestion_tecnico.controller;

import java.net.URI;
import java.util.List;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techmant.Gestion_tecnico.model.Tecnico;
import com.techmant.Gestion_tecnico.service.TecnicoService;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/tecnicos")
@Tag(name = "Técnicos", description = "API para gestionar técnicos del sistema")
public class TecnicoController {
    
    @Autowired
    private TecnicoService tecnicoService;

   @Operation(summary = "Crear un nuevo técnico", description = "Registra un nuevo técnico en la base de datos.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Técnico creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @PostMapping
    public ResponseEntity<Tecnico> crearTecnico(@RequestBody Tecnico tecnico) {
        Tecnico nuevo = tecnicoService.crearTecnico(tecnico);
        URI location = URI.create("/api/v1/tecnicos/" + nuevo.getIdTecnico());
        return ResponseEntity.created(location).body(nuevo);
    }

    @Operation(summary = "Obtener todos los técnicos", description = "Devuelve una lista con todos los técnicos registrados.")
    @ApiResponse(responseCode = "200", description = "Lista de técnicos obtenida correctamente")
    @GetMapping
    public ResponseEntity<List<Tecnico>> obtenerTodosLosTecnicos() {
        return ResponseEntity.ok(tecnicoService.obtenerTodosLosTecnicos());
    }

    @Operation(summary = "Obtener técnico por ID", description = "Busca un técnico específico usando su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Técnico encontrado"),
        @ApiResponse(responseCode = "404", description = "Técnico no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Tecnico> obtenerTecnicoPorId(
            @Parameter(description = "ID del técnico", required = true)
            @PathVariable Long id) {
        Tecnico tecnico = tecnicoService.obtenerTecnicoPorId(id);
        if (tecnico == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Técnico no encontrado");
        }
        return ResponseEntity.ok(tecnico);
    }

    @Operation(summary = "Actualizar técnico", description = "Actualiza los datos de un técnico existente por ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Técnico actualizado correctamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
        @ApiResponse(responseCode = "404", description = "Técnico no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Tecnico> actualizarTecnico(
            @Parameter(description = "ID del técnico", required = true)
            @PathVariable Long id,
            @RequestBody Tecnico tecnico) {
        Tecnico actualizado = tecnicoService.actualizarTecnico(id, tecnico);
        if (actualizado == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Técnico no encontrado");
        }
        return ResponseEntity.ok(actualizado);
    }

    @Operation(summary = "Eliminar técnico", description = "Elimina un técnico por su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Técnico eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Técnico no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTecnico(
            @Parameter(description = "ID del técnico", required = true)
            @PathVariable Long id) {
        Tecnico tecnico = tecnicoService.obtenerTecnicoPorId(id);
        if (tecnico == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Técnico no encontrado");
        }
        tecnicoService.eliminarTecnico(id);
        return ResponseEntity.noContent().build();
    }
}