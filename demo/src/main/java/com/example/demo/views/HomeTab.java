package com.example.demo.views;

import com.example.demo.controllers.HomeTabController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeTab extends Application {

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HomeTab.class.getResource("hello-view.fxml"));
            FXMLLoader fxmlLoaderSettingTabs = new FXMLLoader(SettingsTab.class.getResource("settingsTab-view.fxml"));

            Parent settingsTabRoot = fxmlLoaderSettingTabs.load();
            Parent root = fxmlLoader.load();

            // Create TabPane
            TabPane tabPane = new TabPane();

            // Disables Tabs from being able to be deleted
            tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

            // Create initial tab
            Tab initialTab = new Tab("Home");
            initialTab.setContent(root);

            AuctionsTab auctionInstance = new AuctionsTab();
            HomeTabController instanceHomeTabController = new HomeTabController();

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
            Scene scene = new Scene(tabPane, 600, 600);
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