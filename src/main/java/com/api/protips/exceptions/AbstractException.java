package com.api.protips.exceptions;

import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class AbstractException extends RuntimeException {

  private final LocalDateTime occurredIn = LocalDateTime.now();
  private final HttpStatus status;
  private final String[] errors;

  protected AbstractException(String message, HttpStatus status) {
    super(message);
    this.status = status;
    this.errors = null;
  }
  protected AbstractException(String message, HttpStatus status, String[] errors) {
    super(message);
    this.status = status;
    this.errors = errors;
  }
}
