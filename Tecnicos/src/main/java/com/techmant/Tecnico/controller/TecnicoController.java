package com.techmant.Tecnico.controller;


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

import com.techmant.Tecnico.model.Tecnico;
import com.techmant.Tecnico.service.TecnicoService;


@RestController
@RequestMapping("/api/v1/tecnicos")
public class TecnicoController {

    @Autowired
    private TecnicoService tecnicoService;

    // Crear un nuevo técnico
    @PostMapping
    public ResponseEntity<Tecnico> crearTecnico(@RequestBody Tecnico tecnico) {
        Tecnico nuevo = tecnicoService.crearTecnico(tecnico);
        return ResponseEntity.ok(nuevo);
    }

    // Obtener todos los técnicos
    @GetMapping
    public ResponseEntity<List<Tecnico>> obtenerTodosLosTecnicos() {
        return ResponseEntity.ok(tecnicoService.obtenerTodosLosTecnicos());
    }

    // Obtener un técnico por ID
    @GetMapping("/{id}")
    public ResponseEntity<Tecnico> obtenerTecnicoPorId(@PathVariable Long id) {
        Tecnico tecnico = tecnicoService.obtenerTecnicoPorId(id);
        return ResponseEntity.ok(tecnico);
    }

    // Actualizar un técnico por ID
    @PutMapping("/{id}")
    public ResponseEntity<Tecnico> actualizarTecnico(@PathVariable Long id, @RequestBody Tecnico tecnico) {
        Tecnico actualizado = tecnicoService.actualizarTecnico(id, tecnico);
        if (actualizado == null) {
            throw new RuntimeException("Técnico no encontrado con ID: " + id);
        }
        return ResponseEntity.ok(actualizado);
    }

    // Eliminar un técnico por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarTecnico(@PathVariable Long id) {
        tecnicoService.eliminarTecnico(id);
        return ResponseEntity.ok("Técnico con ID " + id + " eliminado correctamente.");
    }


  
}
