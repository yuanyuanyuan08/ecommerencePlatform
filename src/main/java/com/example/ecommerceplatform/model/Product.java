package com.example.ecommerceplatform.model;

import java.util.ArrayList;
import java.util.List;

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
    public static Product addNewProduct(String name, double price, int stock, String merchantId, String tag1,String tag2,String tag3){
        String tag = tag1 + "," + tag2 + "," + tag3; //没有判空
        Product product = new Product(name, price, stock, merchantId, tag);
        //插入表？
        return product;
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

    public String getTag(Integer i) {
        String[] tag = tags.split(",");
        if (i <= tag.length && i > 0) {
            return tag[i - 1];
        }
        return "";
    }
    // Getters and Setters


    public String getTags() {
        return tags;
    }

    public static  List<Product> getProductListBySearch(String search){
        if (search.isEmpty()) {
            //返回所有product
        }

        List<Product> productList = new ArrayList<>();
        productList.add(new Product("fish1", 10.2, 10, "01", "food,seafood"));
        productList.add(new Product("fish2", 10.2, 10, "01", "food,seafood"));
        productList.add(new Product("fish3", 10.2, 10, "01", "food,seafood"));

        return productList;

    }
}