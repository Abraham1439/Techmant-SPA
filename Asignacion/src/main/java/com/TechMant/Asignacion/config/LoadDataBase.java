package com.TechMant.Asignacion.config;

import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.TechMant.Asignacion.model.Asignacion;
//import com.TechMant.Asignacion.repository.AsignacionRepository;
import com.TechMant.Asignacion.service.AsignacionService;

@Configuration
public class LoadDataBase {

    CommandLineRunner initDataBase(AsignacionService asignacionService) {
        return args -> {
            if (asignacionService.obtenerAsignaciones().isEmpty()) {
                try {
                    Asignacion asignacion = new Asignacion();
                    asignacion.setNombreAsignado("Juan López");
                    asignacion.setNombreServicio("Reparación de hardware");
                    asignacion.setIdTecnico(3L); // debe existir en Gestion_tecnico

                    asignacionService.saveAsignacion(asignacion);

                    System.out.println("Asignación precargada correctamente.");

                } catch (Exception e) {
                    System.err.println("Error al precargar asignación: " + e.getMessage());
                }
            } else {
                System.out.println("Datos ya existentes. No se cargaron datos nuevos.");
            }
        };
    }

}
