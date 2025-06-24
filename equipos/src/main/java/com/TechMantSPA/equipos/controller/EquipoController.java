package com.TechMantSPA.equipos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TechMantSPA.equipos.dto.UsuarioDTO;
import com.TechMantSPA.equipos.model.Equipos;
import com.TechMantSPA.equipos.services.EquipoServices;
import com.TechMantSPA.equipos.client.UsuarioClient;

@RestController
@RequestMapping("api/v1/equipos")
public class EquipoController {

    @Autowired
    private EquipoServices equipoServices;

    @Autowired
    private UsuarioClient usuarioClient;

    // ENDPOINT para traer todos los equipos
    @GetMapping
    public ResponseEntity<List<Equipos>> getAll(){
        List<Equipos> equipos = equipoServices.getAllEquipos();
        if(equipos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(equipos);
    }

    // ENDPOINT para traer un equipo por ID
    @GetMapping("/{id}")
    public ResponseEntity<Equipos> getById(@PathVariable Long id){
        Equipos equipo = equipoServices.getEquipoById(id);
        if(equipo == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(equipo);
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Equipos>> getEquiposPorTipo(@PathVariable String tipo) {
        return ResponseEntity.ok(equipoServices.getEquiposPorTipo(tipo));
    }

    // 🔹 GET: Por usuario
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Equipos>> getEquiposPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(equipoServices.getEquiposPorUsuario(usuarioId));
    }

    // 🔹 GET: Por tipo y usuario
    @GetMapping("/tipo/{tipo}/usuario/{usuarioId}")
    public ResponseEntity<List<Equipos>> getEquiposPorTipoYUsuario(
            @PathVariable String tipo,
            @PathVariable Long usuarioId) {
        return ResponseEntity.ok(equipoServices.getEquiposPorTipoYUsuario(tipo, usuarioId));
    }

    // ENDPOINT para crear un equipo
    @PostMapping
    public ResponseEntity<?> createEquipo(@RequestBody Equipos equipo){
        if(equipo.getIdUsuario() == null){
            return ResponseEntity.badRequest().body("El ID del usuario es obligatorio");
        }
        // Validar existencia del usuairo mediante el microservicio de usuarios 
        UsuarioDTO usuario = usuarioClient.getUsuarioById(equipo.getIdUsuario());
        if(usuario == null){
            return ResponseEntity.badRequest().body("El usuario no existe");
        }
        // Validar que el usuario tenga permiso por su rol 
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

    // ENDPOINT para actualizar un equipo
    @PutMapping("/{id}")
    public ResponseEntity<Equipos> updateEquipo(@PathVariable Long id, @RequestBody Equipos equipo){
        Equipos equipoActualizado = equipoServices.updateEquipo(id, equipo);
        if(equipoActualizado == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(equipoActualizado);
    }

    // ENDPOINT para eliminar un equipo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEquipo(@PathVariable Long id){
        equipoServices.deleteEquipo(id);
        return ResponseEntity.noContent().build();
    }

}
