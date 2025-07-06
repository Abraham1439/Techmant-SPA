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

import com.techmant.Gestion_resena.model.Resena;
import com.techmant.Gestion_resena.service.ResenaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/resena")
@Tag(name = "Resenas", description = "API para gestionar resenas del sistema")
public class ResenaCotroller {

    
    @Autowired
    private ResenaService resenaService;
   

    @Operation(
        summary = "Crear una nueva reseña",
        description = "Registra una nueva reseña en la base de datos."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Reseña creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @PostMapping
    public ResponseEntity<Resena> crearResena(@RequestBody Resena resena) {
        Resena nueva = resenaService.crearResena(resena);
        return ResponseEntity.ok(nueva);
    }

    @Operation(
        summary = "Obtener todas las reseñas",
        description = "Devuelve una lista con todas las reseñas registradas."
    )
    @ApiResponse(responseCode = "200", description = "Lista de reseñas obtenida correctamente")
    @GetMapping
    public ResponseEntity<List<Resena>> obtenerTodasLasResenas() {
        List<Resena> resenas = resenaService.obtenerTodasLasResenas();
        return ResponseEntity.ok(resenas);
    }

   @Operation(
        summary = "Obtener reseña por ID",
        description = "Busca una reseña específica usando su ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reseña encontrada"),
        @ApiResponse(responseCode = "404", description = "Reseña no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Resena> obtenerResenaPorId(@PathVariable Long id) {
        Resena resena = resenaService.obtenerResenaPorId(id);
        return ResponseEntity.ok(resena);
    }

    @Operation(
        summary = "Actualizar reseña",
        description = "Actualiza los datos de una reseña existente por ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reseña actualizada correctamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
        @ApiResponse(responseCode = "404", description = "Reseña no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Resena> actualizarResena(@PathVariable Long id, @RequestBody Resena resena) {
        Resena actualizado = resenaService.actualizarResena(id, resena);
        if (actualizado == null) {
            throw new RuntimeException("Resena no encontrada con ID: " + id);
        }
        return ResponseEntity.ok(actualizado);
    }

    @Operation(
        summary = "Eliminar reseña",
        description = "Elimina una reseña por su ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Reseña eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Reseña no encontrada")
    })

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarResena(@PathVariable Long id) {
        resenaService.eliminarResena(id);
        return ResponseEntity.ok("Resena con ID " + id + " eliminada correctamente.");
    }
}