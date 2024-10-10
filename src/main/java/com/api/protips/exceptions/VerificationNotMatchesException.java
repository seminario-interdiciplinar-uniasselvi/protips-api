package com.api.protips.exceptions;

import org.springframework.http.HttpStatus;

public class VerificationNotMatchesException extends AbstractException {

  public VerificationNotMatchesException(String message, String value) {
    super(String.format(message, value), HttpStatus.BAD_REQUEST);
  }
  public VerificationNotMatchesException(String message) {
    super(message, HttpStatus.BAD_REQUEST);
  }
}
