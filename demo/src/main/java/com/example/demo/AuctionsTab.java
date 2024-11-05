package com.example.demo;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;


public class AuctionsTab {
    private Tab auctionsTab;
    private ListView<HBox> auctionsListView; // Changed to ListView
    private Button viewEndedAuctions;
    private Button viewActiveAuctions;
    private Button viewUserAuctions;

    public AuctionsTab() {
        auctionsTab = new Tab("Auctions");
        
        auctionsListView = new ListView<>(); // Initialize ListView
        
        // Added support for viewing active auctions, as well as user support
        viewUserAuctions = new Button("View My Auctions");
        viewActiveAuctions = new Button("View Active Auctions");
        viewEndedAuctions = new Button("View Ended Auctions");
        
        VBox auctionsContent = new VBox(viewUserAuctions, viewActiveAuctions, viewEndedAuctions, auctionsListView);
        auctionsTab.setContent(auctionsContent);

        // Event handler for viewing user-created auctions
        viewUserAuctions.setOnAction(event -> {
            auctionsListView.getItems().clear();  // Clear the list

            // Sample data for user auctions
            ActiveAuction auction1 = new ActiveAuction("MacBook Pro - ", "3 Hours\n", 349.68);
            EndedAuction auction2 = new EndedAuction("Handmade Quilt - ", "--------\n", 150.00);

            // Add each auction's display (HBox) to the ListView
            auctionsListView.getItems().addAll(
                auction1.getAuctionDisplay(),
                auction2.getAuctionDisplay()
            );
        });

        // Event handler for viewing active auctions
        viewActiveAuctions.setOnAction(event -> {
            auctionsListView.getItems().clear();  // Clear the list

            // Sample data for active auctions
            ActiveAuction auction1 = new ActiveAuction("Modern Art Sculpture - ", "02:37:28 (HH:MM:SS)\n", 200.00);
            ActiveAuction auction2 = new ActiveAuction("Rare Book - ", "37 Minutes\n", 60.00);

            // Add each auction's display (HBox) to the ListView
            auctionsListView.getItems().addAll(
                auction1.getAuctionDisplay(),
                auction2.getAuctionDisplay()
            );
        });
        
        // Event handler for viewing ended auctions
        viewEndedAuctions.setOnAction(event -> {
            auctionsListView.getItems().clear();  // Clear the list

            // Sample data for ended auctions
            EndedAuction auction1 = new EndedAuction("Vintage Clock", "2024-10-05, 10:00", 85.50);
            EndedAuction auction2 = new EndedAuction("Antique Vase", "2024-10-01, 15:34", 150.00);

            // Add each auction's display (HBox) to the ListView
            auctionsListView.getItems().addAll(
                auction1.getAuctionDisplay(),
                auction2.getAuctionDisplay()
            );
        });
    }

    public Tab getAuctionsTab() {
        return auctionsTab;
    }
}