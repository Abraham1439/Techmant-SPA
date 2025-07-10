package com.techmant.Gestion_resena.config;

import java.text.SimpleDateFormat;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.techmant.Gestion_resena.model.Resena;
import com.techmant.Gestion_resena.repository.ResenaRepository;
import java.util.Date;

@Configuration
public class LoadDatabase {
    
        @Bean
        public CommandLineRunner precargarResenas(ResenaRepository resenaRepository) {
        return args -> {
            // Comprobar si ya existen registros en la base de datos
            if (resenaRepository.count() == 0) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                
                Date fecha1 = sdf.parse("24/06/2025");
                Date fecha2 = sdf.parse("25/06/2025");
                Date fecha3 = sdf.parse("26/06/2025");
                Date fecha4 = sdf.parse("27/06/2025");
                Date fecha5 = sdf.parse("28/06/2025");

                resenaRepository.save(new Resena(
                        null,
                        "Excelente servicio, lo recomiendo 100%.",
                        5,
                        fecha1,
                        1L  // Tiene que tener Rol 3 para que sea un cliente
                ));

                resenaRepository.save(new Resena(
                        null,
                        "Muy buen trabajo, rápido y profesional.",
                        5,
                        fecha2,
                        2L  // Tiene que tener Rol 3 para que sea un cliente
                ));

                resenaRepository.save(new Resena(
                        null,
                        "El técnico fue muy amable y resolvió todo de inmediato.",
                        5,
                        fecha3,
                        3L  // Tiene que tener Rol 3 para que sea un cliente
                ));

                resenaRepository.save(new Resena(
                        null,
                        "Servicio impecable, todo quedó funcionando perfecto.",
                        4,
                        fecha4,
                        4L  // Tiene que tener Rol 3 para que sea un cliente
                ));

                resenaRepository.save(new Resena(
                        null,
                        "Gran experiencia, definitivamente volvería a contratar.",
                        5,
                        fecha5,
                        5L  // Tiene que tener Rol 3 para que sea un cliente
                ));

                System.out.println("Reseñas de cliente precargadas exitosamente.");
            } else {
                System.out.println("Las reseñas ya están precargadas.");
            }
        };
    }
}