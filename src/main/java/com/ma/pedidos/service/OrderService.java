package com.ma.pedidos.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.ma.pedidos.command.OrderCreateCommand;
import com.ma.pedidos.command.OrderProductCreateCommand;
import com.ma.pedidos.entity.Order;
import com.ma.pedidos.entity.OrderItem;
import com.ma.pedidos.entity.Product;
import com.ma.pedidos.exception.ResourceNotFoundException;
import com.ma.pedidos.model.OrderItemModel;
import com.ma.pedidos.model.OrderModel;
import com.ma.pedidos.model.OrderStatusModel;
import com.ma.pedidos.model.SearchModel;
import com.ma.pedidos.repository.OrderRepository;
import com.ma.pedidos.repository.ProductRepository;

import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final static String PRODUCT_NOT_FOUND = "Product not found";

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public SearchModel<OrderModel> findByDate(LocalDate date) {

        List<OrderModel> result = new ArrayList<>();
        this.orderRepository.findByDateCreated(date).iterator()
            .forEachRemaining(entity -> {
                result.add(toModel(entity));
            });

        SearchModel<OrderModel> model = new SearchModel<>();
        model.setResults(result);

        return model;
    }

    public OrderModel create(OrderCreateCommand command) {

        Order entity = new Order();
        entity.setAddress(command.getAddress());
        entity.setTime(command.getTime());
        entity.setDateCreated(LocalDate.now());
        entity.setStatus(OrderStatusModel.PENDING.toString());

        List<OrderItem> item = new ArrayList<>();
        command.getDetail().forEach(detail -> {
            item.add(create(detail));
        });
        entity.setOrderItems(item);

        BigDecimal discountRate = entity.getQuantityProducts().compareTo(3) > 0 ?
            BigDecimal.valueOf(30.00) : BigDecimal.ZERO;
            
        entity.calculateTotalAmount(discountRate);

        if (!isBlankString(command.getEmail())) {
            entity.setEmail(command.getEmail());
        }

        if (!isBlankString(command.getPhone())) {
            entity.setPhone(command.getPhone());
        }

        this.orderRepository.save(entity);

        return toModel(entity);
    }

    private OrderItem create(OrderProductCreateCommand command) {

        Product product = this.productRepository
            .findById(UUID.fromString(command.getProduct()))
            .orElseThrow(() -> new ResourceNotFoundException(PRODUCT_NOT_FOUND));

        OrderItem entity = new OrderItem();
        entity.setQuantity(command.getQuantity());
        entity.setProduct(product);
        entity.calculateAmount();

        return entity;
    }

    private static OrderModel toModel(Order entity) {

        OrderModel model = new OrderModel();
        model.setDate(entity.getDateCreated());
        model.setAddress(entity.getAddress());
        model.setEmail(entity.getEmail());
        model.setPhone(entity.getPhone());
        model.setTime(entity.getTime());
        model.setTotal(entity.getTotalAmount());
        model.setDiscount(entity.getDiscount());
        model.setStatus(OrderStatusModel.valueOf(entity.getStatus()));

        List<OrderItemModel> items = new ArrayList<>();
        entity.getOrderItems().forEach(item -> {
            items.add(toModel(item));
        });
        model.setDetail(items);

        return model;
    }

    private static OrderItemModel toModel(OrderItem entity) {
        OrderItemModel model = new OrderItemModel();
        model.setProduct(entity.getProduct().getId().toString());
        model.setName(entity.getProduct().getName());
        model.setQuantity(entity.getQuantity());
        model.setAmount(entity.getAmount());

        return model;
    }

    private static boolean isBlankString(String value) {
        return value == null || value.isEmpty();
    }
}
