package com.omosman.ecommerce.handler;

import com.omosman.ecommerce.exception.InsufficientProductQuantityException;
import com.omosman.ecommerce.exception.ProductNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException exp) {
        var errors = exp.getBindingResult()
                .getAllErrors()
                .stream()
                .map(objectError -> (FieldError) objectError)
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

        return ResponseEntity.badRequest().body(new ErrorResponse(errors));
    }

    // not found
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFoundException(ProductNotFoundException exp) {
        var error = Map.of("message", exp.getMessage());
        return ResponseEntity.badRequest().body(new ErrorResponse(error));
    }

    // out of stock
    @ExceptionHandler(InsufficientProductQuantityException.class)
    public ResponseEntity<ErrorResponse> handleProductSoldOutException(InsufficientProductQuantityException exp) {
        var error = Map.of("message", exp.getMessage());
        return ResponseEntity.badRequest().body(new ErrorResponse(error));
    }


}
