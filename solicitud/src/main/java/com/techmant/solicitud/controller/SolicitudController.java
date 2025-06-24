package com.techmant.solicitud.controller;
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

import com.techmant.solicitud.model.Solicitud;
import com.techmant.solicitud.service.SolicitudService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;



@RestController
@RequestMapping("/api/v1/solicitudes")
@Tag(name = "Solicitudes", description = "API para gestionar solicitudes de servicio técnico")
public class SolicitudController {
    @Autowired
    private SolicitudService solicitudService;

    @Operation(summary = "Crear una nueva solicitud", description = "Registra una nueva solicitud de servicio técnico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Solicitud creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })

    // Crear una nueva solicitud
    @PostMapping
    public ResponseEntity<Solicitud> crearSolicitud(@RequestBody Solicitud solicitud) {
        Solicitud nuevaSolicitud = solicitudService.crearSolicitud(solicitud);
        return ResponseEntity.ok(nuevaSolicitud);
    }

    @Operation(summary = "Obtener todas las solicitudes", description = "Devuelve una lista con todas las solicitudes registradas")
    @ApiResponse(responseCode = "200", description = "Lista de solicitudes obtenida correctamente")

    // Obtener todas las solicitudes
    @GetMapping
    public ResponseEntity<List<Solicitud>> obtenerTodasLasSolicitudes() {
        return ResponseEntity.ok(solicitudService.obtenerTodasSolicitudes());
    }

    
    @Operation(summary = "Obtener solicitud por ID", description = "Devuelve los datos de una solicitud específica según su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Solicitud encontrada"),
        @ApiResponse(responseCode = "404", description = "Solicitud no encontrada")
    })

    // Obtener una solicitud por ID
    @GetMapping("/{id}")
    public ResponseEntity<Solicitud> obtenerSolicitudPorId(@PathVariable Long id) {
        Solicitud solicitud = solicitudService.obtenerSolicitudPorId(id);
        if (solicitud != null) {
            return ResponseEntity.ok(solicitud);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @Operation(summary = "Actualizar una solicitud", description = "Modifica una solicitud existente según su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Solicitud actualizada correctamente"),
        @ApiResponse(responseCode = "404", description = "Solicitud no encontrada")
    })
    // Actualizar una solicitud
    @PutMapping("/{id}")
    public ResponseEntity<Solicitud> actualizarSolicitud(@PathVariable Long id, @RequestBody Solicitud solicitud) {
        Solicitud solicitudActualizada = solicitudService.actualizarSolicitud(id, solicitud);
        if (solicitudActualizada != null) {
            return ResponseEntity.ok(solicitudActualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Eliminar una solicitud", description = "Elimina una solicitud de la base de datos según su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Solicitud eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Solicitud no encontrada")
    })
    // Eliminar una solicitud
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSolicitud(@PathVariable Long id) {
        solicitudService.eliminarSolicitud(id);
        return ResponseEntity.noContent().build();
    }
}
