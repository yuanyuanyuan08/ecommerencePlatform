package com.example.ecommerceplatform.model;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Consumer extends User {
    private String name;
    private String phone;
    private String address;
    private Map<Product,Integer>cart;

    private List<Order> orders;

    public Consumer(String id, String address) {
        super(id, "Consumer");
        this.address = address;
        this.cart = new HashMap<>();
        this.orders = new ArrayList<>();
    }


    public Map<Product,Integer>getCart() {
        return this.cart;
    }

    public List<Order> getOrders() {
        return this.orders;
    }
    public String getAddress() {
        return this.address;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String getId() {
        return super.getId();
    }

    public String getName() {
        return name;
    }

    public static Consumer getCustomerByPhone(String phone){
        Consumer consumer = new Consumer("01","");
        consumer.name = "Wang xiao yuan";
        consumer.address = "Hong Kong";
        consumer.phone = "110";
        Map<Product,Integer>cart = consumer.getCart();
        List<Order> orderList = consumer.getOrders();
        orderList.add(new Order("01", "01", "01","fish1", 1,10.2, "Hong Kong",true,5.0,"01"));
        orderList.add(new Order("01", "01", "01","fish2", 2, 10.2,"Hong Kong",true,5.0,"01"));
        orderList.add(new Order("01", "01", "01","fish3", 3, 10.2,"Hong Kong",true,5.0,"01"));
        orderList.add(new Order("01", "01", "01","fish1", 1, 10.2,"Hong Kong",true,5.0,"02"));
        orderList.add(new Order("01", "01", "01","fish2", 2,10.2, "Hong Kong",true,5.0,"02"));
        orderList.add(new Order("01", "01", "01","fish3", 3, 10.2,"Hong Kong",true,5.0,"02"));


        return consumer;

    }

    public void updateConsumerInfo(String name, String phone, String address) {
        //联表修改顾客个人信息
        this.name = name;
        this.phone = phone;
        this.address = address;

    }

    public void deleteCartItem(Product product) {
        this.cart.remove(product);
    }

    public void addItemsToCart(Product product, int quantity) {
        this.cart.put(product, cart.getOrDefault(product,0)+quantity);
    }

    public Integer getCartItemQuantity(Product item) {
        return this.cart.get(item);
    }

    public void changeCartItemsQuantity(Product product, int quantity) {
        this.cart.put(product, quantity);
    }

    public List<Order> createOrder(Map<Product, Integer> cart) {
        return null;
    }


    // Getters and Setters
}
