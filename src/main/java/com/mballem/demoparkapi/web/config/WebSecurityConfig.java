package com.mballem.demoparkapi.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;
import java.util.List;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.mballem.demoparkapi.web.jwt.JwtAuthenticationFilter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

  @Bean
  public DefaultSecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
      // Habilita CORS básico
      .cors(cors -> cors.configurationSource(corsConfigurationSource()))
      // Desabilita CSRF para APIs REST
      .csrf(csrf -> csrf.disable())
      // Permite frames para o H2 Console
      .headers(headers -> headers.frameOptions(frame -> frame.disable()))
      // Configuração de autorização
      .authorizeHttpRequests(auth -> auth
        // Documentação Swagger e OpenAPI (acesso público em dev)
        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
        // H2 Console (acesso público em dev)
        .requestMatchers("/h2-console/**").permitAll()
        // Endpoints de autenticação e registro
        .requestMatchers("/api/v1/auth/**").permitAll()
        .requestMatchers("/api/v1/users/create").permitAll()
        // Endpoints protegidos por role
        .requestMatchers("/api/v1/users/**").hasAnyRole("USER", "ADMIN")
        .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
        // Qualquer outra requisição precisa estar autenticada
        .anyRequest().authenticated()
      )
      // Adiciona o filtro JWT antes do filtro padrão de autenticação
      .addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  // Configuração básica de CORS para permitir todas as origens (ajuste para produção)
  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(List.of("*"));
    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    configuration.setAllowedHeaders(List.of("*"));
    configuration.setAllowCredentials(true);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

}
