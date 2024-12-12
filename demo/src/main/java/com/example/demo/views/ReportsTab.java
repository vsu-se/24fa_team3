package com.example.demo.views;

import java.util.Date;
import java.util.List;

import com.example.demo.controllers.SettingsConfig;
import com.example.demo.utils.AuctionManager;
import com.example.demo.models.Auction;
import com.example.demo.models.Bid;
import com.example.demo.models.BuyersReport;
import com.example.demo.models.EndedAuction;
import com.example.demo.models.Item;
import com.example.demo.models.SellersReport;

import java.io.IOException;

import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;

public class ReportsTab {
    private Tab reportsTab;
    private TextArea reportArea;
    private Button generateSellersReportButton;
    private Button generateBuyersReportButton;
    private Button generateBidHistoryReportButton;
    private Button saveDataButton;
    private Button loadDataButton;
    private ComboBox<Auction> auctionComboBox; // ComboBox to select auction

    public ReportsTab() {
        reportsTab = new Tab("Reports");
        reportArea = new TextArea();
        generateSellersReportButton = new Button("Generate Seller's Report");
        generateBuyersReportButton = new Button("Generate Buyer's Report");
        generateBidHistoryReportButton = new Button("Generate Bid History Report");
        saveDataButton = new Button("Save Data");
        loadDataButton = new Button("Load Data");
        auctionComboBox = new ComboBox<>(); // Create ComboBox

        reportArea.setEditable(false);

        // Add auctions to ComboBox
        List<Auction> auctions = AuctionManager.getInstance().getAllAuctions(); // Get all auctions
        auctionComboBox.getItems().addAll(auctions); // Add auctions to the ComboBox
        VBox reportsContent = new VBox();
        switch (SettingsConfig.getInstance().getUserType()) {
            case "Seller":
                reportsContent = new VBox(auctionComboBox, generateSellersReportButton,
                generateBidHistoryReportButton, reportArea);
                break;
            case "Buyer":
                reportsContent = new VBox(auctionComboBox, generateBuyersReportButton,
                generateBidHistoryReportButton, reportArea);
                break;
            case "Admin":
                reportsContent = new VBox(auctionComboBox, generateSellersReportButton, generateBuyersReportButton,
                generateBidHistoryReportButton, saveDataButton, loadDataButton, reportArea);
                break;
        }
         
        reportsTab.setContent(reportsContent);

        // Add event handler for the seller's report button
        generateSellersReportButton.setOnAction(event -> {
            // Generate the seller's report
            SellersReport report = SellersReport.getInstance();
            reportArea.clear();
            SellersReport.getInstance().clearSoldItems();
            for (Auction auction : AuctionManager.getInstance().getUserAuctions()) {
                if (auction.isEnded()) {
                    if (auction.getBidHistory().size() > 0) {
                        report.addSoldItem(new Item(auction.getItemName(), auction.getCurrentBid().getAmount(), SettingsConfig.getInstance().getSellerCommission(), 5, auction.getEndDate()));
                    }
                    else {
                        report.addSoldItem(new Item(auction.getItemName(), 0, SettingsConfig.getInstance().getSellerCommission(), 5, auction.getEndDate()));
                    }
                }
            }

            String reportText = report.generateReport();
            reportArea.setText(reportText);
        });

        // Add event handler for the buyer's report button
        generateBuyersReportButton.setOnAction(event -> {
            // Generate the buyer's report
            BuyersReport report = BuyersReport.getInstance();
            reportArea.clear();
            BuyersReport.getInstance().clearBoughtItems();
            for (Auction auction : AuctionManager.getInstance().getEndedAuctions()) {
                if (!auction.isOwnedByUser() && auction.getBidHistory().size() > 0) {
                    report.addBoughtItem(new Item(auction.getItemName(), auction.getCurrentBid().getAmount(), SettingsConfig.getInstance().getBuyerPremium(), 5, auction.getEndDate()));
                }
            }
            String reportText = report.generateReport();
            reportArea.setText(reportText);
        });

        // Add event handler for the bid history report button
        generateBidHistoryReportButton.setOnAction(event -> {
            // Get the selected auction from ComboBox
            Auction selectedAuction = auctionComboBox.getValue();

            if (selectedAuction != null) {
                // Generate the bid history report for the selected auction
                String reportText = selectedAuction.generateBidHistoryReportFull();
                reportArea.setText(reportText);
            } else {
                reportArea.setText("Please select an auction.");
            }
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
