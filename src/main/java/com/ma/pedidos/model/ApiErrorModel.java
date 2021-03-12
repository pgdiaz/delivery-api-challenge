package com.ma.pedidos.model;

public class ApiErrorModel {
    
    private String error;

    public ApiErrorModel(String error) {
        this.error = error;
    }

    public String getError() {
        return this.error;
    }
}
