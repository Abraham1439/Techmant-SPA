package com.TechMant.usuario.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.TechMant.usuario.model.Usuario;
import com.TechMant.usuario.repository.UsuarioRepository;

@Configuration
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(UsuarioRepository usuarioRepository){
        return args -> {
            if(usuarioRepository.count() == 0){
                // Cargar Usuario
                Usuario admin = new Usuario();
                admin.setNombre("Juan Ignacio");
                admin.setCorreo("juanNachi@gmail.com");
                admin.setPassword("12345");
                admin.setIdRol(1);

                usuarioRepository.save(admin);
                System.out.println("Datos Cargados con exito");

            } else {
                System.out.println("Datos ya existen. No se cargaron nuevos datos");
            }
        };
    }


}
