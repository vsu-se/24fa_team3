package com.example.demo;

import java.util.Date;
import java.io.IOException;

import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;

public class ReportsTab {
    private Tab reportsTab;
    private TextArea reportArea;
    private Button generateSellersReportButton;
    private Button generateBuyersReportButton;
    private Button generateBidHistoryReportButton;
    private Button saveDataButton;
    private Button loadDataButton;

    public ReportsTab() {
        reportsTab = new Tab("Reports");
        reportArea = new TextArea();
        generateSellersReportButton = new Button("Generate Seller's Report");
        generateBuyersReportButton = new Button("Generate Buyer's Report");
        generateBidHistoryReportButton = new Button("Generate Bid History Report");
        saveDataButton = new Button("Save Data");
        loadDataButton = new Button("Load Data");

        reportArea.setEditable(false);
        VBox reportsContent = new VBox(generateSellersReportButton, generateBuyersReportButton, generateBidHistoryReportButton, saveDataButton, loadDataButton, reportArea);
        reportsTab.setContent(reportsContent);

        // Add event handler for the seller's report button
        generateSellersReportButton.setOnAction(event -> {
            // Generate the seller's report
            SellersReport report = SellersReport.getInstance();

            // Add some sample sold items
            report.addSoldItem(new Item("Item 1", 100.00, SettingsConfig.getInstance().getSellerCommission(), 5.00, new Date()));
            report.addSoldItem(new Item("Item 2", 200.00, SettingsConfig.getInstance().getSellerCommission(), 10.00, new Date(System.currentTimeMillis() - 100000)));
            report.addSoldItem(new Item("Item 3", 150.00, SettingsConfig.getInstance().getSellerCommission(), 7.50, new Date(System.currentTimeMillis() - 200000)));

            String reportText = report.generateReport();
            reportArea.setText(reportText);
        });

        // Add event handler for the buyer's report button
        generateBuyersReportButton.setOnAction(event -> {
            // Generate the buyer's report
            BuyersReport report = BuyersReport.getInstance();

            // Add some sample bought items
            report.addBoughtItem(new Item("Item 1", 100.00, SettingsConfig.getInstance().getBuyerPremium(), 5.00, new Date()));
            report.addBoughtItem(new Item("Item 2", 200.00, SettingsConfig.getInstance().getBuyerPremium(), 10.00, new Date(System.currentTimeMillis() - 100000)));
            report.addBoughtItem(new Item("Item 3", 150.00, SettingsConfig.getInstance().getBuyerPremium(), 7.50, new Date(System.currentTimeMillis() - 200000)));

            String reportText = report.generateReport();
            reportArea.setText(reportText);
        });

        // Add event handler for the bid history report button
        generateBidHistoryReportButton.setOnAction(event -> {
            // Generate the bid history report
            EndedAuction endedAuction = new EndedAuction("Sample Item", "2023-10-01", 300.00);

            // Add some sample bids
            endedAuction.addBid(new Bid(300.00, new Date(), "User1"));
            endedAuction.addBid(new Bid(150.00, new Date(System.currentTimeMillis() - 100000), "User2"));
            endedAuction.addBid(new Bid(200.00, new Date(System.currentTimeMillis() - 200000), "User3"));

            String reportText = endedAuction.generateBidHistoryReport();
            reportArea.setText(reportText);
        });

        // Add event handler for the save data button
        saveDataButton.setOnAction(event -> {
            try {
                SettingsConfig.getInstance().saveToFile("settings.dat");
                SellersReport.getInstance().saveToFile("sellers_report.dat");
                BuyersReport.getInstance().saveToFile("buyers_report.dat");
                reportArea.setText("Data saved successfully.");
            } catch (IOException e) {
                reportArea.setText("Failed to save data: " + e.getMessage());
            }
        });

        // Add event handler for the load data button
        loadDataButton.setOnAction(event -> {
            try {
                SettingsConfig.loadFromFile("settings.dat");
                SellersReport.loadFromFile("sellers_report.dat");
                BuyersReport.loadFromFile("buyers_report.dat");
                reportArea.setText("Data loaded successfully.");
            } catch (IOException | ClassNotFoundException e) {
                reportArea.setText("Failed to load data: " + e.getMessage());
            }
        });
    }

    public Tab getReportsTab() {
        return reportsTab;
    }
}
