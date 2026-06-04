package com.springworks.product_service.exception;

public class ProductExistsException extends RuntimeException{
    public ProductExistsException(String message) {
        super(message);
    }
}
