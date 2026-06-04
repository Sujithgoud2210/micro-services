package com.springworks.product_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.security.sasl.AuthenticationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(com.trainingmug.ecommerce.productservice.exception.ProductExistsException.class)
    public ResponseEntity<String> handleProductExistsException(com.trainingmug.ecommerce.productservice.exception.ProductExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
    @ExceptionHandler(com.trainingmug.ecommerce.productservice.exception.ProductNotFoundException.class)
    public ResponseEntity<String> handleProductNotFoundException(com.trainingmug.ecommerce.productservice.exception.ProductNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
}
