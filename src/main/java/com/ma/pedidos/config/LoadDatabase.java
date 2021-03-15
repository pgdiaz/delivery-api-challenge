package com.ma.pedidos.config;

import java.math.BigDecimal;

import com.ma.pedidos.entity.Product;
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
    CommandLineRunner initDatabase(ProductRepository productRepository) {
        return args -> {
            Product firstProduct = new Product();
            firstProduct.setName("Mozzarella");
            firstProduct.setShortDescription("Pizza Mozzarella");
            firstProduct.setLongDescription("Pizza de salsa de tomate, mozzarella, orégano y aceitunas");
            firstProduct.setUnitPrice(BigDecimal.valueOf(300.00));
            productRepository.save(firstProduct);
            log.info("Preloading product id " + firstProduct.getId());

            Product secondProduct = new Product();
            secondProduct.setName("Fugazzeta");
            secondProduct.setShortDescription("Pizza Fugazzetta");
            secondProduct.setLongDescription("Pizza de mozzarella, cebolla, oliva, oregáno, aceitunas negras");
            secondProduct.setUnitPrice(BigDecimal.valueOf(550.00));
            productRepository.save(secondProduct);
            log.info("Preloading product id " + secondProduct.getId());
        };
    }
}
