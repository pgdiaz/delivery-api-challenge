package com.ma.pedidos.repository;

import java.util.UUID;

import com.ma.pedidos.entity.Product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    
}
