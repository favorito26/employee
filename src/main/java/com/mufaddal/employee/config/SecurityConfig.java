package com.mufaddal.employee.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Enable CORS
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // Disable CSRF for stateless API
                .csrf(csrf -> csrf.disable())
                // Configure authorization
                .authorizeHttpRequests(auth -> auth
                        // Allow public access to API endpoints
                        .requestMatchers("/api/employee", "/api/employees", "/api/employee/**").permitAll()
                        // Allow root endpoint (optional, for testing)
                        .requestMatchers("/").permitAll()
                        // Require authentication for all other endpoints
                        .anyRequest().authenticated()
                )
                // Enable HTTP Basic Auth for protected endpoints
                .httpBasic();
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:5173");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(false);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}