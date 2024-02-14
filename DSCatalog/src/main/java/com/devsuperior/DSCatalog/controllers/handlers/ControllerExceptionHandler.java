package com.devsuperior.DSCatalog.controllers.handlers;

import com.devsuperior.DSCatalog.dto.errors.CustomError;
import com.devsuperior.DSCatalog.dto.errors.FieldMessage;
import com.devsuperior.DSCatalog.dto.errors.ValidationError;
import com.devsuperior.DSCatalog.services.exceptions.DatabaseException;
import com.devsuperior.DSCatalog.services.exceptions.EmailException;
import com.devsuperior.DSCatalog.services.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        CustomError err = new CustomError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<CustomError> database(DatabaseException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        CustomError err = new CustomError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomError> methodArgumentNotValid(MethodArgumentNotValidException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        ValidationError err = new ValidationError(Instant.now(), status.value(), "Invalid data", request.getRequestURI());
        err.addAll(e.getBindingResult().getFieldErrors().stream().map(fieldError -> new FieldMessage(fieldError.getField(), fieldError.getDefaultMessage())).toList());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(EmailException.class)
    public ResponseEntity<CustomError> database(EmailException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        CustomError err = new CustomError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }
}
