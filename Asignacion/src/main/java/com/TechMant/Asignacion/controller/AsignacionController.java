package com.TechMant.Asignacion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TechMant.Asignacion.model.Asignacion;
import com.TechMant.Asignacion.service.AsignacionService;

@RestController
@RequestMapping("api/v1/asignaciones")
public class AsignacionController {

    @Autowired
    private AsignacionService asignacionService;

    //Enpoint para obtener todos las asignaciones
    @GetMapping
    public ResponseEntity<List<Asignacion>> listarAsignaciones() {
        List<Asignacion> asignaciones = asignacionService.obtenerAsignaciones();
        if(asignaciones.isEmpty()) {
            //si la lista esta vacia se pone el error 204 
            return ResponseEntity.noContent().build();
        }
        //si no esta vacia entonces nos los da :)
        return ResponseEntity.ok(asignaciones);
    }

    //Endpoint para buscar una asignacion por su id 
    @GetMapping("{/id}")
    public ResponseEntity<Asignacion> obtenerAsignacionporId(@PathVariable Long id) {
        Asignacion asignacion = asignacionService.obtenerAsignacionporId(id);
        if(asignacion == null) {
            return ResponseEntity.notFound().build();
        }else {
            return ResponseEntity.ok(asignacion);
        }
    }

    //Endpoint para crear una asignacion nueva
    @PostMapping 
    public ResponseEntity<Asignacion> agregarAsignacion(@RequestBody Asignacion asignacion) {
        try {
            Asignacion nuevaAsignacion = asignacionService.agregarAsignacion(asignacion);
            return ResponseEntity.ok(nuevaAsignacion);
        }catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    } 

    
}
