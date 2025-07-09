package com.TechMant.usuario.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.TechMant.usuario.client.RolServiceClient;
import com.TechMant.usuario.dto.LoginRequest;
import com.TechMant.usuario.dto.LoginResponse;
import com.TechMant.usuario.dto.RolDTO;
import com.TechMant.usuario.model.Usuario;
import com.TechMant.usuario.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolServiceClient rolServiceClient;
    @Autowired
    private PasswordEncoder passwordEncoder;
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
    public Usuario createUsuario(Usuario usuario){
        if(usuarioRepository.existsByCorreo(usuario.getCorreo())){
            throw new IllegalArgumentException("Correo ya registrado");
        }
        if(usuario.getPassword().length() < 4 || usuario.getPassword().length() > 16){
            throw new BadCredentialsException("La clave debe tener entre 4 y 16 caracteres");
        }
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        return usuarioRepository.save(usuario);
    }

    // Validar inicio de sesión
    public LoginResponse login(LoginRequest loginRequest){
        String correo = loginRequest.getCorreo();
        String password = loginRequest.getPassword();

        Usuario usuario = usuarioRepository.findByCorreo(correo).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        if(!passwordEncoder.matches(password, usuario.getPassword())){
            throw new BadCredentialsException("Correo o contraseña incorrectos");
        }

        RolDTO rolDTO = rolServiceClient.getRolById(usuario.getIdRol());

        String nombreRol = rolDTO != null ? rolDTO.getNombreRol() : "Rol no encontrado";

        LoginResponse response = new LoginResponse();
        response.setIdUsuario(usuario.getIdUsuario());
        response.setNombre(usuario.getNombre());
        response.setCorreo(usuario.getCorreo());
        response.setRol(nombreRol);
        return response;
    }

    // Método para actualizar un usuario existente
    public Usuario updateUsuario(Long id, Usuario usuario) {
        Usuario existingUsuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    
        // Validar si el correo fue cambiado
        if (!existingUsuario.getCorreo().equals(usuario.getCorreo())) {
            Optional<Usuario> usuarioConCorreo = usuarioRepository.findByCorreo(usuario.getCorreo());
    
            if (usuarioConCorreo.isPresent() && !usuarioConCorreo.get().getIdUsuario().equals(id)) {
                throw new IllegalArgumentException("Correo ya registrado por otro usuario");
            }
        }
    
        existingUsuario.setNombre(usuario.getNombre());
        existingUsuario.setCorreo(usuario.getCorreo());
        existingUsuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(existingUsuario);
    }
    // Método para eliminar un usuario
    public void deleteUsuario(Long id) {
        Usuario existingUsuario = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuarioRepository.delete(existingUsuario);
    }

}
