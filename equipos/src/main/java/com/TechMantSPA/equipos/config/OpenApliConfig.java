package com.TechMantSPA.equipos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApliConfig {

      @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI().info(new Info().title("Equipos que se estan reparando TechMant-SPA").version("1.0").description("Equipos que se estan reparando por los tecnicos en en TechMant SPA"));
    }

}
