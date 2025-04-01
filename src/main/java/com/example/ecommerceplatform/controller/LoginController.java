package com.example.ecommerceplatform.controller;

import com.example.ecommerceplatform.model.DatabaseConnection;
import com.example.ecommerceplatform.model.Merchant;
import com.example.ecommerceplatform.model.Consumer;
import com.example.ecommerceplatform.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


public class LoginController {
    @FXML
    private ComboBox<String> roleComboBox;
    @FXML private TextField idField;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        roleComboBox.getItems().addAll("商家", "消费者");
    }

    @FXML
    public void handleLoginIn(ActionEvent event) throws IOException {
        String role = roleComboBox.getValue();
        String phone = idField.getText();


        if (role != null && !phone.isEmpty()) {

            Parent root;
            if (role.equals("商家")) {



                Merchant merchant = Merchant.getMerchantByPhone(phone);
                if (merchant == null) { // 不能为空
                    System.err.println("商家信息加载失败");
                    return;
                }

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MerchantView.fxml"));
                root = loader.load();
                MerchantController merchantController = loader.getController();
                merchantController.setMerchant(merchant);


                stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
            else {
                FXMLLoader loader = new FXMLLoader((getClass().getResource("/view/ConsumerView.fxml")));
                root = loader.load();

                //ResultSet CustomerInfo = User.getIdByPhone(phone, "consumer");

                ConsumerController consumerController = loader.getController();
                consumerController.setConsumer(Consumer.getCustomerByPhone(phone));

                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }

        }
    }








}
