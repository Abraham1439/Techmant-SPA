package com.example.ticket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApliConfig {

      @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI().info(new Info().title("Ticket TechMant-SPA").version("1.0").description("Tickes que tienen los tecnicos en TechMant SPA"));
    }

}
