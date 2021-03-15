package com.ma.pedidos.controller;

import java.time.LocalDate;

import javax.validation.Valid;

import com.ma.pedidos.command.OrderCreateCommand;
import com.ma.pedidos.model.OrderModel;
import com.ma.pedidos.model.SearchModel;
import com.ma.pedidos.service.OrderService;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<SearchModel<OrderModel>> search(
        @RequestParam(value = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        SearchModel<OrderModel> models = this.service.findByDate(date);

        return ResponseEntity.ok(models);
    }

    @PostMapping()
    public ResponseEntity<OrderModel> create(@Valid @RequestBody OrderCreateCommand command) {

        OrderModel model = this.service.create(command);

        return ResponseEntity.status(HttpStatus.CREATED).body(model);
    }
}
