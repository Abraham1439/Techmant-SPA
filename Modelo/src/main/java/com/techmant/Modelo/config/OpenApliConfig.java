package com.techmant.Modelo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApliConfig {

      @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI().info(new Info().title("Modelos de los dispositivos TechMant-SPA").version("1.0").description("Tipos de modelos que tienen los diferentes equipos en TechMant SPA"));
    }

}
