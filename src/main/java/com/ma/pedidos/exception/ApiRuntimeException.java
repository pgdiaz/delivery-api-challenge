package com.ma.pedidos.exception;

import org.springframework.http.HttpStatus;

@SuppressWarnings("serial")
public class ApiRuntimeException extends RuntimeException {
    
    private final HttpStatus statusCode;

    public ApiRuntimeException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public HttpStatus getStatusCode() {
        return this.statusCode;
    }
}
