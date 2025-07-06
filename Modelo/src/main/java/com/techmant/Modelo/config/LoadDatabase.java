package com.techmant.Modelo.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.techmant.Modelo.model.Modelo;
import com.techmant.Modelo.repository.ModeloRepository;

@Configuration
public class LoadDatabase {
    
    @Bean
    public CommandLineRunner precargarModelos(ModeloRepository modeloRepository) {
        return args -> {
            if (modeloRepository.count() == 0) {
                modeloRepository.save(new Modelo(
                        null,
                        "Samsung",
                        "SN123456789XYZ"
                ));

                modeloRepository.save(new Modelo(
                        null,
                        "Apple",
                        "SN987654321ABC"
                ));

                modeloRepository.save(new Modelo(
                        null,
                        "Dell",
                        "SN456789123DEF"
                ));

                modeloRepository.save(new Modelo(
                        null,
                        "HP",
                        "SN654321987GHI"
                ));

                modeloRepository.save(new Modelo(
                        null,
                        "Lenovo",
                        "SN321987654JKL"
                ));

                System.out.println("Modelos precargados exitosamente.");
            }
        };
    }
}

