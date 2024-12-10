package com.example.demo.views;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.demo.controllers.SettingsConfig;
import com.example.demo.utils.TimeManager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SettingsTab {

    @FXML
    public Button SellerCommissionBtn;
    @FXML
    private DatePicker SystemDateSetter;
    @FXML
    private TextField SystemTimeSetter;
    @FXML
    public Button SetSystemTimeButton;
    public TextField SellerCommissionTxtF;
    public Button BuyerPremiumBtn;
    public TextField BuyerPremiumTxtF;
    public Label sellerCommissionErrorLbl;
    public Label BuyerPremiumErrorLbl;

    private static SettingsTab instance = new SettingsTab();

    public static SettingsTab getInstance() {
        return instance;
    }

    @FXML
    private void sellerCommission(ActionEvent actionEvent) {
        try {
            sellerCommissionErrorLbl.setVisible(false);
            String check = SellerCommissionTxtF.getText();
            double value = 0;
            if (!check.isEmpty() && check.matches("[0-9]+(\\.[0-9]+)?")) {
                value = Double.parseDouble(SellerCommissionTxtF.getText());
                if (value > -1) {
                    SettingsConfig.getInstance().setSellerCommission(value);
                }
            } else {
                sellerCommissionErrorLbl.setVisible(true);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    private void submitDateAndTime(ActionEvent actionEvent) {
        try {
            LocalDate selectedDate = SystemDateSetter.getValue();

            String timeText = SystemTimeSetter.getText();

            if (selectedDate == null || timeText == null || !timeText.matches("\\d{2}:\\d{2}")) {
                System.out.println("Invalid date or time entered.");
                return;
            }

            String[] timeParts = timeText.split(":");
            int hour = Integer.parseInt(timeParts[0]);
            int minute = Integer.parseInt(timeParts[1]);

            // Combine date and time into a LocalDateTime
            LocalDateTime dateTime = selectedDate.atTime(hour, minute);

            TimeManager.getInstance().adjustTime(dateTime);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    @FXML
    private void buyerPremium(ActionEvent actionEvent) {
        try {
            BuyerPremiumErrorLbl.setVisible(false);
            String check = BuyerPremiumTxtF.getText();
            double value = 0;
            if (!check.isEmpty() && check.matches("[0-9]+(\\.[0-9]+)?")) {
                value = Double.parseDouble(BuyerPremiumTxtF.getText());
                if (value > -1) {
                    SettingsConfig.getInstance().setBuyerPremium(value);
                }
            } else {
                BuyerPremiumErrorLbl.setVisible(true);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
