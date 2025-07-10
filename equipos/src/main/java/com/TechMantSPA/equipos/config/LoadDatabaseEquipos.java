package com.TechMantSPA.equipos.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.TechMantSPA.equipos.model.Equipos;
import com.TechMantSPA.equipos.repository.EquiposRepository;

@Configuration
public class LoadDatabaseEquipos {

    @Bean
    CommandLineRunner initDataBase(EquiposRepository equiposRepository){
        return args -> {
            if(equiposRepository.count() == 0){
                // Equipo 1
                Equipos equipo1 = new Equipos();
                equipo1.setTipoDeDispositivo("Laptop");
                equipo1.setMarca("Dell");
                equipo1.setNroSerie("ABC123");
                equipo1.setDescripcion("Laptop de oficina para mantenimiento");
                equipo1.setIdDuenoEquipo(3L);
                equipo1.setIdUsuarioRegistro(1L);
                equiposRepository.save(equipo1);

                // Equipo 2
                Equipos equipo2 = new Equipos();
                equipo2.setTipoDeDispositivo("Impresora");
                equipo2.setMarca("HP LaserJet");
                equipo2.setNroSerie("XYZ789012");
                equipo2.setDescripcion("Impresora con problemas de conexión");
                equipo2.setIdDuenoEquipo(3L); // Cliente
                equipo2.setIdUsuarioRegistro(2L); // Técnico de servicio
                equiposRepository.save(equipo2);

                // Equipo 3
                Equipos equipo3 = new Equipos();
                equipo3.setTipoDeDispositivo("PC Escritorio");
                equipo3.setMarca("Lenovo");
                equipo3.setNroSerie("DEF456789");
                equipo3.setDescripcion("PC para revisión de hardware");
                equipo3.setIdDuenoEquipo(3L); // Cliente
                equipo3.setIdUsuarioRegistro(4L); // Soporte técnico
                equiposRepository.save(equipo3);

                System.out.println("Equipos de prueba cargados con éxito.");
            } else {
                System.out.println("Los equipos ya existen. No se cargaron nuevos datos");
            };
        };
    }
}
