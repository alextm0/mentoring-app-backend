package com.mentoringapp.controller.advice;

import com.mentoringapp.dto.response.ApiErrorResponseDTO;
import com.mentoringapp.exceptions.*;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@Slf4j
@Hidden // Hide from Swagger documentation
public class GlobalExceptionHandler {

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<ApiErrorResponseDTO> handleUserNotFoundException(UserNotFoundException ex) {
    log.error("Caught UserNotFoundException", ex);
    ApiErrorResponseDTO error = new ApiErrorResponseDTO(ex.getMessage(), "User Not Found", HttpStatus.NOT_FOUND.value());
    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(MentorshipNotFoundException.class)
  public ResponseEntity<ApiErrorResponseDTO> handleMentorshipNotFoundException(MentorshipNotFoundException ex) {
    log.error("Caught MentorshipNotFoundException", ex);
    ApiErrorResponseDTO error = new ApiErrorResponseDTO(ex.getMessage(), "Mentorship Not Found", HttpStatus.NOT_FOUND.value());
    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(InvalidMentorshipException.class)
  public ResponseEntity<ApiErrorResponseDTO> handleInvalidMentorshipException(InvalidMentorshipException ex) {
    log.error("Caught InvalidMentorshipException", ex);
    ApiErrorResponseDTO error = new ApiErrorResponseDTO(ex.getMessage(), "Invalid Mentorship", HttpStatus.BAD_REQUEST.value());
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MentorshipAlreadyExistsException.class)
  public ResponseEntity<ApiErrorResponseDTO> handleMentorshipAlreadyExistsException(MentorshipAlreadyExistsException ex) {
    log.error("Caught MentorshipAlreadyExistsException", ex);
    ApiErrorResponseDTO error = new ApiErrorResponseDTO(ex.getMessage(), "Mentorship Already Exists", HttpStatus.CONFLICT.value());
    return new ResponseEntity<>(error, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(MentorshipPermissionException.class)
  public ResponseEntity<ApiErrorResponseDTO> handleMentorshipPermissionException(MentorshipPermissionException ex) {
    log.error("Caught MentorshipPermissionException", ex);
    ApiErrorResponseDTO error = new ApiErrorResponseDTO(ex.getMessage(), "Permission Denied", HttpStatus.FORBIDDEN.value());
    return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(UserException.class)
  public ResponseEntity<ApiErrorResponseDTO> handleUserException(UserException ex) {
    log.error("Caught UserException", ex);
    ApiErrorResponseDTO error = new ApiErrorResponseDTO(ex.getMessage(), "User Error", HttpStatus.BAD_REQUEST.value());
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiErrorResponseDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
    log.error("Validation error", ex);
    
    List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
    String errorMessage = fieldErrors.stream()
            .findFirst()
            .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
            .orElse("Validation error occurred");

    ApiErrorResponseDTO error = new ApiErrorResponseDTO(errorMessage, "Validation Error", HttpStatus.BAD_REQUEST.value());
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ApiErrorResponseDTO> handleConstraintViolationException(ConstraintViolationException ex) {
    log.error("Caught constraint violation exception", ex);

    String errorMessage = ex.getConstraintViolations()
            .stream()
            .findFirst()
            .map(v -> v.getPropertyPath() + ": " + v.getMessage())
            .orElse("A constraint violation occurred");

    ApiErrorResponseDTO error = new ApiErrorResponseDTO(errorMessage, "Constraint Violation", HttpStatus.BAD_REQUEST.value());
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiErrorResponseDTO> handleException(Exception ex) {
    log.error("Caught unexpected exception", ex);
    ApiErrorResponseDTO error = new ApiErrorResponseDTO("An unexpected error occurred", "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR.value());
    return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
