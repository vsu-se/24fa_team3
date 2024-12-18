package com.example.demo.models;

import com.example.demo.controllers.AuctionTabController;
import com.example.demo.views.AuctionsTab;

import java.util.Date;
import java.util.List;

public class Auction {
    private String itemName;
    private Date timer;
    private Date endDate;
    private Bid currentBid;
    private Bid startingBid;
    private List<Bid> bidHistory;
    private boolean isOwnedByUser;
    private boolean ended;

    public Auction(String itemName, Date timer, boolean isOwnedByUser, boolean ended, Bid startingBid) {
        this.itemName = itemName;
        this.timer = timer;
        this.isOwnedByUser = isOwnedByUser;
        this.ended = ended;
        this.startingBid = startingBid;
    }

    public String getItemName() {
        return itemName;
    }

    public Date getTimer() {
        return timer;
    }

    public Bid getStartingBid() {
        return startingBid;
    }

    public Bid getCurrentBid() {
        return currentBid;
    }

    public boolean isOwnedByUser() {
        return isOwnedByUser;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void deleteAuction() {
        // Logic to delete the auction
        System.out.println("Auction " + itemName + " has been deleted.");
    }

    public void endAuction() {
        // Logic to end the auction
        this.ended = true;
        System.out.println("Auction " + itemName + " has ended.");
    }

    public void setBid(Bid bid) {
        this.currentBid = bid;
        this.bidHistory.add(bid);
    }

    public List<Bid> getBidHistory() {
        return bidHistory;
    }

    public Date getEndDate() {
        return endDate;
    } 

    public String generateBidHistoryReport() {
        StringBuilder report = new StringBuilder();
        report.append("Bid History for Auction: ").append(itemName).append("\n");
        for (Bid bid : bidHistory) {
            report.append(bid.toString()).append("\n");
        }
        return report.toString();
    }
}
