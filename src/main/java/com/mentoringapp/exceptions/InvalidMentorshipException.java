package com.mentoringapp.exceptions;

public class InvalidMentorshipException extends MentorshipException {
  public InvalidMentorshipException(String message) {
    super(message);
  }

  public InvalidMentorshipException() {
    super();
  }

  public InvalidMentorshipException(String message, Throwable cause) {
    super(message, cause);
  }

  public InvalidMentorshipException(Throwable cause) {
    super(cause);
  }

  protected InvalidMentorshipException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
} 