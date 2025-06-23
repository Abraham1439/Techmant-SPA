package com.techmant.servicio.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techmant.servicio.model.Servicio;
import com.techmant.servicio.repository.ServicioRepository;
import com.techmant.servicio.webcategoria.CategoriaCat;

import jakarta.transaction.Transactional;

@Service
@Transactional

public class ServicioService {

    @Autowired
    private ServicioRepository servicioRepository;
    @Autowired
    private CategoriaCat categoriaCat;

    //metodo para ver los servicios echos a los dispositivos 
    public List<Servicio> obtenerServicios() {  
        return  servicioRepository.findAll();
    }


    //metodo para traer un servicio por su id :)
    public Servicio obtenerServicioPorId(Long id) {
        return servicioRepository.findById(id).orElseThrow(() -> new RuntimeException("Lo sentimos no se logro encontrar el servicio con ID:" +id));
    }


    //Metodo para guardar un nuevo servicio(Con conexion)
    public Servicio saveServicio (Servicio nuevoServicio) {
        //verificar si la categoria existe 
        //para eso me comunico con el microservicio de categoria 
        Map<String, Object> categoria = categoriaCat.obtenerCategoriaPorId(nuevoServicio.getIdCategoria());
        if(categoria == null || categoria.isEmpty()) {
            // si no se consigue la categoria mediante el metodo get del otro microservicio
            throw new RuntimeException("categoria no encontrada. No se puede guardar el servicio");
        }
        //si se encuentra la categoria
        return servicioRepository.save(nuevoServicio);
    }

    
    //Metodo para eliminar un servicio 
    public void eliminarServicioPorId(Long id) {
        //verificar si es que el servicio existe 
        Servicio servicio = servicioRepository.findById(id).orElseThrow(()-> new RuntimeException("No se encontró el servicio con ID: " + id));
        //si el servicio existe se elimina 
        servicioRepository.deleteById(id);
    }


}
