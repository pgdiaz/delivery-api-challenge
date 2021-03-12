package com.ma.pedidos.controller;

import java.net.URI;

import com.ma.pedidos.command.ProductCreateCommand;
import com.ma.pedidos.command.ProductUpdateCommand;
import com.ma.pedidos.model.ProductModel;
import com.ma.pedidos.service.ProductService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }
    
    @GetMapping("/products/{id}")
    public ResponseEntity<ProductModel> show(@PathVariable String id) {

        ProductModel model = this.service.find(id);

        return ResponseEntity.ok(model);
    }

    @PostMapping("/products")
    public ResponseEntity<ProductModel> create(@RequestBody ProductCreateCommand command) {

        ProductModel model = this.service.create(command);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(model.getId())
            .toUri();

        return ResponseEntity.created(location).body(model);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Void> update(
        @PathVariable String id,
        @RequestBody ProductUpdateCommand command) {
        
        this.service.update(id, command);
        
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> remove(@PathVariable String id) {

        this.service.remove(id);

        return ResponseEntity.noContent().build();
    }
}
