package com.example.ecommerceplatform.model;

import java.util.List;

// Order.java
public class Order {
    private String orderId;
    private String consumerId;
    private String merchantId;


    private String productName;
    private String productId;

    private Integer quantity;
    private Double price; //单价还是总价？
    private String orderGroupId;
    private String address;
    private String createdDate;
    private String endDate;
    public boolean isShipped; //是否发货：true-已发货，false-未发货
    private Double feedback;

    public Order(String consumerId, String merchantId, String productId,String name,int quantity,double price,String address,boolean isShipped,double feedback,String orderGroupId) {
        this.consumerId = consumerId;
        this.merchantId = merchantId;
        this.productId = productId;
        this.address = address;
        this.quantity = quantity;
        this.price = price;
        this.productName = name;
        this.feedback = feedback;
        this.isShipped = isShipped;
        this.createdDate = "2024-01-01";
        this.orderGroupId = orderGroupId;
    }


    // Getters and Setters


    public String getOrderGroupId() {
        return orderGroupId;
    }

    public Double getPrice() {
        return price;
    }

    public String getFeedback() {
        //假设feedback为-1时代表顾客没有评分，假设满分为5分
        return this.feedback==-1?"Empty":String.valueOf(this.feedback)+"/5";
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getConsumerId() {
        return consumerId;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }
    public Boolean getIsShipped(){
        return isShipped;
    }

    public String getAddress() {
        return address;
    }
    public String getEndDate(){
        return endDate;
    }

    public void setIsShipped(boolean b) {
        this.isShipped = b;
    }

    public void setFeedback(Double feedback) {
        this.feedback = feedback;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}