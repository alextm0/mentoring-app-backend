package com.mentoringapp.exceptions;

public class MentorshipPermissionException extends UserException {
  public MentorshipPermissionException(String message) {
    super(message);
  }

  public MentorshipPermissionException() {
    super();
  }

  public MentorshipPermissionException(String message, Throwable cause) {
    super(message, cause);
  }

  public MentorshipPermissionException(Throwable cause) {
    super(cause);
  }

  protected MentorshipPermissionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
} 