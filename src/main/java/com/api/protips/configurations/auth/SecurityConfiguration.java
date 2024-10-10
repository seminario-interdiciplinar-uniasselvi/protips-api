package com.api.protips.configurations.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

  private final JwtAuthenticationFilter jwtAuthFilter;
  private final AuthenticationProvider authenticationProvider;
  private final LogoutHandler logoutHandler;
  private final CustomAccessDeniedHandler customAccessDeniedHandler;

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
}
