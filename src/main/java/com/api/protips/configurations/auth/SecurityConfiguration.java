package com.api.protips.configurations.auth;

import com.api.protips.configurations.ApplicationProperties;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

  private final JwtAuthenticationFilter jwtAuthFilter;
  private final AuthenticationProvider authenticationProvider;
  private final LogoutHandler logoutHandler;
  private final CustomAccessDeniedHandler customAccessDeniedHandler;
  private final ApplicationProperties applicationProperties;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
      .csrf(AbstractHttpConfigurer::disable)
      .sessionManagement(sessionManagement -> sessionManagement
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      )
      .authorizeHttpRequests(
        authorization ->
          authorization
            .requestMatchers(devToolsMatcher()).permitAll()
            .anyRequest().permitAll()
      )
      .exceptionHandling(httpSecurityExceptionHandlingConfigurer ->
          httpSecurityExceptionHandlingConfigurer.accessDeniedHandler(customAccessDeniedHandler)
        )
      .authenticationProvider(authenticationProvider)
      .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
      .logout(
        httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer
          .logoutUrl("/v1/auth/logout")
          .addLogoutHandler(logoutHandler)
          .logoutSuccessHandler(
            (request, response, authentication) -> SecurityContextHolder.clearContext()
          )
      );
    return http.build();
  }


  private String[] devToolsMatcher() {
    return new String[]{
      "/swagger/**",
      "/swagger-ui/**",
      "/swagger-ui.html",
      "/swagger-resources/**",
      "/configuration/ui",
      "/configuration/security",
      "/webjars/**",
      "/v2/api-docs/**",
      "/v3/api-docs/**",
      "/h2-console/**",
      "/actuator/**"
    };
  }

//  @Bean
//  public Customizer<CorsConfigurer<HttpSecurity>> customizeCors() {
//    return cors -> cors
//      .configurationSource(request -> {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(List.of("http://localhost:5173","http://localhost:4173"));
//        configuration.setAllowedMethods(
//          List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
//        configuration.setAllowedHeaders(List.of("*"));
//        configuration.addExposedHeader("X-Token-Expired");
//        configuration.addExposedHeader("Device-Id");
//        configuration.setAllowCredentials(true);
//
//        return configuration;
//      });
//  }
}
