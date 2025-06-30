package com.mentoringapp.exceptions;

public class AssignmentNotFoundException extends AssignmentException {
  public AssignmentNotFoundException() {
    super();
  }

  public AssignmentNotFoundException(String message) {
    super(message);
  }

  public AssignmentNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public AssignmentNotFoundException(Throwable cause) {
    super(cause);
  }

  protected AssignmentNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
