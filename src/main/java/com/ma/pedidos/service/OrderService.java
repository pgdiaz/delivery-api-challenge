package com.ma.pedidos.service;

import java.time.LocalDate;

import com.ma.pedidos.command.OrderCreateCommand;
import com.ma.pedidos.model.OrderModel;
import com.ma.pedidos.model.SearchModel;

import org.springframework.stereotype.Service;

@Service
public class OrderService {
    
    public SearchModel<OrderModel> findByDate(LocalDate date) {
        throw new UnsupportedOperationException();
    }

    public OrderModel create(OrderCreateCommand command) {
        throw new UnsupportedOperationException();
    }
}
