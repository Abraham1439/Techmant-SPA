package com.techmant.solicitud.controller;
import java.net.URI;
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

   
    @Operation(summary = "Crear una nueva solicitud", description = "Registra una nueva solicitud de servicio técnico.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Solicitud creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })

    @PostMapping
    public ResponseEntity<Solicitud> crearSolicitud(@RequestBody Solicitud solicitud) {
        try {
            Solicitud nuevaSolicitud = solicitudService.crearSolicitud(solicitud);
            URI location = URI.create("/api/v1/solicitudes/" + nuevaSolicitud.getIdSolicitud());
            return ResponseEntity.created(location).body(nuevaSolicitud);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build(); // Manejo de errores
        }
    }

    @Operation(summary = "Obtener todas las solicitudes", description = "Devuelve una lista con todas las solicitudes registradas.")
    @ApiResponse(responseCode = "200", description = "Lista de solicitudes obtenida correctamente")
    @GetMapping
    public ResponseEntity<List<Solicitud>> obtenerTodasLasSolicitudes() {
        List<Solicitud> solicitudes = solicitudService.obtenerTodasSolicitudes();
        return ResponseEntity.ok(solicitudes);
    }

    @Operation(summary = "Obtener solicitud por ID", description = "Devuelve los datos de una solicitud específica según su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Solicitud encontrada"),
        @ApiResponse(responseCode = "404", description = "Solicitud no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Solicitud> obtenerSolicitudPorId(@PathVariable Long id) {
    Solicitud solicitud = solicitudService.obtenerSolicitudPorId(id);
    if (solicitud != null) {
        return ResponseEntity.ok(solicitud);
    } else {
        return ResponseEntity.notFound().build();  // Aquí devuelve 404 si no existe
    }
}
    

    @Operation(summary = "Actualizar una solicitud", description = "Modifica una solicitud existente según su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Solicitud actualizada correctamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
        @ApiResponse(responseCode = "404", description = "Solicitud no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Solicitud> actualizarSolicitud(@PathVariable Long id, @RequestBody Solicitud solicitud) {
        try {
            Solicitud solicitudActualizada = solicitudService.actualizarSolicitud(id, solicitud);
            if (solicitudActualizada != null) {
                return ResponseEntity.ok(solicitudActualizada);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build(); // Manejo de errores
        }
    }

    @Operation(summary = "Eliminar una solicitud", description = "Elimina una solicitud de la base de datos según su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Solicitud eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Solicitud no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSolicitud(@PathVariable Long id) {
        try {
            solicitudService.eliminarSolicitud(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (Exception e) {
            return ResponseEntity.notFound().build(); // 404 Not Found si no se encuentra la solicitud
        }
    }
}
