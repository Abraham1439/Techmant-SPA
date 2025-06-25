package com.techmant.agendamiento.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApliConfig {

    //hace que se ejecute automaticamente
    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI().info(new Info().title("Agendamiento de TechMant-SPA").version("1.0").description("Agendas que estan en TechMant SPA"));
    }
}
