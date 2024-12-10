package com.example.demo.controllers;

import com.example.demo.models.Auction;
import com.example.demo.models.Category;
import com.example.demo.models.Item;
import com.example.demo.utils.AuctionManager;
import com.example.demo.utils.TimeManager;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
    @FXML
    private DatePicker ItemEndDatePicker;

    @FXML
    private TextField ItemEndTimeTextField;

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
        LocalDate today = LocalDate.now();

        ItemEndDatePicker.setDayCellFactory(new Callback<DatePicker, javafx.scene.control.DateCell>() {
            @Override
            public javafx.scene.control.DateCell call(DatePicker datePicker) {
                return new javafx.scene.control.DateCell() {
                    @Override
                    public void updateItem(LocalDate date, boolean empty) {
                        super.updateItem(date, empty);
                        // Disable dates before today
                        if (date.isBefore(today)) {
                            setDisable(true);
                            setStyle("-fx-background-color: #f0f0f0;");  // Optional: set a style for disabled dates
                        }
                    }
                };
            }
        });


    }

    // public void handleSubmit() {
    //     // Get the date from the DatePicker
    //     LocalDate selectedDate = ItemEndDatePicker.getValue();
        
    //     // Get the time from the TextField
    //     String timeString = ItemEndTimeTextField.getText();
    //     LocalTime selectedTime = null;

    //     try {
    //         selectedTime = LocalTime.parse(timeString, DateTimeFormatter.ofPattern("HH:mm"));
    //     } catch (Exception e) {
    //         System.out.println("Invalid time format. Please enter in HH:MM format.");
    //         return;  // Handle invalid time format
    //     }

    //     // Combine date and time into a LocalDateTime
    //     if (selectedDate != null && selectedTime != null) {
    //         LocalDateTime dateTime = LocalDateTime.of(selectedDate, selectedTime);
    //         System.out.println("Selected Date and Time: " + dateTime);
    //     }
    // }

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

    private void timeClock() {
    Thread thread = new Thread(() -> {
        // Define the full date and time format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy HH:mm:ss");

        while (!stop) {
            try {
                Thread.sleep(1000); // Sleep for 1 second
            } catch (Exception e) {
                System.out.println(e);
            }

            // Get the current date and time from TimeManager
            final String timeNow = TimeManager.getInstance().now().format(formatter);

            // Update the UI on the JavaFX Application Thread
            Platform.runLater(() -> {
                time.setText(timeNow);
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
            String dateString = ItemEndDatePicker.getValue().toString();
            String timeString = ItemEndTimeTextField.getText();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-ddHH:mm");
            Date itemEndDate = null;
            try {
                itemEndDate = dateFormat.parse(dateString + timeString);
                System.out.println("Parsed Date: " + itemEndDate);
            } catch (ParseException e) {
                System.out.println("Error parsing the date: " + e.getMessage());
            }           

            Auction item = new Auction(itemName, itemEndDate, true, false, itemPrice, new Category(category));

            AuctionManager.getInstance().addAuction(item);

            // for (Category categories : categorise) {
            //     if (category.equals(categories.getName())) {
            //         categories.addItem(item);
            //     }
            // }
        }

    }
