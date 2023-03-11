package com.example.productapi.error.Exception;

public class NotFoundException extends RuntimeException  {

    public NotFoundException(String massage){
        super(massage);
    }
}
