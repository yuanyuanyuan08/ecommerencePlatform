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
    private String orderGroupId;
    private String address;
    private String createdDate;
    private String endDate;
    private boolean isShipped; //是否发货：true-已发货，false-未发货
    private Double feedback;

    public Order(String consumerId, String merchantId, String productId,String name,int quantity,boolean isShipped) {
        this.consumerId = consumerId;
        this.merchantId = merchantId;
        this.productId = productId;
        this.productName = name;
        this.isShipped = false;
        this.createdDate = "2024-01-01";
    }

    // Getters and Setters

    public Double getFeedback() {
        return feedback;
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
}