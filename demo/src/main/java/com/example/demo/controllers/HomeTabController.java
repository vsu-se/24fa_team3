package com.example.demo.controllers;

import com.example.demo.models.Category;
import com.example.demo.models.Item;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class HomeTabController implements Initializable {

    @FXML
    public Button removeCategory_Btn;
    public Button addCategory_Btn;
    public TextField categorySubmission_Lbl;
    public ListView<String> categoryList_LV;
    public Label categoryCheck_Lbl;
    public Label time;
    private volatile boolean stop = false;

    public Button ListItem_Btn;
    public TextField ItemName_TxtField;
    public TextField ItemPrice_TxtField;
    public TextField ItemShippingCost_TxtField;
    public TextField ItemEndDate_TxtField;
    public ComboBox<String> categoryPicker_ComboBox;


    public static ArrayList<Category> categorise = new ArrayList<>();

    public ObservableList<String> categoryStrings;

    private static HomeTabController instance = new HomeTabController();
    //private AuctionTabController atc = new AuctionTabController();


    public static HomeTabController getInstance() {
        return instance;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timeClock();
        categorise.add(new Category("Electronics"));
        categoryStrings = FXCollections.observableArrayList();
        for (Category category : categorise) {
            categoryStrings.add(category.getName());
        }
        categoryList_LV.setItems(categoryStrings);
        categoryPicker_ComboBox.setItems(categoryStrings);

    }

    public void addToCategory(ActionEvent actionEvent) {

        String categoryName = categorySubmission_Lbl.getText();

        if (categorise.isEmpty()) {
            Category newCategory = new Category(categoryName);
            categorise.add(newCategory);
            categoryStrings.add(categoryName);
        }
        else {
            boolean exists = false;
            for (Category s : categorise) {
                if (s.getName().equals(categoryName)) {
                    exists = true;
                    break;
                }
            }

            if (exists) {
                categoryCheck_Lbl.setText("Category is already created");
                categorySubmission_Lbl.clear();
            }
            else {
                Category newCategory = new Category(categoryName);
                categorise.add(newCategory);
                categoryStrings.add(categoryName);
                categorySubmission_Lbl.clear();
            }

        }


    }


    public void removeFromCategory(ActionEvent actionEvent) {

        String categoryName = categoryList_LV.getSelectionModel().getSelectedItem();

        for (int i = 0; i < categorise.size(); i++) {
            if (categoryName.equals(categorise.get(i).getName())) {
                categorise.remove(i);
                categoryStrings.remove(categoryName);
            }
        }
        categorySubmission_Lbl.clear();

    }


    public ArrayList<Category> getCategoryList() {
        return categorise;
    }

    private void timeClock () {
        Thread thread = new Thread(() -> {
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");
            while (!stop) {
                try {
                    Thread.sleep(1000);
                } catch(Exception e) {
                    System.out.println(e);
                }
                final String timenow = sdf.format(new Date());
                Platform.runLater(()->{
                    time.setText(timenow);
                });
            }

        });

        thread.start();
    }


    public void listItem(ActionEvent actionEvent) {
            String itemName = ItemName_TxtField.getText();
            double itemPrice = Double.parseDouble(ItemPrice_TxtField.getText());
            double itemShippingCost = Double.parseDouble((ItemShippingCost_TxtField.getText()));
            String category = categoryPicker_ComboBox.getSelectionModel().getSelectedItem();
            Item item = new Item(itemName, itemPrice, SettingsConfig.getInstance().getSellerCommission(), itemShippingCost);

            for (Category categories : categorise) {
                if (category.equals(categories.getName())) {
                    categories.addItem(item);
                }
            }
        }

    }
