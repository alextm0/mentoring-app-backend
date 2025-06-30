package com.mentoringapp.controller.advice;

import com.mentoringapp.dto.response.ApiErrorDTO;
import com.mentoringapp.exceptions.*;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
@Hidden // Hide from Swagger documentation
public class GlobalExceptionHandler {

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<ApiErrorDTO> handleUserNotFoundException(UserNotFoundException ex) {
    log.error("User not found: {}", ex.getMessage());
    ApiErrorDTO error = new ApiErrorDTO(ex.getMessage(), "User Not Found", HttpStatus.NOT_FOUND.value());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @ExceptionHandler(MentorshipNotFoundException.class)
  public ResponseEntity<ApiErrorDTO> handleMentorshipNotFoundException(MentorshipNotFoundException ex) {
    log.error("Mentorship not found: {}", ex.getMessage());
    ApiErrorDTO error = new ApiErrorDTO(ex.getMessage(), "Mentorship Not Found", HttpStatus.NOT_FOUND.value());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @ExceptionHandler(InvalidMentorshipException.class)
  public ResponseEntity<ApiErrorDTO> handleInvalidMentorshipException(InvalidMentorshipException ex) {
    log.error("Invalid mentorship: {}", ex.getMessage());
    ApiErrorDTO error = new ApiErrorDTO(ex.getMessage(), "Invalid Mentorship", HttpStatus.BAD_REQUEST.value());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(MentorshipAlreadyExistsException.class)
  public ResponseEntity<ApiErrorDTO> handleMentorshipAlreadyExistsException(MentorshipAlreadyExistsException ex) {
    log.error("Mentorship already exists: {}", ex.getMessage());
    ApiErrorDTO error = new ApiErrorDTO(ex.getMessage(), "Mentorship Already Exists", HttpStatus.CONFLICT.value());
    return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
  }

  @ExceptionHandler(MentorshipPermissionException.class)
  public ResponseEntity<ApiErrorDTO> handleMentorshipPermissionException(MentorshipPermissionException ex) {
    log.error("Mentorship permission denied: {}", ex.getMessage());
    ApiErrorDTO error = new ApiErrorDTO(ex.getMessage(), "Permission Denied", HttpStatus.FORBIDDEN.value());
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
  }

  @ExceptionHandler(UserException.class)
  public ResponseEntity<ApiErrorDTO> handleUserException(UserException ex) {
    log.error("User error: {}", ex.getMessage());
    ApiErrorDTO error = new ApiErrorDTO(ex.getMessage(), "User Error", HttpStatus.BAD_REQUEST.value());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiErrorDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
    String errorMessage = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
            .collect(Collectors.joining(", "));

    log.error("Validation error: {}", errorMessage);
    ApiErrorDTO error = new ApiErrorDTO(errorMessage, "Validation Error", HttpStatus.BAD_REQUEST.value());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ApiErrorDTO> handleConstraintViolationException(ConstraintViolationException ex) {
    String errorMessage = ex.getConstraintViolations()
            .stream()
            .map(ConstraintViolation::getMessage)
            .collect(Collectors.joining(", "));

    log.error("Constraint violation: {}", errorMessage);
    ApiErrorDTO error = new ApiErrorDTO(errorMessage, "Constraint Violation", HttpStatus.BAD_REQUEST.value());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiErrorDTO> handleException(Exception ex) {
    log.error("Unexpected error occurred", ex);
    ApiErrorDTO error = new ApiErrorDTO("An unexpected error occurred", "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR.value());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
  }
}
