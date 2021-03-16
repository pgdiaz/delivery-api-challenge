package com.ma.pedidos.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "order_info")
public class Order {
    
    @Id
    @GeneratedValue
    private Long id;
    private String address;
    private String email;
    private String phone;
    private LocalTime time;
    private LocalDate dateCreated;
    private BigDecimal totalAmount;
    private Boolean discount;
    private String status;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "orderId", nullable = false)
    private List<OrderItem> orderItems;

    public Long getId() {
        return this.id;
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

    public LocalDate getDateCreated() {
        return this.dateCreated;
    }

    public void setDateCreated(LocalDate date) {
        this.dateCreated = date;
    }

    public Boolean getDiscount() {
        return this.discount;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OrderItem> getOrderItems() {
        return this.orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void calculateTotalAmount(BigDecimal discountRate) {

        BigDecimal amount = this.orderItems.stream()
            .map(item -> item.getAmount())
            .reduce(BigDecimal.ZERO, (a, b) -> a.add(b));

        this.totalAmount = amount;
        this.discount = discountRate.compareTo(BigDecimal.ZERO) > 0;

        if (this.discount) {
            this.totalAmount = amount.add(
                amount.multiply(discountRate.divide(BigDecimal.valueOf(100.00))).negate());
        }
    }

    public Integer getQuantityProducts() {
        return this.orderItems.stream()
            .mapToInt(item -> item.getQuantity())
            .sum();
    }
}
