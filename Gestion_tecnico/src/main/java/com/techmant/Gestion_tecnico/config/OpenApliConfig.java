package com.techmant.Gestion_tecnico.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApliConfig {

      @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI().info(new Info().title("Tecnicos de TechMant-SPA").version("1.0").description("Tecnicos que se encargan de los equipos de los ususarios en TechMant SPA"));
    }

}
