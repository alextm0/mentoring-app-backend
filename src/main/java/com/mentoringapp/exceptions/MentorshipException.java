package com.mentoringapp.exceptions;

public class MentorshipException extends RuntimeException {
  public MentorshipException() {
    super();
  }

  public MentorshipException(String message) {
    super(message);
  }

  public MentorshipException(String message, Throwable cause) {
    super(message, cause);
  }

  public MentorshipException(Throwable cause) {
    super(cause);
  }

  protected MentorshipException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
