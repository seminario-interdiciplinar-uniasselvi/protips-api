package com.api.protips.configurations.auth;

import com.api.protips.exceptions.handler.ExceptionResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
@Log4j2
@RequiredArgsConstructor
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

  private final ObjectMapper objectMapper = new ObjectMapper();
  @Override
  public void handle(
    HttpServletRequest request,
    HttpServletResponse response,
    AccessDeniedException accessDeniedException
  )  {
    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    response.setContentType("application/json");
    try {
      ExceptionResponse exceptionResponse = new ExceptionResponse(
        HttpStatus.FORBIDDEN,
        "Protected route",
        getClass().getName(),
        accessDeniedException.getMessage()
      );
      objectMapper.registerModule(new JavaTimeModule());
      objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
      objectMapper.writeValue(response.getOutputStream(), exceptionResponse);
    } catch (Exception e) {
      log.error(e.getMessage());
    }
  }
}
