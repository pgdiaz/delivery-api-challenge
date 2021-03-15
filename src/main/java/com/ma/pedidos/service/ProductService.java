package com.ma.pedidos.service;

import java.util.UUID;

import com.ma.pedidos.command.ProductCreateCommand;
import com.ma.pedidos.command.ProductUpdateCommand;
import com.ma.pedidos.entity.Product;
import com.ma.pedidos.exception.ResourceNotFoundException;
import com.ma.pedidos.model.ProductModel;
import com.ma.pedidos.repository.ProductRepository;

import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final static String PRODUCT_NOT_FOUND = "Product not found";

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public ProductModel find(UUID id) {
        Product entity = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(PRODUCT_NOT_FOUND));

        return toModel(entity);
    }

    public ProductModel create(ProductCreateCommand command) {

        Product entity = new Product();
        entity.setName(command.getName());
        entity.setUnitPrice(command.getUnitPrice());

        if (!isBlankString(command.getShortDescription())) {
            entity.setShortDescription(command.getShortDescription());
        }

        if (!isBlankString(command.getLongDescription())) {
            entity.setLongDescription(command.getLongDescription());
        }

        repository.save(entity);

        return toModel(entity);
    }

    public void update(UUID id, ProductUpdateCommand command) {

        repository.findById(id)
            .map(entity -> {
                if (!isBlankString(command.getName())) {
                    entity.setName(command.getName());
                }

                if (!isBlankString(command.getShortDescription())) {
                    entity.setShortDescription(command.getShortDescription());
                }
        
                if (!isBlankString(command.getLongDescription())) {
                    entity.setLongDescription(command.getLongDescription());
                }

                if (command.getUnitPrice() != null) {
                    entity.setUnitPrice(command.getUnitPrice());
                }

                return repository.save(entity);
            })
            .orElseThrow(() -> new ResourceNotFoundException(PRODUCT_NOT_FOUND));
    }

    public void remove(UUID id) {
    
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException(PRODUCT_NOT_FOUND);
        }

        repository.deleteById(id);
    }

    private static ProductModel toModel(Product entity) {

        ProductModel model = new ProductModel();
        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setShortDescription(entity.getShortDescription());
        model.setLongDescription(entity.getLongDescription());
        model.setUnitPrice(entity.getUnitPrice());

        return model;
    }

    private static boolean isBlankString(String value) {
        return value == null || value.isEmpty();
    }
}
