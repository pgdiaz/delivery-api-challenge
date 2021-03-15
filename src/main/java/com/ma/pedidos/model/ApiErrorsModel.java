package com.ma.pedidos.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiErrorsModel<T> {
    
    @JsonProperty(required = true)
    private List<T> errors;

    public List<T> getErrors() {
        return this.errors;
    }

    public void setErrors(List<T> errors) {
        this.errors = errors;
    }
}
