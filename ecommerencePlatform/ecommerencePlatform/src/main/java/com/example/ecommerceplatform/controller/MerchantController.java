package com.example.ecommerceplatform.controller;

import com.example.ecommerceplatform.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import com.example.ecommerceplatform.model.Merchant;
import com.example.ecommerceplatform.model.Order;
import com.example.ecommerceplatform.model.Product;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;



// MerchantController.java
public class MerchantController {
    @FXML public Label shopname;
    @FXML public Label phone;
    @FXML public Label address;
    public TableView<Product> productTable;

    public TableView<Order> orderTable;
    public TableColumn orderedProductColumn;
    public TableColumn orderedQuantityColumn;
    public TableColumn orderedAddressColumn;
    public TableColumn orderedCreatedTimeColumn;
    public TableColumn orderedEndedTimeColumn;
    public TableColumn orderedStatusColumn;
    public TableColumn orderedFeedbackColumn;
    public Label merchantScoreLabel;


    private Merchant merchant;


    @FXML
    TableColumn nameColumn;
    @FXML
    TableColumn priceColumn;
    @FXML
    TableColumn stockColumn;
    @FXML
    TableColumn tagsColumn;
    @FXML
    TableColumn actionColumn;



    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
        // 加载数据
        updateProductTable();
        // 初始化 merchant info
        updateMerchantInfo();
        updateOrderData();
    }

    public void updateProductTable(){
        ObservableList<Product> data = FXCollections.observableArrayList();
        data.addAll(merchant.getProducts());
        productTable.setItems(data);
    }

    public void initialize() {


        // 初始化表格列
        nameColumn = (TableColumn) productTable.getColumns().get(0);
        nameColumn.setCellValueFactory(new PropertyValueFactory<Product,String>("name"));



        priceColumn = (TableColumn) productTable.getColumns().get(1);
        priceColumn.setCellValueFactory(new PropertyValueFactory<Product, Number>("price"));


        stockColumn = (TableColumn) productTable.getColumns().get(2);
        stockColumn.setCellValueFactory(new PropertyValueFactory<Product, Number>("stock"));


        tagsColumn = (TableColumn) productTable.getColumns().get(3);
        tagsColumn.setCellValueFactory(new PropertyValueFactory<Product,String>("tags"));

        // 设置Action列的按钮
        actionColumn = (TableColumn) productTable.getColumns().get(4);
        actionColumn.setCellFactory(createButtonCellFactory());


        //
        initOrderTable();


    }
    private void initOrderTable(){
        // 初始化表格列
        orderedProductColumn = (TableColumn) orderTable.getColumns().get(0);
        orderedProductColumn.setCellValueFactory(new PropertyValueFactory<Order,String>("productName"));



        orderedQuantityColumn = (TableColumn) orderTable.getColumns().get(1);
        orderedQuantityColumn.setCellValueFactory(new PropertyValueFactory<Order, Number>("quantity"));


        orderedAddressColumn = (TableColumn) orderTable.getColumns().get(2);
        orderedAddressColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("address"));


        orderedCreatedTimeColumn = (TableColumn) orderTable.getColumns().get(3);
        orderedCreatedTimeColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("createdDate"));


        // endDate
        orderedEndedTimeColumn = (TableColumn) orderTable.getColumns().get(4);
        orderedEndedTimeColumn.setCellValueFactory(new PropertyValueFactory<Order,String>("endDate"));
        // feedback
        orderedFeedbackColumn = (TableColumn) orderTable.getColumns().get(5);
        orderedFeedbackColumn.setCellValueFactory(new PropertyValueFactory<Order,String>("feedback"));

        //
        orderedStatusColumn = (TableColumn) orderTable.getColumns().get(6);
        orderedStatusColumn.setCellFactory(createShippedButton());


    }


    private Callback<TableColumn<Order, Void>, TableCell<Order, Void>> createShippedButton() {
        return param -> new TableCell<Order, Void>() {
            private final HBox container = new HBox();
            private final HBox containerEmpty = new HBox();
            private final Button shippedBtn = new Button("Shipped");
            private final Text text = new Text("stock is not enough");

            {
                container.setSpacing(5);
                container.getChildren().add(shippedBtn); // 添加按钮到容器

                containerEmpty.setSpacing(5);
                containerEmpty.getChildren().add(text);
                shippedBtn.setOnAction(event -> {
                    Order order = getTableView().getItems().get(getIndex());
                    //设置为已发货
                    merchant.shipOrder(order);
                    orderTable.refresh();

                    updateProductTable();
                    // 处理修改逻辑
                    System.out.println("Modified order: " + order.getOrderId());
                });


            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    Order order = getTableView().getItems().get(getIndex());
                    Product product = User.getProductById(order.getProductId());

                    if (order.getIsShipped()) {
                        setGraphic(null);  // 如果已发货，不显示按钮
                    } else if(product.getStock()!=null&&product.getStock()<order.getQuantity()){//库存不足
                        setGraphic(containerEmpty);  // 如果未发货，显示按钮
                    }
                    else {
                        setGraphic(container);
                    }
                }
            }
        };
    }
    private Callback<TableColumn, TableCell> createButtonCellFactory() {
        return param -> new TableCell() {
            private final HBox container = new HBox();
            private final Button modifyBtn = new Button("Modify");
            private final Button deleteBtn = new Button("Delete");

            {
                container.setSpacing(5);
                container.getChildren().addAll(modifyBtn, deleteBtn);

                modifyBtn.setOnAction(event -> {
                    Product product = (Product) getTableView().getItems().get(getIndex());
                    Dialog dialog = new Dialog();
                    dialog.setTitle("Modify Info");
                    dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
                    dialog.getDialogPane().setContent(createProductInfoForm(product));
                    dialog.show();
//                    productTable.getItems().removeAll();
//                    productTable.getItems().addAll(merchant.getProducts());



                    System.out.println("Modified product: " + product.getName());
                });

                deleteBtn.setOnAction(event -> {
                    Product product = (Product) getTableView().getItems().get(getIndex());
                    merchant.deleteProductItem(product);
                    updateProductTable();
//                    productTable.getItems().remove(product);
                    System.out.println("Deleted product: " + product.getName());
                });
            }

            @Override
            protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : container);
            }
        };
    }

    private void updateMerchantInfo() {
        if (merchant == null) {
            System.err.println("Merchant is null!");
            return;
        }
//        merchantInfoLabel.setText("商家ID: " + merchant.getId() + "\n地址: " + merchant.getAddress());

        shopname.setText(merchant.getShopname());

        if (!merchant.getPhone().isEmpty()) {
            phone.setText(merchant.getPhone());
        }

        address.setText(merchant.getAddress());


        merchantScoreLabel.setText(merchant.getScore());

    }


    private void updateOrderData() {
        ObservableList<Order> data = FXCollections.observableArrayList();
        data.addAll(merchant.getOrders());
        //double feedback = data.stream().filter(ele->!ele.getIsShipped()||ele.getFeedback().isNaN()).forEach();
        orderTable.setItems(data);
    }

    @FXML
    private void handleAddProduct(ActionEvent event) {

        // 添加商品
        //Product product = new Product("", 0, 0, merchant.getId(), "");
        Dialog dialog = new Dialog();
        dialog.setTitle("Modify Info");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.getDialogPane().setContent(createNewProductInfoForm());
        dialog.show();
        //merchant.getProducts().add(product);

            //System.out.println("Modified product: " + product.getName());

    }




    public void onModifyInfo(ActionEvent actionEvent) {

        Dialog dialog = new Dialog();
        dialog.setTitle("Modify Info");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.getDialogPane().setContent(createInfoForm(shopname.getText(),phone.getText(),address.getText()));
        dialog.show();

    }
    private Node createInfoForm(String shopName, String phone, String address){
        GridPane gridPane = new GridPane();
        TextField newShopName = new TextField(shopName);
        TextField newPhone = new TextField(phone);
        TextField newAddress = new TextField(address);
        gridPane.add(new Label("shop name: "), 0, 0);
        gridPane.add(new Label("Phone: "), 0, 1);
        gridPane.add(new Label("Address: "), 0, 2);
        gridPane.add(newShopName, 1, 0);
        gridPane.add(newPhone, 1, 1);
        gridPane.add(newAddress, 1, 2);
        Button button = new Button("Submit");
        gridPane.add(button, 0, 3);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                merchant.updateMerchantInfo(newShopName.getText(), newPhone.getText(), newAddress.getText());
                ((Stage) (((Button) actionEvent.getSource()).getScene().getWindow())).close();
                updateMerchantInfo();
            }
        });


        return gridPane;
    }

    private Node createProductInfoForm(Product product) {
        GridPane gridPane = new GridPane();
        TextField newProductName = new TextField(product.getName());
        TextField newProductStock = new TextField(String.valueOf(product.getStock()));
        TextField newProductTag1 = new TextField(product.getTag(1));
        TextField newProductTag2 = new TextField(product.getTag(2));
        TextField newProductTag3 = new TextField(product.getTag(3));
        TextField newProductPrice = new TextField(String.valueOf(product.getPrice()));
        gridPane.add(new Label("product name: "), 0, 0);
        gridPane.add(new Label("price: "), 0, 1);
        gridPane.add(new Label("stock: "), 0, 2);
        gridPane.add(new Label("tag1: "), 0, 3);
        gridPane.add(new Label("tag2: "), 0, 4);
        gridPane.add(new Label("tag3: "), 0, 5);
        gridPane.add(newProductName, 1, 0);
        gridPane.add(newProductPrice, 1, 1);
        gridPane.add(newProductStock, 1, 2);
        gridPane.add(newProductTag1, 1, 3);
        gridPane.add(newProductTag2, 1, 4);
        gridPane.add(newProductTag3, 1, 5);
        Button button = new Button("Modify");
        gridPane.add(button, 0, 6);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                merchant.modifyProductInfo(newProductName.getText(), newProductPrice.getText(),newProductStock.getText(), newProductTag1.getText(),newProductTag2.getText(),newProductTag3.getText(),product);
                ((Stage) (((Button) actionEvent.getSource()).getScene().getWindow())).close();
                updateProductTable();
                orderTable.refresh();

            }
        });


        return gridPane;

    }

    private Node createNewProductInfoForm() {
        GridPane gridPane = new GridPane();
        TextField newProductName = new TextField();
        TextField newProductStock = new TextField();
        TextField newProductTag1 = new TextField();
        TextField newProductTag2 = new TextField();
        TextField newProductTag3 = new TextField();
        TextField newProductPrice = new TextField();
        gridPane.add(new Label("product name: "), 0, 0);
        gridPane.add(new Label("price: "), 0, 1);
        gridPane.add(new Label("stock: "), 0, 2);
        gridPane.add(new Label("tag1: "), 0, 3);
        gridPane.add(new Label("tag2: "), 0, 4);
        gridPane.add(new Label("tag3: "), 0, 5);
        gridPane.add(newProductName, 1, 0);
        gridPane.add(newProductPrice, 1, 1);
        gridPane.add(newProductStock, 1, 2);
        gridPane.add(newProductTag1, 1, 3);
        gridPane.add(newProductTag2, 1, 4);
        gridPane.add(newProductTag3, 1, 5);
        Button button = new Button("Add");
        gridPane.add(button, 0, 6);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                merchant.addNewProduct(newProductName.getText(), newProductPrice.getText(),newProductStock.getText(), newProductTag1.getText(),newProductTag2.getText(),newProductTag3.getText());
                ((Stage) (((Button) actionEvent.getSource()).getScene().getWindow())).close();
                updateProductTable();
            }
        });


        return gridPane;

    }


}
