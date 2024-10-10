package com.api.protips.exceptions;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends AbstractException {

  public ResourceNotFoundException(String message, Object... args) {
    super(String.format(message,args), HttpStatus.NOT_FOUND);
  }
}
