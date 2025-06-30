package com.mentoringapp.exceptions;

public class AssignmentPermissionException extends AssignmentException {
  public AssignmentPermissionException() {
    super();
  }

  public AssignmentPermissionException(String message) {
    super(message);
  }

  public AssignmentPermissionException(String message, Throwable cause) {
    super(message, cause);
  }

  public AssignmentPermissionException(Throwable cause) {
    super(cause);
  }

  protected AssignmentPermissionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
