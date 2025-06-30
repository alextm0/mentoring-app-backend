package com.mentoringapp.exceptions;

public class MentorshipNotFoundException extends MentorshipException {
  public MentorshipNotFoundException(String message) {
    super(message);
  }

  public MentorshipNotFoundException() {
    super();
  }

  public MentorshipNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public MentorshipNotFoundException(Throwable cause) {
    super(cause);
  }

  protected MentorshipNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
} 