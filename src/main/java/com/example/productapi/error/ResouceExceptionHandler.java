package com.example.productapi.error;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.productapi.error.Exception.BadRequestException;
import com.example.productapi.error.Exception.InternalServerErrorException;
import com.example.productapi.error.Exception.NotFoundException;
import com.example.productapi.error.Exception.NotFoundProductException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ResouceExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = NotFoundProductException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ResponseException> NotFoundProductExceptionById(NotFoundProductException ex, WebRequest request){
        log.error("NOT_FOUND", ex);
        String id = ex.getMessage();
        //String.format("Produto com ID: %d não foi encontrado", id);
        ResponseException response = new ResponseException("Product com id " + id + " não foi encontrado");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(value = InternalServerErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ResponseException> resouceInternalServerError(InternalServerErrorException ex, WebRequest request){
        log.error("InternalServerError", ex);
        ResponseException response = new ResponseException(ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
    
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ResponseException> resouceNotFount(NotFoundException ex, WebRequest request){
        log.error("Not Found", ex);
        ResponseException response = new ResponseException(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseException> resouceNotFount(BadRequestException ex, WebRequest request){
        log.error("bad request", ex);
        ResponseException response = new ResponseException(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}