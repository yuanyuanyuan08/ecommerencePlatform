package com.example.ecommerceplatform.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import com.example.ecommerceplatform.model.Consumer;
import com.example.ecommerceplatform.model.Order;
import com.example.ecommerceplatform.model.Product;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// ConsumerController.java
public class ConsumerController {
    public TextField searchField;
    public TableColumn orderId;
    public TableView historyOrderTable;
    public TableColumn productName;
    public TableColumn vendorName;
    public TableColumn quantity;
    public TableColumn totalPrice;
    public TableColumn status;
    public TableColumn action;

    public Label consumerAddress;
    public Label consumerPhone;
    public Label consumerName;

    @FXML
    private Label consumerInfoLabel;
    @FXML private ListView<Product> productListView;
    @FXML private ListView<Map.Entry<Product, Integer>> cartListView;
    @FXML private ListView<Order> orderHistoryListView;

    private Consumer consumer;

    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
        updateConsumerInfo();
        updateProductList("");
        updateCartList();
        updateOrderHistory();
    }
    //display info


    private void updateConsumerInfo() {
        if (!consumer.getName().isEmpty()) {
            consumerName.setText(consumer.getName());
        }
        if (!consumer.getAddress().isEmpty()) {
            consumerAddress.setText(consumer.getAddress());
        }
        if (!consumer.getPhone().isEmpty()) {
            consumerPhone.setText(consumer.getPhone());
        }
    }

    private void updateProductList(String search) {
        // 获取所有商家的商品并显示
        List<Product> productList = Product.getProductListBySearch(search);//根据搜索获得商品列表
        productListView.getItems().addAll(productList);
        productListView.setCellFactory(lv -> new ProductListCell());
    }

    private void updateCartList() {
        Map<Product, Integer> cart = consumer.getCart();

        ObservableList<Map.Entry<Product, Integer>> cartEntries =
                FXCollections.observableArrayList(cart.entrySet());
        cartListView.setItems(cartEntries);
        cartListView.setCellFactory(lv -> new cartListCell());
    }

    private void updateOrderHistory() {
        orderHistoryListView.getItems().setAll(consumer.getOrders());
    }

    @FXML
    private void handlePurchase(ActionEvent event) {
        Map<Product, Integer> cart = consumer.getCart();
        // 处理购买逻辑
        consumer.createOrder(cart);
    }

    public void handleSearchProduct(ActionEvent actionEvent) {
        String search = searchField.getText();
        updateProductList(search);
    }



    public void handleModifyConsumerInfo(ActionEvent actionEvent) {
        Dialog dialog = new Dialog();
        dialog.setTitle("Modify Info");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.getDialogPane().setContent(createInfoForm(consumerName.getText(),consumerPhone.getText(),consumerAddress.getText()));
        dialog.show();
    }
    private Node createInfoForm(String name, String phone, String address){
        GridPane gridPane = new GridPane();
        TextField newName = new TextField(name);
        TextField newPhone = new TextField(phone);
        TextField newAddress = new TextField(address);
        gridPane.add(new Label("Your name: "), 0, 0);
        gridPane.add(new Label("Phone: "), 0, 1);
        gridPane.add(new Label("Address: "), 0, 2);
        gridPane.add(newName, 1, 0);
        gridPane.add(newPhone, 1, 1);
        gridPane.add(newAddress, 1, 2);
        Button button = new Button("Submit");
        gridPane.add(button, 0, 3);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                consumer.updateConsumerInfo(newName.getText(), newPhone.getText(), newAddress.getText());
                ((Stage) (((Button) actionEvent.getSource()).getScene().getWindow())).close();
                updateConsumerInfo();
            }
        });


        return gridPane;
    }

    public class ProductListCell extends ListCell<Product> {
        private final HBox root = new HBox(10); // 主容器
        private final VBox infoBox = new VBox(5); // 文字信息
        private final Text nameText = new Text();
        private final Text priceText = new Text();
        private final Text stockText = new Text();
        private final Text tagsText = new Text();

        private final TextField quantityField = new TextField();
        private final Button addToCartBtn = new Button("Add to Cart");

        public ProductListCell() {
            // 设置布局样式
            root.setPadding(new Insets(10));
            root.setAlignment(Pos.CENTER_LEFT);

            // 文字信息样式
            nameText.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");
            priceText.setStyle("-fx-text-fill: #0076a3;");
            stockText.setStyle("-fx-text-fill: #666;");
            tagsText.setStyle("-fx-font-style: italic;");

            // 输入框设置
            quantityField.setPromptText("Quantity");
            quantityField.setPrefWidth(60);

            // 按钮事件
            addToCartBtn.setOnAction(e -> {
                Product product = getItem();
                try {
                    int quantity = Integer.parseInt(quantityField.getText().isEmpty()?"1":quantityField.getText());
                    consumer.addItemsToCart(product,quantity);

                    quantityField.clear();
                    updateCartList();
                    System.out.printf("Added %d x %s to cart%n", quantity, product.getName());
                } catch (NumberFormatException ex) {
                    System.out.println("Invalid quantity!");
                }
            });

            // 组装布局
            infoBox.getChildren().addAll(nameText, priceText, stockText, tagsText);
            root.getChildren().addAll(infoBox, quantityField, addToCartBtn);
        }

        @Override
        protected void updateItem(Product product, boolean empty) {
            super.updateItem(product, empty);
            if (empty || product == null) {
                setGraphic(null);
            } else {
                // 更新数据
                nameText.setText("Name: " + product.getName());
                priceText.setText(String.format("Price: $%.2f", product.getPrice()));
                stockText.setText("Stock: " + product.getStock());
                tagsText.setText("Tags: " + product.getTags());
                quantityField.clear();
                setGraphic(root); // 显示自定义布局
            }
        }
    }
    public class cartListCell extends ListCell<Map.Entry<Product,Integer>> {
        private final HBox root = new HBox(10); // 主容器
        private final VBox infoBox = new VBox(5); // 文字信息
        private final Text nameText = new Text();
        private final Text priceText = new Text();
        private final Text quantityText = new Text();


        private final TextField quantityField = new TextField();
        private final Button modifyBtn = new Button("modify Quantity");
        private final Button deleteBtn = new Button("Delete");

        public cartListCell() {
            // 设置布局样式
            root.setPadding(new Insets(10));
            root.setAlignment(Pos.CENTER_LEFT);

            // 文字信息样式
            nameText.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");
            priceText.setStyle("-fx-text-fill: #0076a3;");
            quantityText.setStyle("-fx-text-fill: #666;");


            // 输入框设置
            quantityField.setPromptText("Quantity");
            quantityField.setPrefWidth(60);

            // 按钮事件
            modifyBtn.setOnAction(e -> {
                Product product = getItem().getKey();
                try {
                    int quantity = Integer.parseInt(quantityField.getText());
                    System.out.printf("Added %d x %s to cart%n", quantity, product.getName());
                    consumer.changeCartItemsQuantity(product, quantity);
                    quantityText.setText("quantity: " +quantity);

                } catch (NumberFormatException ex) {
                    System.out.println("Invalid quantity!");
                }
            });
            deleteBtn.setOnAction(e -> {
                Product cartItem = getItem().getKey();
                try {
                    int quantity = Integer.parseInt(quantityText.getText().split(":")[1].trim());
                    System.out.printf("Delete %d x %s to cart%n", quantity, cartItem.getName());
                    consumer.deleteCartItem(cartItem);
                    updateCartList();


                } catch (NumberFormatException ex) {
                    System.out.println("Invalid quantity!");
                }
            });

            // 组装布局
            infoBox.getChildren().addAll(nameText, priceText, quantityText,quantityField);
            root.getChildren().addAll(infoBox, quantityField, modifyBtn,deleteBtn);
        }

        @Override
        protected void updateItem(Map.Entry<Product,Integer> item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setGraphic(null);
            } else {
                Product product = item.getKey();
                Integer quantity = item.getValue();
                // 更新数据
                nameText.setText("Name: " + product.getName());
                priceText.setText(String.format("Price: $%.2f", product.getPrice()));
                quantityText.setText("quantity: " +quantity);
                quantityField.clear();
                setGraphic(root); // 显示自定义布局
            }
        }
    }
}
