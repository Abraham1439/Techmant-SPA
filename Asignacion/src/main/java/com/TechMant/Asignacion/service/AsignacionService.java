package com.TechMant.Asignacion.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TechMant.Asignacion.model.Asignacion;
import com.TechMant.Asignacion.repository.AsignacionRepository;
import com.TechMant.Asignacion.webtecnico.TecnicoTec;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AsignacionService {
    @Autowired
    private AsignacionRepository asignacionRepository;
    @Autowired
    private TecnicoTec tecnicoTec;
    
    //metodo para ver las asignaciones
    public List<Asignacion> obtenerAsignaciones() {
        return asignacionRepository.findAll();
    }

    //metodo para ver una asignacion mediente el id 
    public Asignacion obtenerAsignacionporId(Long id) {
        return asignacionRepository.findById(id).orElseThrow(() -> new RuntimeException("No hay asignaciones hasta el momento"));
    }


    //Metodo para guardar un nueva Asignacion(Con conexion)
    public Asignacion saveAsignacion(Asignacion nuevaAsignacion) {
        //verificar si la categoria existe 
        //para eso me comunico con el microservicio de categoria
        Map<String, Object> tecnico = tecnicoTec.obtenerTecnicoPorId(nuevaAsignacion.getIdTecnico());
        if(tecnico == null) {
            //si no se consigue la categoria mediante el metodo get del otro microservicio
            throw new RuntimeException("Tecnico no encontrado. No se puede guardar la asignacion");
        }
        //si se encuentra el tecnico
        return asignacionRepository.save(nuevaAsignacion);
    }



    //Metodo para eliminar una Asignacion 
    public void eliminarAsignacionPorId(Long id) {
        //verificar si es que el servicio existe
        Asignacion asignacion = asignacionRepository.findById(id).orElseThrow(()-> new RuntimeException("No se encontró el servicio con ID: " + id));
        //si el servicio existe se elimina 
        asignacionRepository.deleteById(id);
    }

}
