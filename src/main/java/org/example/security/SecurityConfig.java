package org.example.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers("/api/auth/login").permitAll()

                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html"
                        ).permitAll()

                        .requestMatchers(HttpMethod.POST, "/api/airports")
                        .hasAnyRole("BACKOFFICE", "ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/airports/{iataCode}")
                        .hasAnyRole("BACKOFFICE", "ADMIN")

                        .requestMatchers(HttpMethod.PATCH, "/api/airports/{iataCode}/status")
                        .hasAnyRole("BACKOFFICE", "ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/airports/search")
                        .hasAnyRole("ADMIN", "BACKOFFICE")

                        .requestMatchers(HttpMethod.POST, "/api/airports/{iataCode}/certifications")
                        .hasAnyRole("BACKOFFICE", "ADMIN")


                        .requestMatchers(HttpMethod.POST, "/api/aircraft-models")
                        .hasAnyRole("BACKOFFICE", "ADMIN")

                        .requestMatchers(HttpMethod.POST, "/api/aircrafts")
                        .hasAnyRole("ADMIN", "BACKOFFICE")

                        .requestMatchers(HttpMethod.GET, "/api/aircrafts/**")
                        .hasAnyRole("BACKOFFICE", "ADMIN")

                        .requestMatchers(HttpMethod.PATCH, "/api/aircrafts/{registrationNumber}/status")
                        .hasAnyRole("ADMIN", "BACKOFFICE")

                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}