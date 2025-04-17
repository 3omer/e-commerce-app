package com.omosman.ecommerce.handler;

import com.omosman.ecommerce.exception.CustomerNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle CustomerNotFoundException
     */
    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<String> handle(CustomerNotFoundException exp) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(exp.getMessage());
    }

    /**
    * Handle validation errors
    * */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException exp) {
        var errors = new HashMap<String, String>();

        exp.getBindingResult().getAllErrors().forEach((err) -> errors.put(((FieldError) err).getField(), err.getDefaultMessage()));

        return ResponseEntity.badRequest().body(new ErrorResponse(errors));
    }
}