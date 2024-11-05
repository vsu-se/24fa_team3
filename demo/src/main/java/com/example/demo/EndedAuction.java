package com.example.demo;

import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.layout.Priority;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

// Class used to easily create Ended Auction listings in the "View Ended Auctions" tab
public class EndedAuction implements Serializable {
    private static final long serialVersionUID = 1L;

    private String itemName;
    private String endDate;
    private double finalPrice;
    private transient HBox auctionDisplay;
    private List<Bid> bids;

    public EndedAuction(String itemName, String endDate, double finalPrice) {
        this.itemName = itemName;
        this.endDate = endDate;
        this.finalPrice = finalPrice;
        this.bids = new ArrayList<>();

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

    public void addBid(Bid bid) {
        bids.add(bid);
    }

    public List<Bid> getBids() {
        return bids;
    }

    public List<Bid> getSortedBids() {
        List<Bid> sortedBids = new ArrayList<>(bids);
        sortedBids.sort(Comparator.comparing(Bid::getAmount).reversed());
        return sortedBids;
    }

    public String generateBidHistoryReport() {
        StringBuilder report = new StringBuilder();
        report.append("Bid History Report:\n");
        report.append("--------------------------------------------------\n");

        List<Bid> sortedBids = getSortedBids();
        for (Bid bid : sortedBids) {
            report.append(String.format("Bid: %.2f, Date/Time: %s, User Name: %s\n",
                    bid.getAmount(), bid.getDateTime().toString(), bid.getUserName()));
        }

        report.append("--------------------------------------------------\n");
        return report.toString();
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        // Reinitialize transient fields
        auctionDisplay = new HBox();
        Text itemText = new Text(itemName);
        Text dateText = new Text("\n Ended at " + endDate);
        Text priceText = new Text(String.format("Sold for $%.2f", finalPrice));
        HBox.setHgrow(itemText, Priority.ALWAYS);
        auctionDisplay.getChildren().addAll(itemText, dateText, priceText);
    }
}