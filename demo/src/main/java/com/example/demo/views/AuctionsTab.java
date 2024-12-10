package com.example.demo.views;

import com.example.demo.controllers.AuctionTabController;
import com.example.demo.controllers.HomeTabController;
import com.example.demo.models.Auction;
import com.example.demo.models.Category;
import com.example.demo.models.Item;
import com.example.demo.utils.AuctionManager;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class AuctionsTab {
    private static AuctionsTab instance = new AuctionsTab();
    private Tab auctionsTab;
    private ListView<HBox> auctionsListView;
    private Button viewEndedAuctions;
    private Button viewActiveAuctions;
    private Button viewUserAuctions;
    private ComboBox<String> viewCategoryOptions;
    private Button getCategorySelected;
    private String selectedAuctionsList;

    public AuctionsTab() {
        auctionsTab = new Tab("Auctions");

        auctionsListView = new ListView<>();

        viewUserAuctions = new Button("View My Auctions");
        viewActiveAuctions = new Button("View Active Auctions");
        viewEndedAuctions = new Button("View Ended Auctions");
        viewCategoryOptions = new ComboBox<>();
        viewCategoryOptions.setPromptText("Find by Category");
        getCategorySelected = new Button("Search");

        HBox buttonBox = new HBox(10, viewUserAuctions, viewActiveAuctions, viewEndedAuctions, viewCategoryOptions, getCategorySelected);
        buttonBox.setPadding(new Insets(10, 10, 10, 10));

        VBox auctionsContent = new VBox(buttonBox, auctionsListView);
        auctionsTab.setContent(auctionsContent);

        viewUserAuctions.setOnAction(event -> showUserAuctions());
        viewActiveAuctions.setOnAction(event -> showActiveAuctions());
        viewEndedAuctions.setOnAction(event -> showEndedAuctions());

        viewCategoryOptions.setOnMouseClicked(event -> {
            updateCategoryList();
        });


        getCategorySelected.setOnAction(event -> {
            selectCategory();
            showSortedAuctions();
        });
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

    public void updateCategoryList() {
        List<Category> categoryList = HomeTabController.getInstance().getCategoryList();
        List<String> categoryStrings = FXCollections.observableArrayList();

        for (Category category : categoryList) {
            if(!categoryStrings.contains(category.getName())) {
                categoryStrings.add(category.getName());
            }
        }
            viewCategoryOptions.getItems().clear();
            viewCategoryOptions.getItems().addAll(categoryStrings);
    }



    private void showUserAuctions() {
        List<Auction> userAuctions = fetchUserAuctions();
        auctionsListView.getItems().clear();
        for (Auction auction : userAuctions) {
            HBox auctionDisplay = AuctionTabController.getInstance().createActiveAuctionDisplay(auction);
            auctionsListView.getItems().add(auctionDisplay);
        }
        selectedAuctionsList = "User Auctions";
    }

    public void selectCategory() {
        String selected = viewCategoryOptions.getValue();

        if (selected == null) {
            return;
        }

        ArrayList<Category> categories = HomeTabController.getInstance().getCategoryList();
        ArrayList<Item> filteredItems = new ArrayList<>();
        //filteredItems.clear();

        for (Category category : categories) {
            if (selected.equals(category.getName())) {
                AuctionManager.getInstance().setSelectedCategory(category);
                filteredItems.addAll(category.getNewItem());
            }
        }

    }

    private void showActiveAuctions() {
        List<Auction> activeAuctions = fetchActiveAuctions();
        auctionsListView.getItems().clear();
        for (Auction auction : activeAuctions) {
            HBox auctionDisplay = AuctionTabController.getInstance().createActiveAuctionDisplay(auction);
            auctionsListView.getItems().add(auctionDisplay);
        }

        selectedAuctionsList = "Active Auctions";

    }

    private void showEndedAuctions() {
        List<Auction> endedAuctions = fetchEndedAuctions();
        auctionsListView.getItems().clear();
        for (Auction auction : endedAuctions) {
            HBox auctionDisplay = AuctionTabController.getInstance().createEndedAuctionDisplay(auction);
            auctionsListView.getItems().add(auctionDisplay);
        }

        selectedAuctionsList = "Ended Auctions";
    }

    private void showSortedAuctions() {
        Boolean ended = null;
        Boolean ownedByUser = null;
        if (AuctionManager.getInstance().getSelectedCategory() == null) {
            return;
        }
        switch (selectedAuctionsList) {
            case "User Auctions":
                ownedByUser = true;
                break;
            case "Active Auctions":
                ended = false;
                break;
            case "Ended Auctions":
                ended = true;
                break;
            default:
                break;
        }
        List<Auction> sortedAuctions = AuctionManager.getInstance().getFilteredAuctions(AuctionManager.getInstance().getSelectedCategory(), ended, ownedByUser);
        auctionsListView.getItems().clear();
        if (ended != null && ended == true) {
            for (Auction auction : sortedAuctions) {
                HBox auctionDisplay = AuctionTabController.getInstance().createEndedAuctionDisplay(auction);
                auctionsListView.getItems().add(auctionDisplay);
            }
        }
        else {
            for (Auction auction : sortedAuctions) {
                HBox auctionDisplay = AuctionTabController.getInstance().createActiveAuctionDisplay(auction);
                auctionsListView.getItems().add(auctionDisplay);
            }
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
