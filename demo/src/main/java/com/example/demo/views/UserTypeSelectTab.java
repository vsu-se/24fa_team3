package com.example.demo.views;

import com.example.demo.controllers.HomeTabController;
import com.example.demo.controllers.SettingsConfig;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class UserTypeSelectTab extends Application {

    @FXML
    private RadioButton buyerRadio;
    @FXML
    private RadioButton sellerRadio;
    @FXML
    private RadioButton adminRadio;

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("user-view.fxml"));
            Parent root = fxmlLoader.load();

            Scene scene = new Scene(root);
            primaryStage.setTitle("Select User Type");
            primaryStage.setScene(scene);
            Image icon = new Image(getClass().getResource("/gai_logo.png").toExternalForm());
            primaryStage.getIcons().add(icon);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showErrorDialog("Failed to load the user selection screen.");
        }
    }

    @FXML
    private void initialize() {
        ToggleGroup group = new ToggleGroup();
        buyerRadio.setToggleGroup(group);
        sellerRadio.setToggleGroup(group);
        adminRadio.setToggleGroup(group);
    }

    @FXML
    private void handleSubmit(ActionEvent event) {
        String userType = null;
        if (buyerRadio.isSelected()) {
            userType = "Buyer";
        } else if (sellerRadio.isSelected()) {
            userType = "Seller";
        } else if (adminRadio.isSelected()) {
            userType = "Admin";
        }

        if (userType == null) {
            showErrorDialog("Please select a user type.");
            return;
        }

        SettingsConfig.getInstance().setUserType(userType);
        System.out.println("User type selected: " + userType);

        loadHomeScreen((Stage) buyerRadio.getScene().getWindow(), userType);
    }

    private void loadHomeScreen(Stage stage, String userType) {
        HomeTab homeTab = new HomeTab(userType);
        homeTab.start(stage);
    }

    private void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
