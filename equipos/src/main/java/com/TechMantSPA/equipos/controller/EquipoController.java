package com.TechMantSPA.equipos.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.TechMantSPA.equipos.dto.EquipoRequestDTO;
import com.TechMantSPA.equipos.dto.EquipoResponseDTO;
import com.TechMantSPA.equipos.dto.PropietarioDTO;
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
    public ResponseEntity<List<EquipoResponseDTO>> getAll() {
    List<Equipos> equipos = equipoServices.getAllEquipos();

    if (equipos.isEmpty()) {
        return ResponseEntity.noContent().build();
    }

    List<EquipoResponseDTO> responseList = equipos.stream().map(equipo -> {
        UsuarioDTO propietario = usuarioClient.getUsuarioById(equipo.getIdDuenoEquipo());
        UsuarioDTO usuarioRegistro = usuarioClient.getUsuarioById(equipo.getIdUsuarioRegistro());

        PropietarioDTO propietarioDTO = propietario != null
            ? new PropietarioDTO(propietario.getIdUsuario(), propietario.getNombre(), propietario.getCorreo())
            : null;

        PropietarioDTO registroDTO = usuarioRegistro != null
            ? new PropietarioDTO(usuarioRegistro.getIdUsuario(), usuarioRegistro.getNombre(), usuarioRegistro.getCorreo())
            : null;

        EquipoResponseDTO dto = new EquipoResponseDTO();
        dto.setIdEquipo(equipo.getIdEquipo());
        dto.setTipoDeDispositivo(equipo.getTipoDeDispositivo());
        dto.setMarca(equipo.getMarca());
        dto.setNroSerie(equipo.getNroSerie());
        dto.setDescripcion(equipo.getDescripcion());
        dto.setPropietario(propietarioDTO);
        dto.setUsuarioRegistro(registroDTO);

        return dto;
    }).collect(Collectors.toList());

    return ResponseEntity.ok(responseList);
}

    @Operation(summary = "Obtener un equipo por ID", description = "Retorna un equipo específico por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipo encontrado"),
            @ApiResponse(responseCode = "404", description = "Equipo no encontrado")
    })
    @GetMapping("/{id}")
public ResponseEntity<?> getEquipoById(@PathVariable Long id) {
    Equipos equipo = equipoServices.getEquipoById(id);
    if (equipo == null) {
        return ResponseEntity.notFound().build();
    }

    UsuarioDTO propietario = usuarioClient.getUsuarioById(equipo.getIdDuenoEquipo());
    if (propietario == null) {
        return ResponseEntity.badRequest().body("El propietario no existe.");
    }

    UsuarioDTO usuarioRegistro = usuarioClient.getUsuarioById(equipo.getIdUsuarioRegistro());
    if (usuarioRegistro == null) {
        return ResponseEntity.badRequest().body("El usuario que registró no existe.");
    }

    EquipoResponseDTO response = new EquipoResponseDTO();
    response.setIdEquipo(equipo.getIdEquipo());
    response.setTipoDeDispositivo(equipo.getTipoDeDispositivo());
    response.setMarca(equipo.getMarca());
    response.setNroSerie(equipo.getNroSerie());
    response.setDescripcion(equipo.getDescripcion());

    // Propietario
    PropietarioDTO propietarioInfo = new PropietarioDTO(
            propietario.getIdUsuario(),
            propietario.getNombre(),
            propietario.getCorreo()
    );
    response.setPropietario(propietarioInfo);

    // Usuario que registró
    PropietarioDTO registroInfo = new PropietarioDTO(
            usuarioRegistro.getIdUsuario(),
            usuarioRegistro.getNombre(),
            usuarioRegistro.getCorreo()
    );
    response.setUsuarioRegistro(registroInfo);

    return ResponseEntity.ok(response);
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
public ResponseEntity<?> createEquipo(@RequestBody EquipoRequestDTO equipoRequest) {
    // 1. Validar y obtener usuario propietario por correo
    UsuarioDTO propietario = usuarioClient.getUsuarioByCorreo(equipoRequest.getCorreoDuenoEquipo());
    if (propietario == null) {
        return ResponseEntity.badRequest().body("El correo no existe.");
    }
    if (propietario.getIdRol() != 3L) { // validar rol cliente
        return ResponseEntity.badRequest().body("El propietario debe tener rol Cliente.");
    }

    // 2. Validar y obtener usuario que registra por correo
    UsuarioDTO usuarioRegistro = usuarioClient.getUsuarioByCorreo(equipoRequest.getCorreoUsuarioRegistro());
    if (usuarioRegistro == null) {
        return ResponseEntity.badRequest().body("El usuario que registra no existe.");
    }
    if (!List.of(1L, 2L, 3L, 4L, 5L).contains(usuarioRegistro.getIdRol())) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Rol no autorizado para registrar equipos.");
    }

    // 3. Crear objeto Equipos con IDs
    Equipos nuevoEquipo = new Equipos();
    nuevoEquipo.setTipoDeDispositivo(equipoRequest.getTipoDeDispositivo());
    nuevoEquipo.setMarca(equipoRequest.getMarca());
    nuevoEquipo.setNroSerie(equipoRequest.getNroSerie());
    nuevoEquipo.setDescripcion(equipoRequest.getDescripcion());
    nuevoEquipo.setIdDuenoEquipo(propietario.getIdUsuario());
    nuevoEquipo.setIdUsuarioRegistro(usuarioRegistro.getIdUsuario());

    // 4. Guardar equipo
    Equipos equipoGuardado = equipoServices.createEquipo(nuevoEquipo);

    // 5. Construir respuesta DTO
    EquipoResponseDTO response = new EquipoResponseDTO();
    response.setIdEquipo(equipoGuardado.getIdEquipo());
    response.setTipoDeDispositivo(equipoGuardado.getTipoDeDispositivo());
    response.setMarca(equipoGuardado.getMarca());
    response.setNroSerie(equipoGuardado.getNroSerie());
    response.setDescripcion(equipoGuardado.getDescripcion());

    // Propietario DTO
    PropietarioDTO propietarioDTO = new PropietarioDTO(
        propietario.getIdUsuario(),
        propietario.getNombre(),
        propietario.getCorreo()
    );
    response.setPropietario(propietarioDTO);

    // Usuario que registra DTO (puedes usar mismo DTO PropietarioDTO o uno distinto si tienes)
    PropietarioDTO registroDTO = new PropietarioDTO(
        usuarioRegistro.getIdUsuario(),
        usuarioRegistro.getNombre(),
        usuarioRegistro.getCorreo()
    );
    response.setUsuarioRegistro(registroDTO);

    // 6. Retornar respuesta con CREATED
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
}

    @Operation(summary = "Actualizar un equipo existente", description = "Actualiza los datos de un equipo por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipo actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Equipo no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Equipos> updateEquipo(@PathVariable Long id, @RequestBody Equipos equipo) {
        Equipos equipoActualizado = equipoServices.updateEquipo(id, equipo);
        if (equipoActualizado == null) {
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
    public ResponseEntity<Void> deleteEquipo(@PathVariable Long id) {
        equipoServices.deleteEquipo(id);
        return ResponseEntity.noContent().build();
    }
}
