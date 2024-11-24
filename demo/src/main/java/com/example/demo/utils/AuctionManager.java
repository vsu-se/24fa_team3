package com.example.demo.utils;

import com.example.demo.controllers.AuctionTabController;
import com.example.demo.controllers.HomeTabController;
import com.example.demo.models.Auction;
import com.example.demo.models.Bid;
import com.example.demo.models.Category;
import com.example.demo.models.Item;
import com.example.demo.views.HomeTab;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AuctionManager {

    private static AuctionManager instance = new AuctionManager();

    private AuctionManager() {
    }

    public static AuctionManager getInstance() {
        return instance;
    }

    public List<Auction> getUserAuctions() {
        // mock list
        List<Auction> userAuctions = new ArrayList<>();

       // List<Category> categories = HomeTabController.getInstance().categorylist;
       // ArrayList<Item> items = new ArrayList<>();
        //for (Category category : categories) {
        //    List<Item> categoryItems = category.getNewItem();
        //    items.addAll(categoryItems);
       // }

       // for (Item item : items) {
          //  userAuctions.add(new Auction(item.getName(), new Date(), true, false, new Bid(100)));
        //}


        userAuctions.add(new Auction("User Auction 1", new Date(), true, false, new Bid(100)));
       // userAuctions.add(new Auction("User Auction 2", new Date(), true, false, new Bid(200)));
        return userAuctions;
    }

    public List<Auction> getActiveAuctions() {
        // mock list
        List<Auction> activeAuctions = new ArrayList<>();
        activeAuctions.add(new Auction("Active Auction 1", new Date(), false, false, new Bid(150)));
        activeAuctions.add(new Auction("Active Auction 2", new Date(), false, false, new Bid(250)));
        return activeAuctions;
    }

    public List<Auction> getEndedAuctions() {
        // mock list
        List<Auction> endedAuctions = new ArrayList<>();
        endedAuctions.add(new Auction("Ended Auction 1", new Date(), false, true, new Bid(300)));
        endedAuctions.add(new Auction("Ended Auction 2", new Date(), false, true, new Bid(400)));
        return endedAuctions;
    }
}