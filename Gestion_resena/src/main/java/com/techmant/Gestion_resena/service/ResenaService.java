package com.techmant.Gestion_resena.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techmant.Gestion_resena.model.Resena;
import com.techmant.Gestion_resena.repository.ResenaRepository;

@Service
public class ResenaService {
    @Autowired
    private ResenaRepository resenaRepository;

    // Crear una nueva resena
    public Resena crearResena(Resena resena) {
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