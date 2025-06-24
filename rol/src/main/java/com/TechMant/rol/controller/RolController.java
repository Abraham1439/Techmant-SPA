package com.TechMant.rol.controller;

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

import com.TechMant.rol.model.Rol;
import com.TechMant.rol.service.RolService;

@RestController
@RequestMapping("/api/v1/rol")
public class RolController {

    @Autowired
    private RolService rolService;

    // ENDPOINT PARA TRAER TODOS LOS ROLES
    @GetMapping
    public ResponseEntity<List<Rol>> getAllRoles() {
        List<Rol> roles = rolService.getAllRoles();
        if (roles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(roles);
    }

    // ENDPOINT PARA TRAER UN ROL POR ID
    @GetMapping("/{id}")
    public ResponseEntity<Rol> getRolById(@PathVariable Long id) {
        try {
            Rol rol = rolService.getRolById(id);
            return ResponseEntity.ok(rol);
        } catch (RuntimeException ex) {
            if ("Rol no encontrado".equals(ex.getMessage())) {
                return ResponseEntity.notFound().build();
            }
            throw ex;
        }
    }

    // ENDPOINT PARA CREAR UN ROL
    @PostMapping
    public ResponseEntity<Rol> createRol(@RequestBody Rol rol) {
        Rol rolCreated = rolService.createRol(rol);
        return ResponseEntity.ok(rolCreated);
    }

    // ENDPOINT PARA ACTUALIZAR UN ROL
    @PutMapping("/{id}")
    public ResponseEntity<Rol> updateRol(@PathVariable Long id, @RequestBody Rol rol) {
        Rol rolUpdated = rolService.updateRol(id, rol);
        return ResponseEntity.ok(rolUpdated);
    }

    // ENDPOINT PARA ELIMINAR UN ROL
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRol(@PathVariable Long id) {
        rolService.deleteRol(id);
        return ResponseEntity.noContent().build();
    }

}
