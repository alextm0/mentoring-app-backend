package com.mentoringapp.exceptions;

public class AssignmentException extends RuntimeException {
  public AssignmentException() {
    super();
  }

  public AssignmentException(String message) {
    super(message);
  }

  public AssignmentException(String message, Throwable cause) {
    super(message, cause);
  }

  public AssignmentException(Throwable cause) {
    super(cause);
  }

  protected AssignmentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
