package com.example.demo;

import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.layout.Priority;

public class ActiveAuction {
    private String itemName;
    private double currentBid;
    private String timer;
    private HBox auctionDisplay;

    public ActiveAuction (String itemName, String timer, double currentBid) {
        this.itemName = itemName;
        this.timer = timer;
        this.currentBid = currentBid;

        auctionDisplay = new HBox();

        Text itemText = new Text(itemName);
        Text timeLimit = new Text("Time until auction is over: " + timer);
        Text bidText = new Text (String.format("Highest Bid is: ", currentBid));

        HBox.setHgrow(itemText, Priority.ALWAYS);

        auctionDisplay.getChildren().addAll(itemText , timeLimit , bidText);
    }

    public HBox getAuctionDisplay() {
        return auctionDisplay;
    }

    // Getters for item data (optional, if you need to access individual fields)
    public String getItemName() {
        return itemName;
    }

    public String getTimer() {
        return timer;
    }

    public double getCurrentBid() {
        return currentBid;
    }
}