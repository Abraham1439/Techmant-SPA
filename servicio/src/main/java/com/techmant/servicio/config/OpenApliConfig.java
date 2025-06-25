package com.techmant.servicio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApliConfig {

      @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI().info(new Info().title("Servicios TechMant-SPA").version("1.0").description("Tipos de servicios que se ofrecen en TechMant SPA"));
    }

}
