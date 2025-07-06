package com.TechMant.Asignacion.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.TechMant.Asignacion.model.Asignacion;
import com.TechMant.Asignacion.repository.AsignacionRepository;

@Configuration
public class LoadDataBase {

    @Bean
    CommandLineRunner initDataBase(AsignacionRepository asigRepo) {
        return args -> {
            //Si no hay registros en las tablas inserto los datos
            if(asigRepo.count() == 0) {
                //cargar las Asignaciones
                Asignacion tec =    new Asignacion();
                tec.setNombreAsignado("Juan López");
                tec.setNombreServicio("Reparacion de harware");
                tec.setIdTecnico((long)1);
                asigRepo.save(tec);
            }
            else {
                System.out.println("Datos ya existentes. NO se cargaron datos nuevos");
            }
        };
    }

}
