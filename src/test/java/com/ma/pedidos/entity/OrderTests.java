package com.ma.pedidos.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class OrderTests {

    @ParameterizedTest
    @MethodSource("provideArgumentsForCalculateTotalAmountTest")
    public void calculateTotalAmountTest(BigDecimal input, BigDecimal expected, String msg) {

        Order order = buildOrderForTest();

        order.calculateTotalAmount(input);

        assertEquals(expected.setScale(2), order.getTotalAmount().setScale(2), msg);
    }

    @Test
    public void getQuantityProductsTest() {

        Order order = buildOrderForTest();
        Integer expected = 2;

        Integer result = order.getQuantityProducts();

        assertEquals(expected, result);
    }

    private static Stream<Arguments> provideArgumentsForCalculateTotalAmountTest() {

        return Stream.of(
            Arguments.of(BigDecimal.ZERO, BigDecimal.valueOf(800.00), "Without discount"),
            Arguments.of(BigDecimal.valueOf(20.00), BigDecimal.valueOf(640.00), "With discount"),
            Arguments.of(BigDecimal.valueOf(100.00), BigDecimal.valueOf(0.0), "Total dicount"));
    }

    private static Order buildOrderForTest() {

        Product product1 = new Product();
        product1.setUnitPrice(BigDecimal.valueOf(500.00));
        OrderItem item1 = new OrderItem();
        item1.setProduct(product1);
        item1.setQuantity(1);
        item1.calculateAmount();

        Product product2 = new Product();
        product2.setUnitPrice(BigDecimal.valueOf(300.00));
        OrderItem item2 = new OrderItem();
        item2.setProduct(product2);
        item2.setQuantity(1);
        item2.calculateAmount();

        List<OrderItem> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);

        Order order = new Order();
        order.setOrderItems(items);

        return order;
    }
}
