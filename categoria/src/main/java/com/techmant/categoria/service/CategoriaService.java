package com.techmant.categoria.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techmant.categoria.model.Categoria;
import com.techmant.categoria.repository.CategoriaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional

public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    //Metodo para ver todas las categorias del servicio 
    public List<Categoria> obtenCategorias() {
        return categoriaRepository.findAll();
    }

    //Metodo para ver una categortia mediante su id 
    public Categoria obtenerCategoriaPorId(Long id) {
        return categoriaRepository.findById(id).orElseThrow(() -> new RuntimeException("No se logro encontrar el servicio seleccionado :("));
    }

    //Metodo para aliminar una categoria por su id
    public void elminarCategoria(Long id) {
        categoriaRepository.deleteById(id);
    }


    //Metodo para agregar un nuevo categoria
    public Categoria agregarCategoria(Categoria categoria){
        return categoriaRepository.save(categoria);
    } 

}
