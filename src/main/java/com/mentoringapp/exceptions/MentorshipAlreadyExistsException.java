package com.mentoringapp.exceptions;

public class MentorshipAlreadyExistsException extends MentorshipException {
  public MentorshipAlreadyExistsException(String message) {
    super(message);
  }

  public MentorshipAlreadyExistsException() {
    super();
  }

  public MentorshipAlreadyExistsException(String message, Throwable cause) {
    super(message, cause);
  }

  public MentorshipAlreadyExistsException(Throwable cause) {
    super(cause);
  }

  protected MentorshipAlreadyExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
} 