package com.example.productapi.error.Exception;

public class NotFoundProductException extends RuntimeException {
    
    public NotFoundProductException(Long id){
        super(id.toString());
    }
}
