package com.TechMant.Asignacion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TechMant.Asignacion.model.Asignacion;
import com.TechMant.Asignacion.repository.AsignacionRepository;

@Service
public class AsignacionService {
    @Autowired
    private AsignacionRepository asignacionRepository;
    
    //metodo para ver las asignaciones
    public List<Asignacion> obtenerAsignaciones() {
        return asignacionRepository.findAll();
    }

    //metodo para ver una asignacion mediente el id 
    public Asignacion obtenerAsignacionporId(Long id) {
        return asignacionRepository.findById(id).orElseThrow(() -> new RuntimeException("No hay asignaciones hasta el momento"));
    }

    //Metodo para hacer una asignacion 
    public Asignacion agregarAsignacion(Asignacion asignacion) {
        return asignacionRepository.save(asignacion);
    }


}
