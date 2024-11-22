package com.example.demo.views;

import com.example.demo.controllers.AuctionTabController;
import com.example.demo.models.Auction;
import com.example.demo.utils.AuctionManager;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class AuctionsTab {
    private static AuctionsTab instance = new AuctionsTab();
    private Tab auctionsTab;
    private ListView<HBox> auctionsListView;
    private Button viewEndedAuctions;
    private Button viewActiveAuctions;
    private Button viewUserAuctions;

    public AuctionsTab() {
        auctionsTab = new Tab("Auctions");

        auctionsListView = new ListView<>();

        viewUserAuctions = new Button("View My Auctions");
        viewActiveAuctions = new Button("View Active Auctions");
        viewEndedAuctions = new Button("View Ended Auctions");

        HBox buttonBox = new HBox(10, viewUserAuctions, viewActiveAuctions, viewEndedAuctions);
        buttonBox.setPadding(new Insets(10, 10, 10, 10));

        VBox auctionsContent = new VBox(buttonBox, auctionsListView);
        auctionsTab.setContent(auctionsContent);

        viewUserAuctions.setOnAction(event -> showUserAuctions());
        viewActiveAuctions.setOnAction(event -> showActiveAuctions());
        viewEndedAuctions.setOnAction(event -> showEndedAuctions());

        AuctionTabController.getInstance().setAuctionTab(this);
    }

    public static AuctionsTab getInstance() {
        return instance;
    }

    public ListView<HBox> getAuctionsListView() {
        return auctionsListView;
    }

    public Tab getAuctionsTab() {
        return auctionsTab;
    }

    private void showUserAuctions() {
        List<Auction> userAuctions = fetchUserAuctions();
        auctionsListView.getItems().clear();
        for (Auction auction : userAuctions) {
            HBox auctionDisplay = AuctionTabController.getInstance().createActiveAuctionDisplay(auction);
            auctionsListView.getItems().add(auctionDisplay);
        }
    }

    private void showActiveAuctions() {
        List<Auction> activeAuctions = fetchActiveAuctions();
        auctionsListView.getItems().clear();
        for (Auction auction : activeAuctions) {
            HBox auctionDisplay = AuctionTabController.getInstance().createActiveAuctionDisplay(auction);
            auctionsListView.getItems().add(auctionDisplay);
        }
    }

    private void showEndedAuctions() {
        List<Auction> endedAuctions = fetchEndedAuctions();
        auctionsListView.getItems().clear();
        for (Auction auction : endedAuctions) {
            HBox auctionDisplay = AuctionTabController.getInstance().createEndedAuctionDisplay(auction);
            auctionsListView.getItems().add(auctionDisplay);
        }
    }

    private List<Auction> fetchUserAuctions() {
        return AuctionManager.getInstance().getUserAuctions();
    }

    private List<Auction> fetchActiveAuctions() {
        return AuctionManager.getInstance().getActiveAuctions();
    }

    private List<Auction> fetchEndedAuctions() {
        return AuctionManager.getInstance().getEndedAuctions();
    }
}
