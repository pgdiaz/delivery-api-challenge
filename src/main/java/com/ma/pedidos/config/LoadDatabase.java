package com.ma.pedidos.config;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.ma.pedidos.entity.Order;
import com.ma.pedidos.entity.OrderItem;
import com.ma.pedidos.entity.Product;
import com.ma.pedidos.repository.OrderRepository;
import com.ma.pedidos.repository.ProductRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {
    
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(
        ProductRepository productRepository,
        OrderRepository orderRepository) {

        return args -> {
            Product firstProduct = new Product();
            firstProduct.setName("Mozzarella");
            firstProduct.setShortDescription("Pizza Mozzarella");
            firstProduct.setLongDescription("Pizza de salsa de tomate, mozzarella, orégano y aceitunas");
            firstProduct.setUnitPrice(BigDecimal.valueOf(300.00));
            productRepository.save(firstProduct);
            log.info("Preloading product with id " + firstProduct.getId());

            Product secondProduct = new Product();
            secondProduct.setName("Fugazzeta");
            secondProduct.setShortDescription("Pizza Fugazzetta");
            secondProduct.setLongDescription("Pizza de mozzarella, cebolla, oliva, oregáno, aceitunas negras");
            secondProduct.setUnitPrice(BigDecimal.valueOf(550.00));
            productRepository.save(secondProduct);
            log.info("Preloading product with id " + secondProduct.getId());

            Order firstOrder = new Order();
            firstOrder.setAddress("Dorton Road 80");
            firstOrder.setEmail("tsayb@opera.com");
            firstOrder.setPhone("(0351) 48158101");
            firstOrder.setTime(LocalTime.of(14, 30));
            firstOrder.setDateCreated(LocalDate.of(2021, 2, 15));
            firstOrder.setStatus("PENDING");
            OrderItem item = new OrderItem();
            item.setQuantity(1);
            item.setProduct(secondProduct);
            item.calculateAmount();
            List<OrderItem> items = new ArrayList<>();
            items.add(item);
            firstOrder.setOrderItems(items);
            firstOrder.calculateTotalAmount(BigDecimal.ZERO);
            orderRepository.save(firstOrder);
            log.info("Preloading order with id " + firstOrder.getId());
        };
    }
}
