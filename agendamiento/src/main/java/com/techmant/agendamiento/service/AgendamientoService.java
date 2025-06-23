package com.techmant.agendamiento.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techmant.agendamiento.model.Agendamiento;
import com.techmant.agendamiento.repository.AgendamientoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AgendamientoService {

    @Autowired
    private AgendamientoRepository agendamientoRepository;

    //Metodo para obtener todas las agendas 
    public List<Agendamiento> getAgendamientos() {
        return agendamientoRepository.findAll();
    }

    //Metodo para obtener un agendamiento por su ID
    public Agendamiento getAgendamientoById(Long id) {
        return agendamientoRepository.findById(id).orElseThrow(() -> new RuntimeException("Lo sentimos la agenda no pudo ser encontrada."));
    }

    //Metodo para crear una agenda 
    public Agendamiento createAgendamiento(Agendamiento agendamiento) {
        return agendamientoRepository.save(agendamiento);
    }


    //Metodo para eliminar una agenda mediante el ID
    public void eliminarAgendamiento(Long id) {
        agendamientoRepository.deleteById(id);
    }

}
