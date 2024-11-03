package com.example.demo;

import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.layout.Priority;


// Class used to easily create Ended Auction listings in the "View Ended Auctions" tab
public class EndedAuction {
    private String itemName;
    private String endDate;
    private double finalPrice;
    private HBox auctionDisplay;

    public EndedAuction(String itemName, String endDate, double finalPrice) {
        this.itemName = itemName;
        this.endDate = endDate;
        this.finalPrice = finalPrice;

        // Setup HBox layout
        auctionDisplay = new HBox();

        Text itemText = new Text(itemName);
        Text dateText = new Text("\n Ended at " + endDate);
        Text priceText = new Text(String.format("Sold for $%.2f", finalPrice));

        HBox.setHgrow(itemText, Priority.ALWAYS);

        auctionDisplay.getChildren().addAll(itemText, dateText, priceText);
    }

    public HBox getAuctionDisplay() {
        return auctionDisplay;
    }

    // Getters for item data (optional, if you need to access individual fields)
    public String getItemName() {
        return itemName;
    }

    public String getEndDate() {
        return endDate;
    }

    public double getFinalPrice() {
        return finalPrice;
    }
}
