package com.ma.pedidos.repository;

import java.util.UUID;

import com.ma.pedidos.entity.Product;

import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, UUID> {
    
}
