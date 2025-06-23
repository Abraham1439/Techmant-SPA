package com.techmant.servicio.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.techmant.servicio.model.Servicio;
import com.techmant.servicio.repository.ServicioRepository;

@Configuration
public class LoadDataBase {
    @Bean
    CommandLineRunner initDataBase(ServicioRepository serviRepo) {
        return args -> {
            //si no hay registros en las tablas inserto los de defecto
            if(serviRepo.count() == 0 ) {
                //cargar los servicios
                Servicio formateo = new Servicio();
                formateo.setNombreServicio("Formateo de notebook");
                formateo.setDescripcion("Reinstalación del sistema operativo, drivers y software básico");
                formateo.setPrecio(15000);
                formateo.setIdCategoria((long) 7);
                serviRepo.save(formateo);

                Servicio cambio = new Servicio();
                cambio.setNombreServicio("Cambio de pantalla");
                cambio.setDescripcion("Reemplazo de pantalla LCD o LED dañada en notebook");
                cambio.setPrecio(35000);
                cambio.setIdCategoria((long) 6);
                serviRepo.save(cambio);

                Servicio diagnostico = new Servicio();
                diagnostico.setNombreServicio("Diagnóstico completo");
                diagnostico.setDescripcion("Revisión técnica general para detectar fallas o problemas");
                diagnostico.setPrecio(25000);
                diagnostico.setIdCategoria((long) 5);
                serviRepo.save(diagnostico);

                System.out.println("Datos iniciales Cargados correctamente");

            }
            else {
                System.out.println("Datos ya existentes. NO se cargaron datos nuevos");
            }
        };
    }

}

