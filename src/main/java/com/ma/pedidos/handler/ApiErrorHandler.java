package com.ma.pedidos.handler;

import java.util.ArrayList;
import java.util.List;

import com.ma.pedidos.exception.ApiRuntimeException;
import com.ma.pedidos.model.ApiErrorModel;
import com.ma.pedidos.model.ApiErrorsModel;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiErrorHandler {

    @ExceptionHandler(ApiRuntimeException.class)
    public ResponseEntity<ApiErrorModel> handleApiException(ApiRuntimeException exception) {

        ApiErrorModel model = new ApiErrorModel(exception.getMessage());

        return ResponseEntity.status(exception.getStatusCode()).body(model);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorsModel<ApiErrorModel>> handleValidationException(
            MethodArgumentNotValidException exception) {

        List<ApiErrorModel> errors = new ArrayList<ApiErrorModel>();

        exception.getBindingResult().getAllErrors().forEach(err -> {
            errors.add(new ApiErrorModel(err.getDefaultMessage()));
        });

        ApiErrorsModel<ApiErrorModel> model = new ApiErrorsModel<ApiErrorModel>();
        model.setErrors(errors);

        return ResponseEntity.badRequest().body(model);
    }
}
