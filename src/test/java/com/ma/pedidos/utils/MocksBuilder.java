package com.ma.pedidos.utils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.ma.pedidos.model.OrderItemModel;
import com.ma.pedidos.model.OrderModel;
import com.ma.pedidos.model.OrderStatusModel;
import com.ma.pedidos.model.SearchModel;

public class MocksBuilder {
    
    public static SearchModel<OrderModel> buildSearchOrdersResult() {

        SearchModel<OrderModel> result = new SearchModel<>();
        List<OrderModel> orders = new ArrayList<>();
        orders.add(buildOrderWhitDiscount());
        orders.add(buildOrderWithoutDiscount());
        result.setResults(orders);

        return result;
    }

    public static OrderModel buildOrderWhitDiscount() {

        OrderModel order = new OrderModel();
        order.setDate(LocalDate.of(2020, 05, 26));
        order.setAddress("Dorton Road 80");
        order.setEmail("tsayb@opera.com");
        order.setPhone("(0351) 48158101");
        order.setTime(LocalTime.of(21, 00));
        List<OrderItemModel> detail = new ArrayList<>();
        detail.add(buildOrderItem(
            "89efb206-2aa6-4e21-8a23-5765e3de1f31", "Jamón y morrones", 2, 1100.00));
        detail.add(buildOrderItem(
            "e29ebd0c-39d2-4054-b0f4-ed2d0ea089a1", "Palmitos", 2, 1200.00));
        order.setDetail(detail);
        order.setTotal(BigDecimal.valueOf(1610.00));
        order.setDiscount(true);
        order.setStatus(OrderStatusModel.PENDING);

        return order;
    }

    private static OrderModel buildOrderWithoutDiscount() {

        OrderModel order = new OrderModel();
        order.setDate(LocalDate.of(2020, 05, 26));
        order.setAddress("Artisan Hill 47");
        order.setEmail("ghathawayg@home.pl");
        order.setPhone("(0358) 48997013");
        order.setTime(LocalTime.of(22, 30));
        List<OrderItemModel> detail = new ArrayList<>();
        detail.add(buildOrderItem(
            "89efb206-2aa6-4e21-8a23-5765e3de1f31", "Jamón y morrones", 1, 550.00));
        order.setDetail(detail);
        order.setTotal(BigDecimal.valueOf(550.00));
        order.setDiscount(false);
        order.setStatus(OrderStatusModel.PENDING);

        return order;
    }

    private static OrderItemModel buildOrderItem(
        String product,
        String name,
        Integer quantity,
        Double amount) {

        OrderItemModel item = new OrderItemModel();
        item.setProduct(product);
        item.setName(name);
        item.setQuantity(quantity);
        item.setAmount(BigDecimal.valueOf(amount));

        return item;
    }
}
