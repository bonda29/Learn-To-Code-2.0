package com.example.learntocode.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final AuthenticationErrorHandler authenticationErrorHandler;

    @Bean
    public SecurityFilterChain httpSecurity(final HttpSecurity http) throws Exception {
/*                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/messages/protected", "/api/messages/admin").authenticated()
                        .anyRequest()
                        .permitAll())*/
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(authz -> authz
                        .anyRequest()
                        .permitAll())
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(Customizer.withDefaults())
                        .authenticationEntryPoint(authenticationErrorHandler))
                .headers(headers -> headers
                        .frameOptions().disable()
                )
                .build();
    }
}
