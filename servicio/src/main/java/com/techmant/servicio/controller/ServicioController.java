package com.techmant.servicio.controller;

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

import com.techmant.servicio.model.Servicio;
import com.techmant.servicio.service.ServicioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/servicios")
@Tag(name = "Servicios", description = "API para gestionar servicios ofrecidos por TechMant")
public class ServicioController {

    @Autowired
    private ServicioService servicioService;


    //Se actualizo esto 
    @Operation(summary = "Obtener todos los servicios", description = "Devuelve una lista con todos los servicios registrados en la base de datos.")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Lista de servicios obtenida correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Servicio.class))),
    @ApiResponse(responseCode = "204", description = "No se encontraron servicios registrados")
    })

    //Enpoint para obtener todos los servicios 
    @GetMapping
    public ResponseEntity<List<Servicio>> listarServicios() {
        List<Servicio> servicios = servicioService.obtenerServicios();
        //preguntar si vienen registros vacios o no 
        if(servicios.isEmpty()) {
            //si la lista esta vacia se pone el error 204 
            return ResponseEntity.noContent().build();
        }
        //si no esta vacia entonces nos los da :)
        return ResponseEntity.ok(servicios);
    }



    //Se agrego esto 
    @Operation(summary = "Obtener un servicio por su ID", description = "Busca y devuelve un servicio específico usando su ID")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Servicio encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Servicio.class))),
    @ApiResponse(responseCode = "404", description = "Servicio no encontrado")
    })

    //Endpoint para buscar un servicio por su id 
    @GetMapping("/{id}")
    public ResponseEntity<Servicio> obtenerServicioPorId(@PathVariable Long id) {
        Servicio servicio = servicioService.obtenerServicioPorId(id);
        if(servicio == null) {
            return ResponseEntity.notFound().build();
        }else {
            return ResponseEntity.ok(servicio);
        }
    }


    //se agrego esto 
    @Operation(summary = "Crear un nuevo servicio", description = "Registra un nuevo servicio si la categoría indicada existe")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Servicio creado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Servicio.class))),
    @ApiResponse(responseCode = "404", description = "Categoría no encontrada, no se puede crear el servicio")
    })
    //Endpoint para crear un servicio nuevo(con conexion)
    @PostMapping
    public ResponseEntity<?> agregarServicio(@RequestBody Servicio nuevo) {
        try {
            Servicio servicio = servicioService.saveServicio(nuevo);
            return ResponseEntity.status(201).body(servicio);
        }catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }

    }


    //se agrego esto 
    @Operation(summary = "Actualizar un servicio existente", description = "Modifica los datos de un servicio ya registrado por su ID")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Servicio actualizado correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Servicio.class))),
    @ApiResponse(responseCode = "404", description = "Servicio no encontrado")
    })

    //Endpoint para actualiazar un servicio por su ID 
    @PutMapping("/{id}")
    public ResponseEntity<Servicio> modificarServicio(@PathVariable Long id, @RequestBody Servicio servi) {
        try {
            //Creamos un objeto para buscar el servicio que queremos modificar
            Servicio servicio2 = servicioService.obtenerServicioPorId(id);
            //si este existe modificamos los datos 
            servicio2.setIdServicio(id);
            servicio2.setNombreServicio(servi.getNombreServicio());
            servicio2.setDescripcion(servi.getDescripcion());
            servicio2.setPrecio(servi.getPrecio());
            //actualizar el servicio 
            servicioService.saveServicio(servicio2);
            return ResponseEntity.ok(servicio2);
        }catch (Exception e) {
            //Si el servicio no existe 
            return ResponseEntity.notFound().build();
        }
    }



    //Se agrego esto 
    @Operation(summary = "Eliminar un servicio por su ID", description = "Elimina un servicio registrado en la base de datos")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "204", description = "Servicio eliminado exitosamente"),
    @ApiResponse(responseCode = "404", description = "Servicio no encontrado")
    })

    //Endpoint para eliminar un servicio por su ID 
    @DeleteMapping("/{id}") 
    public ResponseEntity<?> eliminarServicioPorId(@PathVariable Long id) {
        try {
            servicioService.eliminarServicioPorId(id);
            return ResponseEntity.noContent().build();
        }catch (Exception e) {
            //Si no existe el servicio retorno not found +
            return ResponseEntity.notFound().build();
        }
    }


}
