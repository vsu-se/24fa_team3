package com.example.demo.utils;

import com.example.demo.controllers.AuctionTabController;
import com.example.demo.controllers.HomeTabController;
import com.example.demo.models.Auction;
import com.example.demo.models.Bid;
import com.example.demo.models.Category;
import com.example.demo.models.Item;
import com.example.demo.views.AuctionsTab;
import com.example.demo.views.HomeTab;
import javafx.scene.control.SingleSelectionModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AuctionManager {

    private static AuctionManager instance = new AuctionManager();
    private List<Auction> allAuctions = new ArrayList<>();

    private AuctionManager() {
    }


    public static AuctionManager getInstance() {
        return instance;
    }

    public List<Auction> getAllAuctions() {
        return allAuctions;
    }


    /**
     * This method returns a filtered list of auctions based on the given parameters.
     *
     * If a parameter is null, it is not used to filter the list.
     * i.e., if all filters are null, the method returns all auctions.
     * 
     * @param category category to filter by
     * @param ended ended status to filter by
     * @param ownedByUser ownedByUser status to filter by
     * @return List of Auctions that match the given parameters
     */
    public List<Auction> getFilteredAuctions(Category category, Boolean ended, Boolean ownedByUser) {
        List<Auction> filteredAuctions = new ArrayList<>();
    
        if (category == null && ended == null && ownedByUser == null) {
            return allAuctions;
        }

        for (Auction auction : allAuctions) {
            boolean matchesCategory = false;
            if (category != null) {
                matchesCategory = auction.getCategory() != null && auction.getCategory().equals(category);
            } else {
                matchesCategory = true;
            }

            boolean matchesEnded = false;
            if (ended != null) {
                matchesEnded = auction.isEnded() == ended;
            } else {
                matchesEnded = true;
            }
    
            boolean matchesOwnedByUser = false;
            if (ownedByUser != null) {
                matchesOwnedByUser = auction.isOwnedByUser() == ownedByUser;
            } else {
                matchesOwnedByUser = true;
            }
    
            if (matchesCategory && matchesEnded && matchesOwnedByUser) {
                filteredAuctions.add(auction);
            }
        }
    
        return filteredAuctions;
    }
    


    public List<Auction> getUserAuctions() {
        List<Auction> userAuctions = new ArrayList<>();

        for (Auction auction : allAuctions) {
            if (auction.isOwnedByUser()) {
                userAuctions.add(auction);
            }
        }

        return userAuctions;
    }

    public void addAuction(Auction auction) {
        allAuctions.add(auction);
    }

    public boolean removeAuction(Auction auction) {
        return allAuctions.remove(auction);
    }

    public boolean clearAuctions() {
        allAuctions.clear();
        return allAuctions.isEmpty();
    }

    public List<Auction> getActiveAuctions() {
        List<Auction> activeAuctions = new ArrayList<>();
        for (Auction auction : allAuctions) {
            if (!auction.isEnded()) {
                activeAuctions.add(auction);
            }
        }
        return activeAuctions;
    }

    public List<Auction> getEndedAuctions() {
        List<Auction> endedAuctions = new ArrayList<>();
        for (Auction auction : allAuctions) {
            if (auction.isEnded()) {
                endedAuctions.add(auction);
            }
        }
        return endedAuctions;
    }
}