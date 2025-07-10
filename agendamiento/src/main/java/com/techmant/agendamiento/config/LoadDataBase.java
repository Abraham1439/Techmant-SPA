package com.techmant.agendamiento.config;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import com.techmant.agendamiento.model.Agendamiento;
import com.techmant.agendamiento.service.AgendamientoService;

@Configuration
public class LoadDataBase {

    @Bean
    CommandLineRunner initDataBase(AgendamientoService agendamientoService) {
        return args -> {
            if (agendamientoService.getAgendamientos().isEmpty()) {
                try {

                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    Date fecha1 = formatter.parse("08/07/2025");

                    //Primer agendamiento
                    Agendamiento ag1 = new Agendamiento();
                    ag1.setEstado("pendiente");
                    ag1.setFechaCita(fecha1);
                    ag1.setObservaciones("Instalación urgente");
                    ag1.setIdUsuario(3L); //este ID debe existir en el microservicio de Usuario sino f 
                    agendamientoService.agregarAgendamiento(ag1);

                    System.out.println("Asignación precargada correctamente.");

                }catch (Exception e) {
                    System.err.println("Error al precargar asignación: " + e.getMessage());
                }
            } else {
                System.out.println("Datos ya existentes. No se cargaron datos nuevos! :).");
            }
        };
    }

}
