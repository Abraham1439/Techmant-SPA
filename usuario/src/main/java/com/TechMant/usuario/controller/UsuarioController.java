package com.TechMant.usuario.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TechMant.usuario.dto.LoginRequest;
import com.TechMant.usuario.dto.LoginResponse;
import com.TechMant.usuario.model.Usuario;
import com.TechMant.usuario.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/usuarios")
@Tag(name = "Usuarios", description = "Operaciones relacionadas con la gestión de usuarios del sistema")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Obtener todos los usuarios", description = "Retorna una lista de todos los usuarios registrados en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida exitosamente"),
            @ApiResponse(responseCode = "204", description = "No hay usuarios registrados")
    })
    @GetMapping
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        List<Usuario> usuarios = usuarioService.getAllUsuarios();
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @Operation(summary = "Obtener un usuario por ID", description = "Retorna un usuario específico según su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario obtenido exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUserById(@PathVariable Long id) {
        Usuario usuario = usuarioService.getUsuarioById(id);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario);
    }

    @Operation(summary = "Obtener usuarios por rol", description = "Retorna una lista de usuarios según su rol")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida exitosamente"),
            @ApiResponse(responseCode = "204", description = "No hay usuarios con el rol especificado")
    })
    @GetMapping("/rol/{id}")
    public ResponseEntity<List<Usuario>> getAllUsuariosByRol(@PathVariable Integer id) {
        List<Usuario> usuarios = usuarioService.getAllUsuariosByRol(id);
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @Operation(summary = "Crear un nuevo usuario", description = "Crea un nuevo usuario en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente"),
            @ApiResponse(responseCode = "403", description = "No tiene permisos para crear usuarios"),
            @ApiResponse(responseCode = "400", description = "Error en la creación del usuario")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario creado = usuarioService.createUsuario(usuario);
            creado.setPassword(null); // No exponer la contraseña
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear usuario: " + e.getMessage());
        }
    }
    @Operation(summary = "Iniciar sesión", description = "Inicia sesión con un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sesión iniciada exitosamente"),
            @ApiResponse(responseCode = "403", description = "No tiene permisos para iniciar sesión"),
            @ApiResponse(responseCode = "500", description = "Error de servidor")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            LoginResponse response = usuarioService.login(loginRequest);
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al iniciar sesión: " + e.getMessage());
        }
    }

    @Operation(summary = "Actualizar un usuario existente", description = "Actualiza un usuario específico en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        try {
            Usuario updatedUsuario = usuarioService.updateUsuario(id, usuario);
            return ResponseEntity.ok(updatedUsuario);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar usuario: " + e.getMessage());
        }
    }

    @Operation(summary = "Eliminar un usuario", description = "Elimina un usuario específico en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        usuarioService.deleteUsuario(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Obtener un usuario por correo", description = "Retorna un usuario específico según su correo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario obtenido exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/correo/{correo}")
public ResponseEntity<Usuario> getUsuarioByCorreo(@PathVariable String correo) {
    Optional<Usuario> usuarioOpt = usuarioService.findByCorreo(correo);
    if (usuarioOpt.isPresent()) {
        Usuario usuario = usuarioOpt.get();
        usuario.setPassword(null); // No devolver contraseña
        return ResponseEntity.ok(usuario);
    }
    return ResponseEntity.notFound().build();
}
}
