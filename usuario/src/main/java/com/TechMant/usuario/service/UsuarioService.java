package com.TechMant.usuario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.TechMant.usuario.model.Usuario;
import com.TechMant.usuario.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;


    // Método para traer a todos los usuarios
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    public List<Usuario> getAllUsuariosByRol(Integer idRol){
        return usuarioRepository.findByIdRol(idRol);
    }


    // Método para traer a un usuario por su ID
    public Usuario getUsuarioById(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    // Método para crear un nuevo usuario
    public Usuario createUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // Método para actualizar un usuario existente
    public Usuario updateUsuario(Long id, Usuario usuario) {
        Usuario existingUsuario = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        existingUsuario.setNombre(usuario.getNombre());
        existingUsuario.setCorreo(usuario.getCorreo());
        existingUsuario.setPassword(usuario.getPassword());
        return usuarioRepository.save(existingUsuario);
    }

    // Método para eliminar un usuario
    public void deleteUsuario(Long id) {
        Usuario existingUsuario = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuarioRepository.delete(existingUsuario);
    }

}
