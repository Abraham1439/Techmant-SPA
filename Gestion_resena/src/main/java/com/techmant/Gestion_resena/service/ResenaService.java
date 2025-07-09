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

    // Crear una nueva reseña con validación del usuario desde microservicio
    public Resena crearResenaConValidacion(Resena resena) {
    // Validar que el usuario existe consultando al microservicio
    Map<String, Object> usuario = usuarioCat.obtenerUsuarioPorId(resena.getIdUsuario());
    
    if (usuario == null || usuario.isEmpty()) {
        throw new RuntimeException("Usuario no encontrado. No se puede crear la reseña.");
    }

    // Si el usuario existe, se guarda la reseña
    return resenaRepository.save(resena);
}

    // Obtener todas las resenas
    public List<Resena> obtenerTodasLasResenas() {
        return resenaRepository.findAll();
    }

    // Buscar una resena por ID
    public Resena obtenerResenaPorId(Long id) {
        return resenaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Resena no encontrada con el ID: " + id));
    }

    // Actualizar una resena
    public Resena actualizarResena(Long id, Resena resenaActualizada) {
        if (!resenaRepository.existsById(id)) {
            throw new RuntimeException("Resena no encontrada con el ID: " + id);
        }

        if (resenaActualizada == null) {
            throw new IllegalArgumentException("La resena a actualizar no puede ser null");
        }

           resenaActualizada.setIdResena(id);
        return resenaRepository.save(resenaActualizada);
    }

    // Eliminar una resena
    public void eliminarResena(Long id) {
        if (resenaRepository.existsById(id)) {
            resenaRepository.deleteById(id);
        } else {
            throw new RuntimeException("Resena no encontrada con el ID: " + id);
        }
    }
}