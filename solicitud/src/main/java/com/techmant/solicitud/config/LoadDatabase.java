package com.techmant.solicitud.config;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.techmant.solicitud.model.Solicitud;
import com.techmant.solicitud.repository.SolicitudRepository;

@Configuration
public class LoadDatabase {
     
    @Bean
    CommandLineRunner initDatabase(SolicitudRepository solicitudRepository) {
        return args -> {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            // Comprobar si ya existen registros en la base de datos
            if (solicitudRepository.count() == 0) {
                Solicitud s1 = new Solicitud(null, sdf.parse("01/07/2025"), "Instalación de software", 30000);
                Solicitud s2 = new Solicitud(null, sdf.parse("05/07/2025"), "Actualización de sistema", 45000);
                Solicitud s3 = new Solicitud(null, sdf.parse("10/07/2025"), "Aumento de memoria RAM", 60000);

                solicitudRepository.saveAll(Arrays.asList(s1, s2, s3));
                System.out.println("Datos precargados exitosamente");
            } else {
                System.out.println("Los datos ya están precargados");
            }
        };
    }
}
