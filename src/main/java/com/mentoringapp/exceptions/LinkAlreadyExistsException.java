package com.mentoringapp.exceptions;

public class LinkAlreadyExistsException extends UserException {
  public LinkAlreadyExistsException(String message) {
    super(message);
  }

  public LinkAlreadyExistsException() {
    super();
  }

  public LinkAlreadyExistsException(String message, Throwable cause) {
    super(message, cause);
  }

  public LinkAlreadyExistsException(Throwable cause) {
    super(cause);
  }

  protected LinkAlreadyExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
