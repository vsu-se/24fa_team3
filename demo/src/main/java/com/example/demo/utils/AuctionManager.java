package com.example.demo.utils;

import com.example.demo.controllers.HomeTabController;
import com.example.demo.models.Auction;
import com.example.demo.models.Bid;
import com.example.demo.models.Category;
import com.example.demo.models.Item;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AuctionManager {

    private static AuctionManager instance = new AuctionManager();
    private List<Auction> allAuctions = new ArrayList<>();
    private Category selectedCategory;

    public Category getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(Category selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    private AuctionManager() {
        makeMockAuctions();
    }

    public List<Auction> getAllAuctions() {
        return allAuctions;
    }

    public static AuctionManager getInstance() {
        return instance;
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

    //Easton's Changes
    public void addAuction(Auction auction) {
        allAuctions.add(auction);
    }

    //Easton's Changes
    public boolean removeAuction(Auction auction) {
        return allAuctions.remove(auction);
    }

    // Easton's Changes
    public boolean clearAuctions() {
        allAuctions.clear();
        return allAuctions.isEmpty();
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

/*
    public List<Auction> getUserAuctions() {
        // mock list
        List<Auction> userAuctions = new ArrayList<>();

        ArrayList<Category> lists = HomeTabController.getInstance().getCategoryList();
        ArrayList<Item> items = new ArrayList<>();

        for (Category category : lists) {
            List<Item> categoryItems = category.getNewItem();
            items.addAll(categoryItems);
        }

        for (Item item : items) {
            userAuctions.add(new Auction(item.getName(), new Date(), true, false, new Bid(100)));
        }


        //userAuctions.add(new Auction("User Auction 1", new Date(), true, false, new Bid(100)));
       // userAuctions.add(new Auction("User Auction 2", new Date(), true, false, new Bid(200)));
        return userAuctions;
    }

 */

    //Easton's Changes
    public List<Auction> getActiveAuctions() {
        List<Auction> activeAuctions = new ArrayList<>();
        for (Auction auction : allAuctions) {
            if (!auction.isEnded()) {
                activeAuctions.add(auction);
            }
        }
        return activeAuctions;
    }


    /*
    public List<Auction> getActiveAuctions() {
        // mock list
        List<Auction> activeAuctions = new ArrayList<>();
        activeAuctions.add(new Auction("Active Auction 1", new Date(), false, false, new Bid(150)));
        activeAuctions.add(new Auction("Active Auction 2", new Date(), false, false, new Bid(250)));
        return activeAuctions;
    }
     */

    public List<Auction> getEndedAuctions() {
        List<Auction> endedAuctions = new ArrayList<>();
        for (Auction auction : allAuctions) {
            if (auction.isEnded()) {
                endedAuctions.add(auction);
            }
        }
        return endedAuctions;
    }
    /*
    public List<Auction> getEndedAuctions() {
        // mock list
        List<Auction> endedAuctions = new ArrayList<>();
        endedAuctions.add(new Auction("Ended Auction 1", new Date(), false, true, new Bid(300)));
        endedAuctions.add(new Auction("Ended Auction 2", new Date(), false, true, new Bid(400)));
        return endedAuctions;
    }
     */

    // Method to make auctions for testing purposes
    public void makeMockAuctions() {
        Category category = new Category("Electronics");
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.DECEMBER, 24, 12, 0, 0);
        Date timerGoing = calendar.getTime();
        calendar.set(2024, Calendar.DECEMBER, 8, 12, 0, 0);
        Date timerEnded = calendar.getTime();
        addAuction(new Auction("Phone", timerGoing, true, false, 5, category));  // Owned by user, ended, in category
        addAuction(new Auction("Computer", timerEnded, false, true, 5, category));  // Ended, not owned by user, in category
        addAuction(new Auction("Airpods", timerGoing, false, false, 5, category)); // Not ended, not owned by user, in category
        addAuction(new Auction("Projector", timerEnded, true, true, 5, category));   // Owned by user, ended, in category
        addAuction(new Auction("Scarf", timerGoing, true, false, 5, null));      // Owned by user, ended, not in category
        addAuction(new Auction("Car", timerEnded, false, true, 5, null));      // Ended, not owned by user, not in category
        addAuction(new Auction("Table", timerGoing, false, false, 5, null));     // Not ended, not owned by user, not in category
        addAuction(new Auction("Water", timerEnded, true, true, 5, null));       // Owned by user, ended, not in category

    }
}