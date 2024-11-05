package com.example.demo;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.layout.Priority;

public class ActiveAuction {
    private String itemName;
    private double currentBid;
    private String timer;
    private boolean isOwnedByOwner;
    private HBox auctionDisplay;

    public ActiveAuction (String itemName, String timer, double currentBid, boolean isOwnedByUser) {
        this.itemName = itemName;
        this.timer = timer;
        this.currentBid = currentBid;
        this.isOwnedByOwner = isOwnedByUser;

        auctionDisplay = new HBox(10);

        Text itemText = new Text(itemName);
        Text timeLimit = new Text("\nTime until auction is over: " + timer);
        Text bidText = new Text (String.format("Highest Bid is $%.2f ", currentBid));

        HBox.setHgrow(itemText, Priority.ALWAYS);

        if(isOwnedByUser) {
            MenuButton userActions = new MenuButton("Actions");
            MenuItem deleteItem = new MenuItem("Delete Auction");
            MenuItem endAuctionItem = new MenuItem("End Auction");

            deleteItem.setOnAction(event -> {
                // Add logic for deleting the auction
                System.out.println("Deleting auction: " + itemName);
            });

            endAuctionItem.setOnAction(event -> {
                // Add logic for ending the auction
                System.out.println("Ending auction: " + itemName);
            });

            userActions.getItems().addAll(deleteItem, endAuctionItem);
            auctionDisplay.getChildren().addAll(itemText, timeLimit, bidText, userActions);
        
        } else {
            
            Button placeBidButton = new Button("Place Bid");
        placeBidButton.setOnAction(event -> {
            System.out.println("Placing bid on: " + itemName);
        });

        auctionDisplay.getChildren().addAll(itemText , timeLimit , bidText, placeBidButton);
        }

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
