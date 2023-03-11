package com.example.productapi.error.Exception;

public class BadRequestException extends RuntimeException {
    
    public BadRequestException( String massage){
        super(massage);
    }
}
