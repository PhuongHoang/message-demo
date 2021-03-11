package com.phuonghoang.messagedemo.web.exceptions;

import com.phuonghoang.messagedemo.web.dto.RestErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestExceptionHandler {

  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<RestErrorDto> handleInvalidInput(
      IllegalArgumentException ex, HttpServletRequest httpServletRequest) {
    final RestErrorDto restErrorDto =
        new RestErrorDto(
            "error/incorrect-arguments",
            "Invalid arguments provided",
            HttpStatus.BAD_REQUEST,
            ex.getMessage(),
            httpServletRequest.getRequestURI());
    return new ResponseEntity<>(restErrorDto, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<RestErrorDto> handleValidationException(
      MethodArgumentNotValidException ex, HttpServletRequest httpServletRequest) {
    final Map<String, String> errors = new HashMap<>();
    ex.getBindingResult()
        .getAllErrors()
        .forEach(
            (error) -> {
              String fieldName = ((FieldError) error).getField();
              String errorMessage = error.getDefaultMessage();
              errors.put(fieldName, errorMessage);
            });
    final String errorMessage =
        errors.entrySet().stream()
            .map(entry -> entry.getKey() + ": " + entry.getValue())
            .collect(Collectors.joining(","));
    final RestErrorDto restErrorDto =
        new RestErrorDto(
            "error/incorrect-arguments",
            "Invalid arguments provided",
            HttpStatus.BAD_REQUEST,
            errorMessage,
            httpServletRequest.getRequestURI());
    return new ResponseEntity<>(restErrorDto, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(NoSuchElementException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<RestErrorDto> handleNoElementFound(
      NoSuchElementException ex, HttpServletRequest httpServletRequest) {
    final RestErrorDto restErrorDto =
        new RestErrorDto(
            "error/not-found",
            "No element found",
            HttpStatus.NOT_FOUND,
            ex.getMessage(),
            httpServletRequest.getRequestURI());
    return new ResponseEntity<>(restErrorDto, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(RuntimeException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<RestErrorDto> handleGenericError(
      RuntimeException ex, HttpServletRequest httpServletRequest) {
    final RestErrorDto restErrorDto =
        new RestErrorDto(
            "error/internal-error",
            "Internal error",
            HttpStatus.INTERNAL_SERVER_ERROR,
            ex.getMessage(),
            httpServletRequest.getRequestURI());
    return new ResponseEntity<>(restErrorDto, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
