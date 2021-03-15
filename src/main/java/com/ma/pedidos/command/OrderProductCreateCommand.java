package com.ma.pedidos.command;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class OrderProductCreateCommand {
    
    @NotBlank(message = "The product is required")
    private String product;
    @NotNull(message = "The quantity is required")
    @Min(value = 1, message = "The quantity must be greater than 0")
    private Integer quantity;

    public String getProduct() {
        return this.product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
