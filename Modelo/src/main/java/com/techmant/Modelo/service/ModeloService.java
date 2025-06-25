package com.techmant.Modelo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techmant.Modelo.model.Modelo;
import com.techmant.Modelo.repository.ModeloRepository;

@Service
public class ModeloService {

    @Autowired
    private ModeloRepository modeloRepository;

    // Crear un nuevo modelo
    public Modelo crearModelo(Modelo modelo) {
        return modeloRepository.save(modelo);
    }

    // Obtener todos los modelos
    public List<Modelo> obtenerTodosLosModelos() {
        return modeloRepository.findAll();
    }

    // Buscar un modelo por ID
    public Modelo obtenerModeloPorId(Long id) {
        return modeloRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Modelo no encontrado con el ID: " + id));
    }

    // Actualizar un modelo
    public Modelo actualizarModelo(Long id, Modelo modeloActualizado) {
        if (!modeloRepository.existsById(id)) {
            throw new RuntimeException("Modelo no encontrado con el ID: " + id);
        }

        if (modeloActualizado == null) {
            throw new IllegalArgumentException("El modelo a actualizar no puede ser null");
        }

        modeloActualizado.setIdModelo(id);
        return modeloRepository.save(modeloActualizado);
    }

    // Eliminar un modelo
    public void eliminarModelo(Long id) {
        if (modeloRepository.existsById(id)) {
            modeloRepository.deleteById(id);
        } else {
            throw new RuntimeException("Modelo no encontrado con el ID: " + id);
        }
    }
}
