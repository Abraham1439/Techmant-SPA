package com.TechMant.usuario.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.TechMant.usuario.model.Usuario;
import com.TechMant.usuario.repository.UsuarioRepository;

@Configuration
public class LoadDatabase {

    @Bean
CommandLineRunner initDatabase(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
    return args -> {
        if (usuarioRepository.count() == 0) {
            // ✅ Admin
            Usuario admin = new Usuario();
            admin.setNombre("Juan Ignacio");
            admin.setCorreo("juanNachi@gmail.com");
            admin.setPassword(passwordEncoder.encode("12345"));
            admin.setIdRol(1);
            usuarioRepository.save(admin);

            // ✅ Técnico de Servicio
            Usuario tecnicoServicio = new Usuario();
            tecnicoServicio.setNombre("Camila Torres");
            tecnicoServicio.setCorreo("camila.torres@example.com");
            tecnicoServicio.setPassword(passwordEncoder.encode("12345"));
            tecnicoServicio.setIdRol(2);
            usuarioRepository.save(tecnicoServicio);

            // ✅ Cliente
            Usuario cliente = new Usuario();
            cliente.setNombre("Renato Morales");
            cliente.setCorreo("renato.morales@example.com");
            cliente.setPassword(passwordEncoder.encode("12345"));
            cliente.setIdRol(3);
            usuarioRepository.save(cliente);

            // ✅ Soporte Técnico
            Usuario soporteTecnico = new Usuario();
            soporteTecnico.setNombre("Valentina Reyes");
            soporteTecnico.setCorreo("valentina.reyes@example.com");
            soporteTecnico.setPassword(passwordEncoder.encode("12345"));
            soporteTecnico.setIdRol(4);
            usuarioRepository.save(soporteTecnico);

            // ✅ Supervisor Técnico
            Usuario supervisorTecnico = new Usuario();
            supervisorTecnico.setNombre("Cristóbal Vera");
            supervisorTecnico.setCorreo("cristobal.vera@example.com");
            supervisorTecnico.setPassword(passwordEncoder.encode("12345"));
            supervisorTecnico.setIdRol(5);
            usuarioRepository.save(supervisorTecnico);

            System.out.println(" Usuarios de prueba cargados con éxito.");
        } else {
            System.out.println("ℹ Los usuarios ya existen. No se cargaron nuevos datos.");
        }
    };
};

}
