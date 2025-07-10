package com.techmant.Gestion_resena.controller;


import java.util.List;
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
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import com.techmant.Gestion_resena.model.Resena;
import com.techmant.Gestion_resena.service.ResenaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/resena")
@Tag(name = "Resenas", description = "API para gestionar resenas del sistema")
public class ResenaController {

    @Autowired
    private ResenaService resenaService;

// Endpoint para obtener todas las reseñas
    @Operation(summary = "Obtener todas las reseñas", description = "Devuelve una lista con todas las reseñas registradas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de reseñas obtenida correctamente", content = @Content(schema = @Schema(implementation = Resena.class))),
        @ApiResponse(responseCode = "204", description = "No hay reseñas registradas")
    })
    @GetMapping
    public ResponseEntity<List<Resena>> listarResenas() {
        List<Resena> resenas = resenaService.getResenas();
        if (resenas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(resenas);
    }

    // Endpoint para agregar una nueva reseña
    @Operation(summary = "Agregar una nueva reseña", description = "Registra una nueva reseña asociada a un usuario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Reseña creada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @PostMapping
    public ResponseEntity<?> agregarResena(@RequestBody Resena nuevaResena) {
        try {
            Resena resena = resenaService.agregarResena(nuevaResena);
            return ResponseEntity.status(201).body(resena);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // Endpoint para buscar una reseña por ID
    @Operation(summary = "Buscar reseña por ID", description = "Obtiene una reseña específica según su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reseña encontrada"),
        @ApiResponse(responseCode = "404", description = "Reseña no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Resena> buscarResenaPorId(@PathVariable Long id) {
        Resena resena = resenaService.getResenaById(id);
        return ResponseEntity.ok(resena);
    }

    // Endpoint para eliminar una reseña mediante su ID
    @Operation(summary = "Eliminar reseña", description = "Elimina una reseña existente por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Reseña eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Reseña no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarResena(@PathVariable Long id) {
        try {
            resenaService.eliminarResena(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint para actualizar una reseña mediante su ID
    @Operation(summary = "Actualizar reseña por ID", description = "Actualiza los datos de una reseña existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reseña actualizada correctamente"),
        @ApiResponse(responseCode = "404", description = "Reseña no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Resena> actualizarResena(@PathVariable Long id, @RequestBody Resena resenaActualizada) {
        try {
            Resena resena = resenaService.getResenaById(id);
            // Actualizar los campos de la reseña
            resena.setComentario(resenaActualizada.getComentario());
            resena.setCalificacion(resenaActualizada.getCalificacion());
            // Guardar la reseña actualizada
            resenaService.agregarResena(resena);
            return ResponseEntity.ok(resena);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}