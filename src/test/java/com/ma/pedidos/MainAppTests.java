package com.ma.pedidos;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.ma.pedidos.controller.OrderController;
import com.ma.pedidos.controller.ProductController;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MainAppTests {

    @Autowired
    private ProductController productController;

    @Autowired
    private OrderController orderController;

    @Test
    public void contextLoads() {
        assertNotNull(productController);
        assertNotNull(orderController);
    }
}
