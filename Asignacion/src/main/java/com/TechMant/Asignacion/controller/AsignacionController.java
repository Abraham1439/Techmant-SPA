package com.TechMant.Asignacion.controller;

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
    @GetMapping("/{id}")
    public ResponseEntity<Asignacion> obtenerAsignacionporId(@PathVariable Long id) {
        Asignacion asignacion = asignacionService.obtenerAsignacionporId(id);
        if(asignacion == null) {
            return ResponseEntity.notFound().build();
        }else {
            return ResponseEntity.ok(asignacion);
        }
    }

    //Endpoint para crear una asignacion nueva(con conexion)
    @PostMapping 
    public ResponseEntity<?> agregarAsignacion(@RequestBody Asignacion nueva) {
        try {
            Asignacion asignacion = asignacionService.saveAsignacion(nueva);
            return ResponseEntity.status(201).body(asignacion);
        }catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }


    //Endpoint para actualiazar un agendamiento por su ID 
    @PutMapping("/{id}")
    public ResponseEntity<Asignacion> modificarAsignacion(@PathVariable Long id, @RequestBody Asignacion asig) {
        try {
            //Creamos un objeto para buscar el servicio que queremos modificar
            Asignacion asignacion2 = asignacionService.obtenerAsignacionporId(id);
            //si este existe modificamos los datos 
            asignacion2.setIdAsignacion(id);
            asignacion2.setNombreAsignado(asig.getNombreAsignado());
            asignacion2.setNombreCaso(asig.getNombreCaso());
            //actualizar el servicio
            asignacionService.saveAsignacion(asignacion2);
            return ResponseEntity.ok(asignacion2);
        }catch (Exception e) {
            //Si el servicio no existe 
            return ResponseEntity.notFound().build();
        }
    }


    //Endpoint para eliminar un servicio por su ID 
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarAsignacionPorId(@PathVariable Long id) {
        try {
            asignacionService.eliminarAsignacionPorId(id);
            return ResponseEntity.noContent().build();
        }catch (Exception e) {
            //Si no existe el servicio retorno not found 
            return ResponseEntity.ok("Asignacion con ID:" + id + "eliminado correctamente");
        }
    }

    
}
