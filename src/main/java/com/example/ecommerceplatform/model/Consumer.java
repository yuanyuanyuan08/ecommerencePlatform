package com.example.ecommerceplatform.model;

import java.util.ArrayList;
import java.util.List;

public class Consumer extends User {
    private String location;
    private List<Product> cart;

    private List<Order> orders;

    public Consumer(String id, String location) {
        super(id, "Consumer");
        this.location = location;
        this.cart = new ArrayList<>();
        this.orders = new ArrayList<>();
    }

    public Product getCart() {
        return null;
    }

    public Order getOrders() {
        return null;
    }
    public String getLocation() {
        return this.location;

    }


    // Getters and Setters
}
