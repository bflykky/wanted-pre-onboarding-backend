package com.wanted.preonboarding.error;

import com.wanted.preonboarding.error.exception.EntityNotFoundException;
import com.wanted.preonboarding.error.exception.PasswordNotMatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = EntityNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException e) {
        ErrorResponse errorResponse = ErrorResponse.of(e.getErrorCode());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(errorResponse.getStatus()));
    }

    @ExceptionHandler(value = PasswordNotMatchException.class)
    protected ResponseEntity<ErrorResponse> handlePasswordFalseException(PasswordNotMatchException e) {
        ErrorResponse errorResponse = ErrorResponse.of(e.getErrorCode());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(errorResponse.getStatus()));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        FieldError fieldError = e.getFieldError();
        ErrorResponse errorResponse = ErrorResponse.of(status.value(), "E010" , fieldError.getDefaultMessage());
        return new ResponseEntity<>(errorResponse, status);
    }
}
