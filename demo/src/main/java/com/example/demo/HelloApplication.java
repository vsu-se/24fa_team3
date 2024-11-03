package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
        try {
            // Load the main view
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
            Parent root = fxmlLoader.load();

            // Create TabPane
            TabPane tabPane = new TabPane();

            // Create initial tab
            Tab initialTab = new Tab("Home");
            initialTab.setContent(root);

            // Create reports tab
            ReportsTab reportsTab = new ReportsTab();

            // Create auctions tab
            AuctionsTab auctionsTab = new AuctionsTab();

            // Add all the tabs
            tabPane.getTabs().addAll(initialTab, auctionsTab.getAuctionsTab() ,reportsTab.getReportsTab());
            tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

            // Set up scene and stage
            Scene scene = new Scene(tabPane, 320, 240);
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}