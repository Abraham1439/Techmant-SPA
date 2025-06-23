package com.techmant.agendamiento.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techmant.agendamiento.model.Agendamiento;
import com.techmant.agendamiento.service.AgendamientoService;

@RestController
@RequestMapping("api/v1/agendamiento")
public class AgendamientoController {

    @Autowired
    private AgendamientoService agendamientoService;

    //Endpoint para obtener todas las agendas 
    @GetMapping
    public ResponseEntity<List<Agendamiento>> listarAgendamiento() {
        List<Agendamiento> agendamientos = agendamientoService.getAgendamientos();
        //preguntar si vienen registros vacios o no 
        if(agendamientos.isEmpty()) {
            //si la lista esta vacia pone el codigo 204 
            return ResponseEntity.noContent().build(); 
        }
        //Si no esta vacia entonces nos lo entrega omaga
        return ResponseEntity.ok(agendamientos);
    }


    //Endpoint para agregar una agenda 
    @PostMapping
    public ResponseEntity<Agendamiento> createAgendamiento(@RequestBody Agendamiento agendamiento) {
        try {
            Agendamiento nuevoAgendamiento = agendamientoService.createAgendamiento(agendamiento);
            return ResponseEntity.ok(nuevoAgendamiento);

        }catch (Exception e) {
            return ResponseEntity.badRequest().build();

        }
    }


    //Endpoint para buscar una agenda por su ID 
    @GetMapping("/{id}")
    public ResponseEntity<Agendamiento> buscarAgendaPorId(@PathVariable Long id) {
        Agendamiento agendamiento = agendamientoService.getAgendamientoById(id);
        if(agendamiento == null) {
            return ResponseEntity.notFound().build();
        }else {
            return ResponseEntity.ok(agendamiento);
        }
    }


    //Endpoint para eliminar una agenda mediante su ID 
    public ResponseEntity<?> eliminarAgendamiento(@PathVariable Long id) {
        try {
            agendamientoService.eliminarAgendamiento(id);
            return ResponseEntity.noContent().build();
        }catch (Exception e) {
            //si no existe la agenda retorna el not found 
            return ResponseEntity.notFound().build();
        }
    }


    
    //Endpoint para actualizar una agenda mediente su ID (Si es que se necesita):)
    public ResponseEntity <Agendamiento> actualizarAgendamiento(@PathVariable Long id, @RequestBody Agendamiento agenda) {
        try {
            //Creamos un objeto para buscar el paciente que queremos modificar
            Agendamiento agendamiento2 = agendamientoService.getAgendamientoById(id);
            //si este existe modificamos los datos
            agendamiento2.setIdAgendamiento(id);
            agendamiento2.setEstado(agenda.getEstado());
            agendamiento2.setFechaCita(agenda.getFechaCita());
            agendamiento2.setObservaciones(agenda.getObservaciones());
            //actualizar el registro
            agendamientoService.createAgendamiento(agendamiento2);
            return ResponseEntity.ok(agendamiento2);
        }catch (Exception e) {
            //si la agenda no existe 
            return ResponseEntity.notFound().build();
        }
    }

}
