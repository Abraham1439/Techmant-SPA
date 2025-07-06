package com.TechMant.Asignacion.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.TechMant.Asignacion.model.Asignacion;
import com.TechMant.Asignacion.service.AsignacionService;

@Configuration
public class LoadDataBase {

    @Bean
    CommandLineRunner initDataBase(AsignacionService asignacionService) {
        return args -> {
            if (asignacionService.obtenerAsignaciones().isEmpty()) {
                try {
                    Asignacion asignacion = new Asignacion();
                    asignacion.setNombreAsignado("Juan López");
                    asignacion.setNombreServicio("Reparación de hardware");
                    asignacion.setIdTecnico(1L); // debe existir en Gestion_tecnico
                    asignacionService.saveAsignacion(asignacion);

                    Asignacion asignacion2 = new Asignacion();
                    asignacion2.setNombreAsignado("Anna Torres");
                    asignacion2.setNombreServicio("Instalacion de software");
                    asignacion2.setIdTecnico(2L); // debe existir en Gestion_tecnico
                    asignacionService.saveAsignacion(asignacion2);

                    Asignacion asignacion3 = new Asignacion();
                    asignacion3.setNombreAsignado("Pedro Sánchezll");
                    asignacion3.setNombreServicio("Soporte técnico general");
                    asignacion3.setIdTecnico(3L); // debe existir en Gestion_tecnico
                    asignacionService.saveAsignacion(asignacion3);
                    

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
