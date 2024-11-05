package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
            FXMLLoader fxmlLoaderSettingTabs = new FXMLLoader(SystemController.class.getResource("settingsTab-view.fxml"));

            Parent settingsTabRoot = fxmlLoaderSettingTabs.load();
            Parent root = fxmlLoader.load();

            // Create TabPane
            TabPane tabPane = new TabPane();

            // Disables Tabs from being able to be deleted
            tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

            // Create initial tab
            Tab initialTab = new Tab("Home");
            initialTab.setContent(root);

            // Create and add reports tab
            ReportsTab reportsTab = new ReportsTab();

            // Create and add settings tab
            Tab settingsTab = new Tab("Settings");
            settingsTab.setContent(settingsTabRoot);


            // Create auctions tab
            AuctionsTab auctionsTab = new AuctionsTab();

            // Add all the tabs
            tabPane.getTabs().addAll(initialTab, auctionsTab.getAuctionsTab() ,reportsTab.getReportsTab(), settingsTab);
            tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

            // Set up scene and stage
            Scene scene = new Scene(tabPane, 500, 500);
            stage.setTitle("Welcome to GAI");
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