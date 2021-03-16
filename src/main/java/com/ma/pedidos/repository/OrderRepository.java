package com.ma.pedidos.repository;

import java.time.LocalDate;

import com.ma.pedidos.entity.Order;

import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
    
    Iterable<Order> findByDateCreated(LocalDate dateCreated);
}
