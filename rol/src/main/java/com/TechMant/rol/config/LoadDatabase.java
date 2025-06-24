package com.TechMant.rol.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.TechMant.rol.model.Rol;
import com.TechMant.rol.repository.RolRepository;

@Configuration
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(RolRepository rolrepo) {
        return args -> {
            //si no hay registros en las tablas se insertan los datos de defecto
            if(rolrepo.count() == 0) {

                //cargar los roles 
                Rol admin = new Rol();
                admin.setNombreRol("Administrador");
                rolrepo.save(admin);

                Rol tecnico = new Rol();
                tecnico.setNombreRol("Tecnico de servicio");
                rolrepo.save(tecnico);

                Rol cliente = new Rol ();
                cliente.setNombreRol("Cliente");
                rolrepo.save(cliente);

                Rol soporte = new Rol();
                soporte.setNombreRol("Soporte Tecnico");
                rolrepo.save(soporte);

                Rol supervisor = new Rol();
                supervisor.setNombreRol("Supervisor Tecnico");
                rolrepo.save(supervisor);
            }
            else{
                System.out.println("Datos ya existen. No se cargaron nuevos datos");
            }
        };
    }

}
