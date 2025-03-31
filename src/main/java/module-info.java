module com.example.ecommerceplatform {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.ecommerceplatform to javafx.fxml;
    opens com.example.ecommerceplatform.controller to javafx.fxml; // 开放 controller 包
    opens com.example.ecommerceplatform.model to javafx.base,javafx.fxml;
    exports com.example.ecommerceplatform;
    exports com.example.ecommerceplatform.controller; // 导出 controller 包
}