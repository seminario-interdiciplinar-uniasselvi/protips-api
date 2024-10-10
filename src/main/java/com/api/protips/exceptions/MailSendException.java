package com.api.protips.exceptions;

import org.springframework.http.HttpStatus;

public class MailSendException extends AbstractException {

  public MailSendException(String message, Object... args) {
    super(String.format(message, args), HttpStatus.BAD_REQUEST);
  }
}
