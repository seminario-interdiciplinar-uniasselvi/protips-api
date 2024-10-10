package com.api.protips.exceptions;

import org.springframework.http.HttpStatus;

public class ResourceNotValidException extends AbstractException {

  public ResourceNotValidException(String message, String value) {
    super(String.format(message, value), HttpStatus.BAD_REQUEST);
  }
  public ResourceNotValidException(String message) {
    super(message, HttpStatus.BAD_REQUEST);
  }
}
