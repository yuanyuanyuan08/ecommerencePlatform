package com.example.ecommerceplatform.model;

public class Product {

    private String name;
    private Double price;
    private Integer stock;
    private String merchantId;
    private String tags;
    public Product(String name, double price, int stock, String merchantId,String tags) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.merchantId = merchantId;
        this.tags = tags;
    }

    public void setName(String value) {
    }

    public void setPrice(Double value) {
        //
    }

    public void setStock(Integer value) {
        //
    }

    public void setTags(String value) {
        // "tag1,tag2"(","作为分割)
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getStock() {
        return stock;
    }

    public String getTags() {
        return tags;
    }
    // Getters and Setters
}