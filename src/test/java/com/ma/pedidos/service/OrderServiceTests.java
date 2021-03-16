package com.ma.pedidos.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.ma.pedidos.command.OrderCreateCommand;
import com.ma.pedidos.command.OrderProductCreateCommand;
import com.ma.pedidos.entity.Order;
import com.ma.pedidos.entity.Product;
import com.ma.pedidos.model.OrderModel;
import com.ma.pedidos.repository.OrderRepository;
import com.ma.pedidos.repository.ProductRepository;

import org.junit.jupiter.api.Test;

public class OrderServiceTests {

    @Test
    public void createOrderWithoutDiscount() {

        ProductRepository productRepository = mock(ProductRepository.class);
        when(productRepository.findById(any(UUID.class)))
            .thenReturn(Optional.of(buildProduct()));

        OrderRepository orderRepository = mock(OrderRepository.class);
        when(orderRepository.save(any(Order.class))).thenAnswer(arg -> {
            return arg.getArgument(0);
        });

        OrderCreateCommand command = buildCommand(3);

        OrderService service = new OrderService(orderRepository, productRepository);

        OrderModel result = service.create(command);

        assertFalse(result.getDiscount());
        assertEquals("150.00", result.getTotal().setScale(2).toString());
    }

    @Test()
    public void createOrderWithDiscount() {

        ProductRepository productRepository = mock(ProductRepository.class);
        when(productRepository.findById(any(UUID.class)))
            .thenReturn(Optional.of(buildProduct()));

        OrderRepository orderRepository = mock(OrderRepository.class);
        when(orderRepository.save(any(Order.class))).thenAnswer(arg -> {
            return arg.getArgument(0);
        });

        OrderCreateCommand command = buildCommand(6);

        OrderService service = new OrderService(orderRepository, productRepository);

        OrderModel result = service.create(command);

        assertTrue(result.getDiscount());
        assertEquals("210.00", result.getTotal().setScale(2).toString());
    }

    private static OrderCreateCommand buildCommand(Integer quantityProducts) {
        List<OrderProductCreateCommand> detail = new ArrayList<>();
        OrderProductCreateCommand item = new OrderProductCreateCommand();
        item.setProduct("89efb206-2aa6-4e21-8a23-5765e3de1f31");
        item.setQuantity(quantityProducts);
        detail.add(item);
        OrderCreateCommand command = new OrderCreateCommand();
        command.setAddress("testAddress");
        command.setTime(LocalTime.now());
        command.setDetail(detail);

        return command;
    }

    private static Product buildProduct() {

        Product entity = new Product();
        entity.setUnitPrice(BigDecimal.valueOf(50.00));
        entity.setId(UUID.fromString("89efb206-2aa6-4e21-8a23-5765e3de1f31"));

        return entity;
    }
}
