package com.example.ecommerceplatform;

import com.example.ecommerceplatform.controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginView.fxml"));
        URL url = getClass().getResource("/view/LoginView.fxml");
        if (url == null) {
            System.err.println("FXML file not found!");
        } else {
            System.out.println("FXML file found: " + url);
        }
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();
        LoginController controller = loader.getController();
        controller.setStage(primaryStage);
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("登录");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
