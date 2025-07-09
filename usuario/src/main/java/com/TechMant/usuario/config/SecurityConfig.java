package com.TechMant.usuario.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    private static final String ADMIN = "admin";
    private static final String PASSWORD = "Admin123$";

    // Usuarios en memoria para autenticación básica
    @Bean
    public InMemoryUserDetailsManager userDetailsService(){
        UserDetails admin = User.withUsername(ADMIN)
                .password(passwordEncoder().encode(PASSWORD))
                .roles("ADMIN")  // rol ADMIN
                .build();

        UserDetails user = User.withUsername("user")
                .password(passwordEncoder().encode("user123"))
                .roles("USER")   // rol USER común
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Para pruebas POST sin token CSRF
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**",
                "/swagger-ui.html",
                "/swagger-ui/**",
                "/v3/api-docs/**").permitAll()  // permitir login o registro público
                .anyRequest().authenticated()                  // resto requiere autenticación
            )
            .httpBasic(Customizer.withDefaults()); // Usa autenticación básica (username/password)

        return http.build();
    }


}
