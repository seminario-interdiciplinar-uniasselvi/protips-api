package com.api.protips.configurations.cors;

import com.api.protips.configurations.ApplicationProperties;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsConfig extends OncePerRequestFilter {


    private final ApplicationProperties applicationProperties;
    @Override
    public void doFilterInternal(
      @NonNull HttpServletRequest httpReq,
      HttpServletResponse response,
      FilterChain filterChain
    ) throws ServletException, IOException {
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, applicationProperties.getCors().getAllowedOrigins());
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, String.valueOf(applicationProperties.getCors().isAllowCredentials()));
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, applicationProperties.getCors().getAllowedMethods());
        response.setHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, applicationProperties.getCors().getMaxAge());
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, applicationProperties.getCors().getAllowedHeaders());
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, applicationProperties.getCors().getExposedHeaders());
        response.setHeader(HttpHeaders.VARY, "Origin");
        filterChain.doFilter(httpReq, response);
    }
}