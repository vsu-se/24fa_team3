package com.example.demo.controllers;

import com.example.demo.models.Auction;
import com.example.demo.models.Category;
import com.example.demo.utils.AuctionManager;
import com.example.demo.utils.TimeManager;
import com.example.demo.views.AuctionsTab;

import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

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

    public String getFormattedTimeElapsed(Auction a) {
        return TimeManager.getInstance().getFormattedTimeElapsed(a.getEndDate().getTime() / 1000 - TimeManager.getInstance().now().atZone(ZoneId.systemDefault())
                                                .toInstant()
                                                .getEpochSecond());
    }

    
    public HBox createActiveAuctionDisplay(Auction a) {
        HBox auctionDisplay = new HBox(10);

        Text itemText = new Text(a.getItemName());
        Text timeLimit;
        if (a.isEnded()) {
            timeLimit = new Text("\nAuction ended on: " + a.getEndDate());
        }
        else {
            timeLimit = new Text("\nTime until auction is over: " + getFormattedTimeElapsed(a));
        }
        Text bidText = new Text(String.format("Highest Bid is $%.2f ", a.getCurrentBid()));

        HBox.setHgrow(itemText, Priority.ALWAYS);

        auctionDisplay.getChildren().addAll(itemText, timeLimit, bidText);

        if (a.isOwnedByUser()) {
            MenuButton userActions = new MenuButton("Actions");
            MenuItem deleteItem = new MenuItem("Delete Auction");
            MenuItem endAuctionItem = new MenuItem("End Auction");

            deleteItem.setOnAction(event -> {
                AuctionsTab.getInstance().updateCategoryList();
                //a.deleteAuction();
               // auctionTab.getAuctionsListView().getItems().remove(auctionDisplay);
                AuctionManager.getInstance().removeAuction(a);
                auctionTab.getAuctionsListView().getItems().remove(auctionDisplay);

            });

            endAuctionItem.setOnAction(event -> {
                a.endAuction();
                auctionTab.getAuctionsListView().getItems().remove(auctionDisplay);
            });

            userActions.getItems().addAll(deleteItem, endAuctionItem);
            auctionDisplay.getChildren().add(userActions);
        }
        else {
            MenuButton userActions = new MenuButton("Actions");
            MenuItem deleteItem = new MenuItem("Delete Auction");
            MenuItem endAuctionItem = new MenuItem("End Auction");
            MenuItem bidOnAuctionItem = new MenuItem("Place Bid");

            deleteItem.setOnAction(event -> {
                a.deleteAuction();
                auctionTab.getAuctionsListView().getItems().remove(auctionDisplay);
            });

            endAuctionItem.setOnAction(event -> {
                a.endAuction();
                auctionTab.getAuctionsListView().getItems().remove(auctionDisplay);
            });

            bidOnAuctionItem.setOnAction(actionEvent -> {
                Stage newWindow = new Stage();

                StackPane secondaryRoot = new StackPane();
                //secondaryRoot.getChildren().add();
                Scene newWindowScene = new Scene(secondaryRoot, 200, 150);

                newWindow.setScene(newWindowScene);
                newWindow.show();

            });

            userActions.getItems().addAll(deleteItem, endAuctionItem, bidOnAuctionItem);
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
