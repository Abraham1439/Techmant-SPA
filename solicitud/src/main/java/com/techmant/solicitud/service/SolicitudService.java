package com.techmant.solicitud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techmant.solicitud.model.Solicitud;
import com.techmant.solicitud.repository.SolicitudRepository;

@Service
public class SolicitudService {

    @Autowired
    private SolicitudRepository solicitudRepository;

    //Crear una nueva solicitud
    public Solicitud crearSolicitud(Solicitud solicitud) {
        return solicitudRepository.save(solicitud);
    }

    //Obtener todas las solicitudes
    public List<Solicitud> obtenerTodasSolicitudes() {
        return solicitudRepository.findAll();
    }

    //Buscar una solicitud por ID
    public Solicitud obtenerSolicitudPorId(Long id) {
        return solicitudRepository.findById(id).get();
    }

    // Actualizar una solicitud
    public Solicitud actualizarSolicitud(Long id, Solicitud solicitudActualizada) {
        if (solicitudRepository.existsById(id)) {
            solicitudActualizada.setIdSolicitud(id);
            return solicitudRepository.save(solicitudActualizada);
        }
        return null; 
    }

    // Eliminar una solicitud
    public void eliminarSolicitud(Long id) {
        if (solicitudRepository.existsById(id)) {
            solicitudRepository.deleteById(id);
        }
        
    }

}
