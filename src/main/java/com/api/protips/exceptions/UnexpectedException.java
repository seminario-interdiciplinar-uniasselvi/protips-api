package com.api.protips.exceptions;

import org.springframework.http.HttpStatus;

public class UnexpectedException extends AbstractException {

  private static final long serialVersionUID = 1L;

  public UnexpectedException(String message) {
    super(message,HttpStatus.INTERNAL_SERVER_ERROR);
  }


}
