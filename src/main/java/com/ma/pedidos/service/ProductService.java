package com.ma.pedidos.service;

import com.ma.pedidos.command.ProductCreateCommand;
import com.ma.pedidos.command.ProductUpdateCommand;
import com.ma.pedidos.model.ProductModel;

import org.springframework.stereotype.Service;

@Service
public class ProductService {
    
    public ProductModel find(String id) {
        throw new UnsupportedOperationException();
    }

    public ProductModel create(ProductCreateCommand command) {
        throw new UnsupportedOperationException();
    }

    public void update(String id, ProductUpdateCommand command) {
        throw new UnsupportedOperationException();
    }

    public void remove(String id) {
        throw new UnsupportedOperationException();
    }
}
