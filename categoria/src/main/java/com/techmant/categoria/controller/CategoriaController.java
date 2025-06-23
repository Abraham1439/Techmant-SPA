package com.techmant.categoria.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techmant.categoria.model.Categoria;
import com.techmant.categoria.service.CategoriaService;

@RestController
@RequestMapping("api/v1/categoria")

public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;


    //Metodo para obtener todas las categorias del servicio
    @GetMapping
    public ResponseEntity<List<Categoria>> listarCategorias() {
        //variable para almacenar el resultado del select 
        List<Categoria> categorias = categoriaService.obtenCategorias();
        //Pregunta si vienen registros o no 
        if (categorias.isEmpty()) {
            //si esta vacia pone el codigo 204
            return ResponseEntity.noContent().build();
        }
        //si esta no esta vacia 
        return ResponseEntity.ok(categorias);
    }

    //metodo para agregar categorias
    @PostMapping
    public ResponseEntity<Categoria> agregarCategoria(@RequestBody Categoria cat) {
        // variable para almacenar el retorno del servicio
        Categoria categoria2 = categoriaService.agregarCategoria(cat);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoria2);
    }


    //Metodo para buscar por su id 
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> buscarPorId(@PathVariable Long id) {
        try {
            Categoria categoria2 = categoriaService.obtenerCategoriaPorId(id);
            return ResponseEntity.ok(categoria2);
        }
        catch (Exception e){
            return ResponseEntity.notFound().build();
        }

    }

    //Metodo para eliminar una categoria en espesifico (por su ID)
    @DeleteMapping("/{id}")
    public  ResponseEntity<?> eliminarCategoria(@PathVariable long id) {
        try {
            categoriaService.elminarCategoria(id);
            return ResponseEntity.noContent().build();
        }catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    //Metodo para modificar una categoria 
    @PutMapping("/{id}")
    public ResponseEntity<Categoria> modificarCategoria (@PathVariable Long id, @RequestBody Categoria cat) {
        try {
            //Creamos un objeto para buscar la categoria que queremos modificar
            Categoria categoria2 = categoriaService.obtenerCategoriaPorId(id);
            //si este existe modificamos los datos
            categoria2.setIdCategoria(id);
            categoria2.setDescripcion(cat.getDescripcion());
            categoria2.setNombreCategoria(cat.getNombreCategoria());
            //Actaliza la categoria
            categoriaService.agregarCategoria(categoria2);
            return ResponseEntity.ok(categoria2);
        }catch (Exception e) {
            // Si la categoria no existe
            throw new RuntimeException("Categoria con ID:" + id + "no encontrada intente nuevamente con otra porfavor.");
        }
    }


}
