package com.mentoringapp.controller.advice;

import com.mentoringapp.dto.ErrorDTO;
import com.mentoringapp.exceptions.UserNotFoundException;
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
public class GlobalExceptionHandler {

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<ErrorDTO> handleUserNotFoundException(UserNotFoundException ex) {
    log.error("Caught exception", ex);
    ErrorDTO errorDTO = new ErrorDTO("User not found");
    return new ResponseEntity<>(errorDTO, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorDTO> handleException(Exception ex) {
    log.error("Caught exception", ex);
    ErrorDTO errorDTO = new ErrorDTO("An unknown error occurred");
    return new ResponseEntity<>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ErrorDTO> handleConstraintViolationException(ConstraintViolationException ex) {
    log.error("Caught constraint violation exception", ex);

    String errorMessage = ex.getConstraintViolations()
            .stream()
            .findFirst()
            .map(v -> v.getPropertyPath() + ": " + v.getMessage())
            .orElse("A constraint violation occurred");

    return new ResponseEntity<>(new ErrorDTO(errorMessage), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
    log.error("Validation error", ex);

    List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
    String errorMessage = fieldErrors.stream()
            .findFirst()
            .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
            .orElse("Validation error occurred");

    return new ResponseEntity<>(new ErrorDTO(errorMessage), HttpStatus.BAD_REQUEST);
  }
}
