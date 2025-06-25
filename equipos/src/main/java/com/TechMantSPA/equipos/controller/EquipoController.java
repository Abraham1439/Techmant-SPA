package com.TechMantSPA.equipos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.TechMantSPA.equipos.dto.UsuarioDTO;
import com.TechMantSPA.equipos.model.Equipos;
import com.TechMantSPA.equipos.services.EquipoServices;
import com.TechMantSPA.equipos.client.UsuarioClient;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/equipos")
@Tag(name = "Controlador de Equipos", description = "API para la gestión de equipos")
public class EquipoController {

    @Autowired
    private EquipoServices equipoServices;

    @Autowired
    private UsuarioClient usuarioClient;

    @Operation(summary = "Obtener todos los equipos", description = "Retorna una lista de todos los equipos registrados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de equipos obtenida exitosamente"),
        @ApiResponse(responseCode = "204", description = "No hay equipos registrados")
    })
    @GetMapping
    public ResponseEntity<List<Equipos>> getAll(){
        List<Equipos> equipos = equipoServices.getAllEquipos();
        if(equipos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(equipos);
    }

    @Operation(summary = "Obtener un equipo por ID", description = "Retorna un equipo específico por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Equipo encontrado"),
        @ApiResponse(responseCode = "404", description = "Equipo no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Equipos> getById(@PathVariable Long id){
        Equipos equipo = equipoServices.getEquipoById(id);
        if(equipo == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(equipo);
    }

    @Operation(summary = "Obtener equipos por tipo", description = "Retorna una lista de equipos filtrados por tipo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de equipos obtenida exitosamente")
    })
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Equipos>> getEquiposPorTipo(@PathVariable String tipo) {
        return ResponseEntity.ok(equipoServices.getEquiposPorTipo(tipo));
    }

    @Operation(summary = "Obtener equipos por usuario", description = "Retorna una lista de equipos asociados a un usuario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de equipos obtenida exitosamente")
    })
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Equipos>> getEquiposPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(equipoServices.getEquiposPorUsuario(usuarioId));
    }

    @Operation(summary = "Obtener equipos por tipo y usuario", description = "Retorna una lista de equipos filtrados por tipo y usuario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de equipos obtenida exitosamente")
    })
    @GetMapping("/tipo/{tipo}/usuario/{usuarioId}")
    public ResponseEntity<List<Equipos>> getEquiposPorTipoYUsuario(
            @Parameter(description = "Tipo de dispositivo a filtrar") @PathVariable String tipo,
            @Parameter(description = "ID del usuario") @PathVariable Long usuarioId) {
        return ResponseEntity.ok(equipoServices.getEquiposPorTipoYUsuario(tipo, usuarioId));
    }

    @Operation(summary = "Crear un nuevo equipo", description = "Crea un nuevo registro de equipo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Equipo creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "403", description = "No autorizado para realizar esta acción"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<?> createEquipo(@RequestBody Equipos equipo){
        if(equipo.getIdUsuario() == null){
            return ResponseEntity.badRequest().body("El ID del usuario es obligatorio");
        }
        UsuarioDTO usuario = usuarioClient.getUsuarioById(equipo.getIdUsuario());
        if(usuario == null){
            return ResponseEntity.badRequest().body("El usuario no existe");
        }
        if(!List.of(2L, 5L).contains(usuario.getIdRol())){
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .body("Error: Solo los técnicos y supervisores pueden registrar equipos");
        }
        try {
            Equipos nuevoEquipo = equipoServices.createEquipo(equipo);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoEquipo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("Error al crear el equipo: " + e.getMessage());
        }
    }

    @Operation(summary = "Actualizar un equipo existente", description = "Actualiza los datos de un equipo por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Equipo actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Equipo no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Equipos> updateEquipo(@PathVariable Long id, @RequestBody Equipos equipo){
        Equipos equipoActualizado = equipoServices.updateEquipo(id, equipo);
        if(equipoActualizado == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(equipoActualizado);
    }

    @Operation(summary = "Eliminar un equipo", description = "Elimina un equipo por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Equipo eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Equipo no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEquipo(@PathVariable Long id){
        equipoServices.deleteEquipo(id);
        return ResponseEntity.noContent().build();
    }
}
