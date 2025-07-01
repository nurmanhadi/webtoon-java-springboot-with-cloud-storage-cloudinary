package com.nurman.webtoon.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.nurman.webtoon.helper.JwtFilter;
import com.nurman.webtoon.helper.UserRole;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Autowired
    private Environment env;

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(configurationSource()))
                .csrf(csrf -> csrf.disable())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/public/**").permitAll()
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/admin/**").hasRole(UserRole.ADMIN.toString())
                        .requestMatchers("/api/users/**")
                        .hasAnyRole(UserRole.USER.toString(), UserRole.ADMIN.toString())
                        .anyRequest().authenticated());
        return http.build();
    }

    @Bean
    public CorsConfigurationSource configurationSource() {
        return request -> {
            var config = new CorsConfiguration();
            config.setAllowedOrigins(List.of(env.getProperty("origin.url")));
            config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
            config.setAllowedHeaders(List.of("Content-Type", "Authorization"));
            config.setExposedHeaders(List.of("X-Custom-Header"));
            config.setAllowCredentials(true);
            return config;
        };
    }
}
