package com.techmant.categoria.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.techmant.categoria.model.Categoria;
import com.techmant.categoria.repository.CategoriaRepository;

@Configuration
public class LoadDataBase {
    @Bean
    CommandLineRunner initDataBase(CategoriaRepository catRepo) {
        return args -> {
            //Si no hay registros en las tablas inserto los datos 
            if(catRepo.count() == 0 ) {
                //cargar las categorias 
                Categoria diagnostico = new Categoria();
                diagnostico.setNombreCategoria("Diagnóstico");
                diagnostico.setDescripcion("Evaluación inicial del dispositivo para detectar fallas");
                catRepo.save(diagnostico);

                Categoria reparacion = new Categoria();
                reparacion.setNombreCategoria("Reparación de hardware");
                reparacion.setDescripcion("Reparación de componentes físicos (placas, pantallas, etc)");
                catRepo.save(reparacion);

                Categoria rsoftware = new Categoria();
                rsoftware.setNombreCategoria("Reparación de software");
                rsoftware.setDescripcion("Formateo, instalación de sistema operativo, eliminación de virus");
                catRepo.save(rsoftware);

                System.out.println("Datos iniciales Cargados correctamente");
            }
            else {
                System.out.println("Datos ya existentes. NO se cargaron datos nuevos");
            }
        };
    }

}
