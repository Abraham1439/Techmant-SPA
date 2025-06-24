package com.TechMant.usuario.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.TechMant.usuario.client.RolServiceClient;
import com.TechMant.usuario.dto.RolDTO;
import com.TechMant.usuario.model.Usuario;

import com.TechMant.usuario.service.UsuarioService;




@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RolServiceClient rolServiceClient;
    
    

    // ENDPOINT para traer a todos los usuarios
    @GetMapping
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        List<Usuario> usuarios = usuarioService.getAllUsuarios();
        if(usuarios.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    // ENDPOINT para traer a un usuario pro ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUserById(@PathVariable Long id){
        Usuario usuario = usuarioService.getUsuarioById(id);
        if(usuario == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario);
    }

    // ENDPOINT para traer a los usuarios por rol
    @GetMapping("/rol/{id}")
    public ResponseEntity<List<Usuario>> getAllUsuariosByRol(@PathVariable Integer id){
        List<Usuario> usuarios = usuarioService.getAllUsuariosByRol(id);
        if(usuarios.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }
    // ENDPOINT para crear un nuevo usuario
    @PostMapping
    public ResponseEntity<?> createUsuario(@RequestBody Usuario usuario, @RequestParam Integer idRolSolicitante) {
        // Validar si quien intenta crear el usuario tiene idRol = 1
        if(idRolSolicitante != 1){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permisos para crear usuarios");
        }
        RolDTO rol = rolServiceClient.getRolById(usuario.getIdRol());
        if(rol == null){
            return ResponseEntity.badRequest().body("Error: el rol con ID "+usuario.getIdRol() + " no existe");

        }
        usuarioService.createUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }


    // ENDPOINT para actualizar un usuario existente
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable Long id, @RequestBody Usuario usuario){
        Usuario updatedUsuario = usuarioService.updateUsuario(id, usuario);
        return ResponseEntity.ok(updatedUsuario);
    }

    // ENDPOINT para eliminar un usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id){
        usuarioService.deleteUsuario(id);
        return ResponseEntity.ok().build();
    }
}
