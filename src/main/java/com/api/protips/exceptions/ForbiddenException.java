package com.api.protips.exceptions;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends AbstractException {

  public ForbiddenException(String message, String value) {
    super(String.format(message, value), HttpStatus.FORBIDDEN);
  }
  public ForbiddenException(String message) {
    super(message, HttpStatus.FORBIDDEN);
  }
}
