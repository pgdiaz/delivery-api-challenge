package com.ma.pedidos.command;

import java.time.LocalTime;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

public class OrderCreateCommand {

    @NotBlank(message = "The address is required")
    private String address;
    @Email(message = "The Email should be valid")
    private String email;
    private String phone;
    @NotNull(message = "The time is required")
    @DateTimeFormat(pattern = "HH:ss")
    private LocalTime time;
    @NotNull(message = "The detail is required")
    @Size(min = 1, message = "The detail must include at least one product")
    @Valid
    private List<OrderProductCreateCommand> detail;

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

    public List<OrderProductCreateCommand> getDetail() {
        return this.detail;
    }

    public void setDetail(List<OrderProductCreateCommand> detail) {
        this.detail = detail;
    }
}
