package com.ma.pedidos.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class OrderModel {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private String address;
    private String email;
    private String phone;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime time;
    private List<OrderItemModel> detail;
    private BigDecimal total;
    private Boolean discount;
    private OrderStatusModel status;

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalTime getTime() {
        return this.time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public List<OrderItemModel> getDetail() {
        return this.detail;
    }

    public void setDetail(List<OrderItemModel> detail) {
        this.detail = detail;
    }

    public BigDecimal getTotal() {
        return this.total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Boolean getDiscount() {
        return this.discount;
    }

    public void setDiscount(Boolean discount) {
        this.discount = discount;
    }

    public OrderStatusModel getStatus() {
        return this.status;
    }

    public void setStatus(OrderStatusModel status) {
        this.status = status;
    }
}
