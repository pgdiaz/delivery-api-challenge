package com.ma.pedidos.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SearchModel<T> {
    
    @JsonProperty(required = true)
    private List<T> results;

    public List<T> getResults() {
        return this.results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }
}
