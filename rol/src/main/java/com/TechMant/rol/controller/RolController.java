package com.TechMant.rol.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.TechMant.rol.model.Rol;
import com.TechMant.rol.service.RolService;

import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/rol")
@Tag(name = "Controlador de Roles", description = "API para la gestión de roles en el sistema")
public class RolController {

    @Autowired
    private RolService rolService;

    @Operation(summary = "Obtener todos los roles", description = "Retorna una lista de todos los roles disponibles")
    @ApiResponse(responseCode = "200", description = "Lista de roles encontrada")
    @ApiResponse(responseCode = "204", description = "No hay roles registrados")
    @GetMapping()
    public ResponseEntity<List<Rol>> getAllRoles() {
        List<Rol> roles = rolService.getAllRoles();
        if (roles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(roles);
    }

    @Operation(summary = "Obtener un rol por ID", description = "Retorna un rol específico según su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Rol encontrado"),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Rol> getRolById(
            @PathVariable Long id) {
        try {
            Rol rol = rolService.getRolById(id);
            return ResponseEntity.ok(rol);
        } catch (RuntimeException ex) {
            if ("Rol no encontrado".equals(ex.getMessage())) {
                return ResponseEntity.notFound().build();
            }
            throw ex;
        }
    }

    @Operation(summary = "Crear un nuevo rol", description = "Crea un nuevo rol en el sistema")
    @ApiResponse(responseCode = "200", description = "Rol creado exitosamente")
    @PostMapping()
    public ResponseEntity<Rol> createRol(
            @RequestBody Rol rol) {
        Rol rolCreated = rolService.createRol(rol);
        return ResponseEntity.ok(rolCreated);
    }

    @Operation(summary = "Actualizar un rol existente", description = "Actualiza los datos de un rol según su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Rol actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Rol> updateRol(

            @PathVariable Long id,
            @RequestBody Rol rol) {
        Rol rolUpdated = rolService.updateRol(id, rol);
        return ResponseEntity.ok(rolUpdated);
    }

    @Operation(summary = "Eliminar un rol", description = "Elimina un rol según su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Rol eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRol(
           @PathVariable Long id) {
        rolService.deleteRol(id);
        return ResponseEntity.noContent().build();
    }
}
