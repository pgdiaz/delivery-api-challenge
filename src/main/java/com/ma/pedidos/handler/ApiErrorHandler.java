package com.ma.pedidos.handler;

import com.ma.pedidos.exception.ApiRuntimeException;
import com.ma.pedidos.model.ApiErrorModel;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiErrorHandler {
    
    @ExceptionHandler(ApiRuntimeException.class)
    public ResponseEntity<ApiErrorModel> handleApiException(ApiRuntimeException exception) {

        ApiErrorModel model = new ApiErrorModel(exception.getMessage());

        return ResponseEntity.status(exception.getStatusCode()).body(model);
    }
}
