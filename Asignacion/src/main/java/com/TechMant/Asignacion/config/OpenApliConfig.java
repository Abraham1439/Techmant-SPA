package com.TechMant.Asignacion.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApliConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI().info(new Info().title("Asignaciones TechMant-SPA").version("1.0").description("Asignaciones de los servicios que estan asociados a los tecnicos en TechMant SPA"));
    }

}
