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

    public AuctionsTab() {
        auctionsTab = new Tab("Auctions");
        
        auctionsListView = new ListView<>(); // Initialize ListView
        
        viewEndedAuctions = new Button("View Ended Auctions");
        
        VBox auctionsContent = new VBox(viewEndedAuctions, auctionsListView);
        auctionsTab.setContent(auctionsContent);

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
