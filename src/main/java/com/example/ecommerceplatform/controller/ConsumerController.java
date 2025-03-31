package com.example.ecommerceplatform.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import com.example.ecommerceplatform.model.Consumer;
import com.example.ecommerceplatform.model.Order;
import com.example.ecommerceplatform.model.Product;

// ConsumerController.java
public class ConsumerController {
    @FXML
    private Label consumerInfoLabel;
    @FXML private ListView<Product> productListView;
    @FXML private ListView<Product> cartListView;
    @FXML private ListView<Order> orderHistoryListView;

    private Consumer consumer;

    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
        updateConsumerInfo();
        updateProductList();
        updateCartList();
        updateOrderHistory();
        updateConsumerInfo();
    }
    //display info


    private void updateConsumerInfo() {
        consumerInfoLabel.setText("消费者ID: " + consumer.getId() + "\n地理位置: " + consumer.getLocation());
    }

    private void updateProductList() {
        // 获取所有商家的商品并显示
    }

    private void updateCartList() {
        cartListView.getItems().setAll(consumer.getCart());
    }

    private void updateOrderHistory() {
        orderHistoryListView.getItems().setAll(consumer.getOrders());
    }

    @FXML
    private void handlePurchase(ActionEvent event) {
        // 处理购买逻辑
    }

    public void handleAddProduct(ActionEvent actionEvent) {
    }

    public void handleSearchProduct(ActionEvent actionEvent) {
    }
}
