module com.example.demo {
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    exports com.example.demo.controllers;
    exports com.example.demo.models;
    exports com.example.demo.utils;
    exports com.example.demo.views;

    opens com.example.demo.controllers to javafx.fxml;
    opens com.example.demo.models to javafx.fxml;
    opens com.example.demo.utils to javafx.fxml;
    opens com.example.demo.views to javafx.fxml;
}