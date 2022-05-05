module com.example.v1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.swing;


    opens com.example.v1 to javafx.fxml;
    exports com.example.v1;
}