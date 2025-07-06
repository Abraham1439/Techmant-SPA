package com.techmant.Gestion_tecnico.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.techmant.Gestion_tecnico.model.Tecnico;
import com.techmant.Gestion_tecnico.repository.TecnicoRepository;

@Configuration
public class LoadDatabase {
    @Bean
    public CommandLineRunner precargarTecnicos(TecnicoRepository tecnicoRepository) {
        return args -> {
            if (tecnicoRepository.count() == 0) {
                tecnicoRepository.save(new Tecnico(null, "Juan López", "Reparación de hardware"));
                tecnicoRepository.save(new Tecnico(null, "Ana Torres", "Instalación de software"));
                tecnicoRepository.save(new Tecnico(null, "Pedro Sánchez", "Soporte técnico general"));
                System.out.println("Datos de técnicos precargados exitosamente.");
            }
        };
    }
}

