package com.ma.pedidos;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.ma.pedidos.controller.ProductController;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MainAppTests {

    @Autowired
    private ProductController controller;

    @Test
    public void contextLoads() {
        assertNotNull(controller);
    }
}
