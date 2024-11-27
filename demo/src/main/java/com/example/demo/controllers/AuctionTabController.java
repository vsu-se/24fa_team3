package com.example.demo.controllers;

import com.example.demo.models.Auction;
import com.example.demo.models.Category;
import com.example.demo.views.AuctionsTab;

import javafx.collections.FXCollections;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;

import java.util.List;

public class AuctionTabController {

    private static AuctionTabController instance = new AuctionTabController();
    private AuctionsTab auctionTab;

    public AuctionTabController() {
    }

    public static AuctionTabController getInstance() {
        return instance;
    }

    public void setAuctionTab(AuctionsTab auctionTab) {
        this.auctionTab = auctionTab;
    }

    public HBox createActiveAuctionDisplay(Auction a) {
        HBox auctionDisplay = new HBox(10);

        Text itemText = new Text(a.getItemName());
        Text timeLimit = new Text("\nTime until auction is over: " + a.getTimer());
        Text bidText = new Text(String.format("Highest Bid is $%.2f ", a.getCurrentBid()));

        HBox.setHgrow(itemText, Priority.ALWAYS);

        auctionDisplay.getChildren().addAll(itemText, timeLimit, bidText);

        if (a.isOwnedByUser()) {
            MenuButton userActions = new MenuButton("Actions");
            MenuItem deleteItem = new MenuItem("Delete Auction");
            MenuItem endAuctionItem = new MenuItem("End Auction");

            deleteItem.setOnAction(event -> {
                a.deleteAuction();
                auctionTab.getAuctionsListView().getItems().remove(auctionDisplay);
            });

            endAuctionItem.setOnAction(event -> {
                a.endAuction();
                auctionTab.getAuctionsListView().getItems().remove(auctionDisplay);
            });

            userActions.getItems().addAll(deleteItem, endAuctionItem);
            auctionDisplay.getChildren().add(userActions);
        }

        return auctionDisplay;
    }

    public HBox createEndedAuctionDisplay(Auction a) {
        HBox auctionDisplay = new HBox(10);

        Text itemText = new Text(a.getItemName());
        Text endDateText = new Text("Auction ended on: " + a.getEndDate());
        Text bidHistoryText = new Text(a.generateBidHistoryReport());

        HBox.setHgrow(itemText, Priority.ALWAYS);

        auctionDisplay.getChildren().addAll(itemText, endDateText, bidHistoryText);

        return auctionDisplay;
    }


}
