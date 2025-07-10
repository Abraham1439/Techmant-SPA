package com.techmant.Gestion_resena.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techmant.Gestion_resena.model.Resena;
import com.techmant.Gestion_resena.repository.ResenaRepository;
import com.techmant.Gestion_resena.webusuario.UsuarioCat;

@Service
public class ResenaService {
    @Autowired
    private ResenaRepository resenaRepository;
    
    @Autowired
    private UsuarioCat usuarioCat;

      // Método para obtener todas las reseñas
    public List<Resena> getResenas() {
        return resenaRepository.findAll();
    }

    // Método para obtener una reseña por su ID
    public Resena getResenaById(Long id) {
        return resenaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Lo sentimos, la reseña no pudo ser encontrada."));
    }

    // Método para crear una nueva reseña con validación del usuario
    public Resena agregarResena(Resena nuevaResena) {
        // Validar que el usuario existe consultando al microservicio
        Map<String, Object> usuario = usuarioCat.obtenerUsuarioPorId(nuevaResena.getIdUsuario());
        if (usuario == null || usuario.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado. No se puede guardar la reseña.");
        }
        // Si el usuario existe, guardar la reseña
        return resenaRepository.save(nuevaResena);
    }

    // Método para eliminar una reseña mediante el ID
    public void eliminarResena(Long id) {
        resenaRepository.deleteById(id);
    }
}
