package com.example.demo.views;

import com.example.demo.controllers.AuctionTabController;
import com.example.demo.controllers.HomeTabController;
import com.example.demo.models.Auction;
import com.example.demo.models.Category;
import com.example.demo.utils.AuctionManager;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AuctionsTab {
    private static AuctionsTab instance = new AuctionsTab();
    private Tab auctionsTab;
    private ListView<HBox> auctionsListView;
    private Button viewEndedAuctions;
    private Button viewActiveAuctions;
    private Button viewUserAuctions;
    private ComboBox<String> viewCategoryOptions;

    public AuctionsTab() {
        auctionsTab = new Tab("Auctions");

        auctionsListView = new ListView<>();

        viewUserAuctions = new Button("View My Auctions");
        viewActiveAuctions = new Button("View Active Auctions");
        viewEndedAuctions = new Button("View Ended Auctions");
        viewCategoryOptions = new ComboBox<>();
        viewCategoryOptions.setPromptText("Find by Category");

        HBox buttonBox = new HBox(10, viewUserAuctions, viewActiveAuctions, viewEndedAuctions, viewCategoryOptions);
        buttonBox.setPadding(new Insets(10, 10, 10, 10));

        VBox auctionsContent = new VBox(buttonBox, auctionsListView);
        auctionsTab.setContent(auctionsContent);



        viewUserAuctions.setOnAction(event -> showUserAuctions());
        viewActiveAuctions.setOnAction(event -> showActiveAuctions());
        viewEndedAuctions.setOnAction(event -> showEndedAuctions());


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
/*
    public void updateCategoryList() {
        List<Category> categoryList = HomeTabController.getInstance().getCategoryList();
        System.out.println(categoryList.size());

        List<String> categoryStrings = FXCollections.observableArrayList();

        for (Category category : categoryList) {
            categoryStrings.add(category.getName());
        }

        Platform.runLater(() -> {
            //viewCategoryOptions.setItems(categoryStrings);
            viewCategoryOptions.getItems().addAll(categoryStrings);
        });
    }

 */

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
