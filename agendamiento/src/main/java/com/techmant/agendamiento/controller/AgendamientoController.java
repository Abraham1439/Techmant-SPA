package com.techmant.agendamiento.controller;

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

import com.techmant.agendamiento.model.Agendamiento;
import com.techmant.agendamiento.service.AgendamientoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/agendamiento")
@Tag(name = "Agendamientos", description = "API para gestionar los agendamientos de las solicitudes")
public class AgendamientoController {

    @Autowired
    private AgendamientoService agendamientoService;


    //se agrego esto nuevo
    @Operation(summary = "Obtener todos los agendamientos", description = "Devuelve una lista con todos los agendamientos registrados")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Lista de agendamientos obtenida correctamente", content = @Content(schema = @Schema(implementation = Agendamiento.class))),
    @ApiResponse(responseCode = "204", description = "No hay agendamientos registradas") // se agrego esto nuevo 
    })

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


    //se agrego esto nuevo
    @Operation(summary = "Agregar un nuevo agendamiento", description = "Registra un nuevo agendamiento asociado a una solicitud")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Agendamiento creado exitosamente"),
    @ApiResponse(responseCode = "404", description = "Solicitud no encontrada")
    })

    //Endpoint para agregar una agenda (Con conexion)
    @PostMapping
    public ResponseEntity<?> agregarAgendamiento(@RequestBody Agendamiento nuevo) {
        try {
            Agendamiento agendamiento = agendamientoService.agregarAgendamiento(nuevo);
            return ResponseEntity.status(201).body(agendamiento);
        }catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }


    //se agrego esto nuevo
    @Operation(summary = "Buscar agendamiento por ID", description = "Obtiene un agendamiento específico según su ID")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Agendamiento encontrado"),
    @ApiResponse(responseCode = "404", description = "Agendamiento no encontrado")
    })

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


    //se agrego esto nuevo
    @Operation(summary = "Eliminar agendamiento", description = "Elimina un agendamiento existente por su ID")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "204", description = "Agendamiento eliminado exitosamente"),
    @ApiResponse(responseCode = "404", description = "Agendamiento no encontrado")
    })

    //Endpoint para eliminar una agenda mediante su ID 
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarAgendamiento(@PathVariable Long id) {
        try {
            agendamientoService.eliminarAgendamiento(id);
            return ResponseEntity.noContent().build();
        }catch (Exception e) {
            //si no existe la agenda retorna el not found 
            return ResponseEntity.notFound().build();
        }
    }


    //se agrego esto nuevo
    @Operation(summary = "Actualizar agendamiento por ID", description = "Actualiza los datos de un agendamiento existente")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Agendamiento actualizado correctamente"),
    @ApiResponse(responseCode = "404", description = "Agendamiento no encontrado")
    })

    
    //Endpoint para actualizar una agenda mediente su ID (Si es que se necesita):)
    @PutMapping("/{id}")
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
            agendamientoService.agregarAgendamiento(agendamiento2);
            return ResponseEntity.ok(agendamiento2);
        }catch (Exception e) {
            //si la agenda no existe 
            return ResponseEntity.notFound().build();
        }
    }

}
