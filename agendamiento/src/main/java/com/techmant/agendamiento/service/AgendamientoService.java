package com.techmant.agendamiento.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techmant.agendamiento.model.Agendamiento;
import com.techmant.agendamiento.repository.AgendamientoRepository;
import com.techmant.agendamiento.websolicitud.SolicitudCat;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AgendamientoService {

    @Autowired
    private AgendamientoRepository agendamientoRepository;
    @Autowired
    private SolicitudCat solicitudCat;

    //Metodo para obtener todas las agendas 
    public List<Agendamiento> getAgendamientos() {
        return agendamientoRepository.findAll();
    }

    //Metodo para obtener un agendamiento por su ID
    public Agendamiento getAgendamientoById(Long id) {
        return agendamientoRepository.findById(id).orElseThrow(() -> new RuntimeException("Lo sentimos la agenda no pudo ser encontrada."));
    }

     //Metodo para crear una agenda (Con conexion)
    public Agendamiento agregarAgendamiento (Agendamiento nuevoAgendamiento) {
        //verificar si la categoria existe 
        //para eso me comunico con el microservicio de categoria 
        Map<String, Object> solicitud = solicitudCat.obtenerSolicitudPorId(nuevoAgendamiento.getIdSolicitud());
        if(solicitud == null || solicitud.isEmpty()) {
            //si no se consigue la categoria mediante el metodo get del otro microservicio
            throw new RuntimeException("Solicutud no encontrada. No se puede guardar la agenda");
        }
        //si se encuentra la categoria
        return agendamientoRepository.save(nuevoAgendamiento);
    }


    //Metodo para eliminar una agenda mediante el ID
    public void eliminarAgendamiento(Long id) {
        agendamientoRepository.deleteById(id);
    }

}
