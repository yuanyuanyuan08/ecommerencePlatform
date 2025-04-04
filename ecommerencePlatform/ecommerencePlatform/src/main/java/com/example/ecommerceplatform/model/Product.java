package com.example.ecommerceplatform.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Product {

    private Integer pid;
    private Integer vid;
    private String name;
    private Double price;
    private Integer stock;
    private String merchantId;
    private String tags;
    private Date createTime;
    public Product(String name, double price, int stock, String merchantId,String tags) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.merchantId = merchantId;
        this.tags = tags;
    }

    public Product() {
    }

    public static Product addNewProduct(String name, double price, int stock, String merchantId, String tag1, String tag2, String tag3){
        List<String> tagList = new ArrayList<>();
        if (tag1 != null && !tag1.isEmpty()) tagList.add(tag1);
        if (tag2 != null && !tag2.isEmpty()) tagList.add(tag2);
        if (tag3 != null && !tag3.isEmpty()) tagList.add(tag3);
        String tags = String.join(",", tagList);
        Product product = new Product(name, price, stock, merchantId, tags);

        //插入表？
        return product;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public void setVid(Integer vid) {
        this.vid = vid;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public void setName(String value) {
        this.name = value;
    }

    public void setPrice(Double value) {
        this.price = value;
    }

    public void setStock(Integer value) {
        this.stock = value;
    }

    public void setTags(String value) {
        // "tag1,tag2"(","作为分割)
        this.tags = value;
    }

    public String getPid() {
        return String.valueOf(pid);
    }

    public Integer getVid() {
        return vid;
    }

    public String getMerchantId() {
        return merchantId;
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
            List<Product> products = User.getProduct();
            return products;
        }
        List<Product> products = User.getProductByTags(search);
        return products;
    }

    public String getMerchantName() {
        String name = User.getMerchantNameByMerchantId(String.valueOf(this.getVid()));
        return name;
    }
}