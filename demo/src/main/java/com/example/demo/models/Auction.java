package com.example.demo.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import com.example.demo.utils.TimeManager;

public class Auction {
    private String itemName;
    private Date endDate;
    private Bid currentBid;
    private Category category;
    private Bid startingBid;
    private double startingPrice;
    private List<Bid> bidHistory;
    private boolean isOwnedByUser;
    private boolean ended;

    public Auction(String itemName, Date endDate, boolean isOwnedByUser, boolean ended, double startingPrice, Category category) {
        this.itemName = itemName;
        this.endDate = endDate;
        this.isOwnedByUser = isOwnedByUser;
        this.ended = ended;
        this.startingPrice = startingPrice;
        this.category = category;
    }

    public double getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(double startingPrice) {
        this.startingPrice = startingPrice;
    }

    public String getItemName() {
        return itemName;
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

    public Category getCategory() {
        return category;
    }

    public boolean isEnded() {
        if (endDate != null) {
            return TimeManager.getInstance().now().isAfter(endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        }
        return ended;
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

    // Easton's changes
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Auction auction = (Auction) obj;
         return itemName.equals(auction.itemName) && endDate.equals(auction.endDate);
    }

    public String generateBidHistoryReport() {
        StringBuilder report = new StringBuilder();
        report.append("Bid History for Auction: ").append(itemName).append("\n");
        if (bidHistory != null) {
            for (Bid bid : bidHistory) {
                report.append(bid.toString()).append("\n");
            }
            return report.toString();
        }
        return "No bid history.";
    }
}
