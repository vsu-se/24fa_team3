package com.example.demo.views;

import com.example.demo.controllers.HomeTabController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeTab extends Application {

    private String userType;

    public HomeTab(String userType) {
        this.userType = userType;
    }

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            FXMLLoader fxmlLoaderSettingTabs = new FXMLLoader(getClass().getResource("settingsTab-view.fxml"));

            Parent settingsTabRoot = fxmlLoaderSettingTabs.load();
            Parent root = fxmlLoader.load();

            TabPane tabPane = new TabPane();
            tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

            Tab initialTab = new Tab("Home");
            initialTab.setContent(root);

            AuctionsTab auctionsTab = new AuctionsTab();
            ReportsTab reportsTab = new ReportsTab();
            Tab settingsTab = new Tab("Settings");
            settingsTab.setContent(settingsTabRoot);

            switch (userType) {
                case "Buyer":
                    tabPane.getTabs().addAll(initialTab, auctionsTab.getAuctionsTab(), reportsTab.getReportsTab());
                    break;
                case "Seller":
                    tabPane.getTabs().addAll(initialTab, auctionsTab.getAuctionsTab(), reportsTab.getReportsTab());
                    break;
                case "Admin":
                    tabPane.getTabs().addAll(initialTab, auctionsTab.getAuctionsTab(), reportsTab.getReportsTab(), settingsTab);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid user type: " + userType);
            }

            Scene scene = new Scene(tabPane, 625, 600);
            stage.setTitle("Welcome to GAI, " + userType);
            Image icon = new Image(getClass().getResourceAsStream("/gai_logo.png"));
            stage.getIcons().add(icon);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
