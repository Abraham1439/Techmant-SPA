package com.techmant.solicitud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApliConfig {

      @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI().info(new Info().title("Solicitudes TechMant-SPA").version("1.0").description("Solicitudes que hacen los usuarios para las reparaciones en TechMant SPA"));
    }

}
