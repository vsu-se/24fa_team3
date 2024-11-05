package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SystemController {

    @FXML
    public Button SellerCommissionBtn;
    public TextField SellerCommissionTxtF;
    public Button BuyerPremiumBtn;
    public TextField BuyerPremiumTxtF;
    public Label sellerCommissionErrorLbl;
    public Label BuyerPremiumErrorLbl;

    private static SystemController instance = new SystemController();

    public static SystemController getInstance() {
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
            }
            else {
                sellerCommissionErrorLbl.setVisible(true);
            }
        }
        catch (Exception e) {
            System.out.println(e);
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
            }
            else {
                BuyerPremiumErrorLbl.setVisible(true);
            }

            double result = SettingsConfig.getInstance().getBuyerPremium();
            System.out.println("Result" + result);
        }
        catch (Exception e) {
            System.out.println(e);
        }


    }





}
