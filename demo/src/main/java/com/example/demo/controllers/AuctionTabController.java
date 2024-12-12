package com.example.demo.controllers;

import com.example.demo.models.Auction;
import com.example.demo.models.Bid;
import com.example.demo.utils.AuctionManager;
import com.example.demo.utils.TimeManager;
import com.example.demo.views.AuctionsTab;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class AuctionTabController {

    private static AuctionTabController instance = new AuctionTabController();
    private AuctionsTab auctionTab;

    public AuctionTabController() {
        // Initialize currentUser. This should be set based on logged-in user details.
    }

    public static AuctionTabController getInstance() {
        return instance;
    }

    public void setAuctionTab(AuctionsTab auctionTab) {
        this.auctionTab = auctionTab;
    }

    private boolean isAdmin() {
        return SettingsConfig.getInstance().getUserType() == "Admin";  
    }

    private boolean isBuyer() {
        return SettingsConfig.getInstance().getUserType() == "Buyer";
    }

    private boolean isAuctionOwner(Auction auction) {
        return auction.isOwnedByUser();
    }

    public String getFormattedTimeElapsed(Auction a) {
        return TimeManager.getInstance().getFormattedTimeElapsed(a.getEndDate().getTime() / 1000 - TimeManager.getInstance().now().atZone(ZoneId.systemDefault())
                                                .toInstant()
                                                .getEpochSecond());
    }

    public HBox createActiveAuctionDisplay(Auction a) {
        HBox auctionDisplay = new HBox(10);

        Text itemText = new Text(a.getItemName());
        Text timeLimit;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy HH:mm");
        if (a.isEnded()) {
            timeLimit = new Text("\nAuction ended on: " + a.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().format(formatter));
        }
        else {
            timeLimit = new Text("\nTime until auction is over: " + getFormattedTimeElapsed(a));
        }
        Text bidText;
        if (a.getBidHistory().size() == 0) {
            bidText = new Text(String.format("Starting bid is $%.2f ", a.getCurrentBid().getAmount()));
            if (a.isEnded()) {
                bidText = new Text(String.format("Ended. No bid history.", a.getCurrentBid().getAmount()));
            }
        }
        else {
            bidText = new Text(String.format("Highest Bid is $%.2f ", a.getCurrentBid().getAmount()));
        }

        HBox.setHgrow(itemText, Priority.ALWAYS);

        auctionDisplay.getChildren().addAll(itemText, timeLimit, bidText);

        if (isAdmin() || isAuctionOwner(a) && !isBuyer()) {  // Check if user is admin or auction owner
            MenuButton userActions = new MenuButton("Actions");
            MenuItem deleteItem = new MenuItem("Delete Auction");
            MenuItem endAuctionItem = new MenuItem("End Auction");

            // Delete auction if admin or auction owner
            deleteItem.setOnAction(event -> {
                AuctionsTab.getInstance().updateCategoryList();
                AuctionManager.getInstance().removeAuction(a);
                if (auctionTab != null) {
                    auctionTab.getAuctionsListView().getItems().remove(auctionDisplay);
                }
            });

            // User ends an auction they own
            endAuctionItem.setOnAction(event -> {
                a.endAuction();
                auctionDisplay.getChildren().clear();
                userActions.getItems().addAll(deleteItem);
                auctionDisplay.getChildren().clear();

                Text newItemText = new Text(a.getItemName());
                Text newTimeLimit;
                if (a.isEnded()) {
                    newTimeLimit = new Text("\nAuction ended on: " + a.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().format(formatter));
                }
                else {
                    newTimeLimit = new Text("\nTime until auction is over: " + getFormattedTimeElapsed(a));
                }
                Text newBidText;
                if (a.getBidHistory().size() == 0) {
                    newBidText = new Text("Ended. No bid history.");
                }
                else {
                    newBidText = new Text(String.format("Ended. Highest Bid: $%.2f", a.getCurrentBid().getAmount()));
                }

                HBox.setHgrow(newItemText, Priority.ALWAYS);

                auctionDisplay.getChildren().addAll(newItemText, newTimeLimit, newBidText);
                MenuButton newUserActions = new MenuButton("Actions");
                MenuItem newDeleteItem = new MenuItem("Delete Auction");
                newUserActions.getItems().addAll(newDeleteItem);
                auctionDisplay.getChildren().add(newUserActions);
            });

            if (a.isEnded()) {
                userActions.getItems().addAll(deleteItem);
            } else {
                userActions.getItems().addAll(deleteItem, endAuctionItem);
            }
            auctionDisplay.getChildren().add(userActions);
        }
        else {
            // Only allow bidding if the user is an admin or buyer
            if (isAdmin() || isBuyer()) {
                MenuButton userActions = new MenuButton("Actions");
                MenuItem bidOnAuctionItem = new MenuItem("Place Bid");

                bidOnAuctionItem.setOnAction(actionEvent -> {
                    // Create a new window for bidding
                    Stage newWindow = new Stage();
                    newWindow.setTitle("Place Your Bid");

                    VBox vbox = new VBox(10);
                    vbox.setPadding(new Insets(20));

                    Label label = new Label("Enter your bid:");
                    TextField bidField = new TextField();
                    bidField.setPromptText("Your bid");

                    Button submitBidButton = new Button("Place Bid");

                    vbox.getChildren().addAll(label, bidField, submitBidButton);

                    Scene newWindowScene = new Scene(vbox, 300, 200);
                    newWindow.setScene(newWindowScene);

                    newWindow.show();

                    submitBidButton.setOnAction(submitEvent -> {
                        String newBidText = bidField.getText();
                        Bid userBid;

                        try {
                            userBid = new Bid(Double.parseDouble(newBidText));

                            Bid currentBid = a.getCurrentBid();

                            if (userBid.getAmount() > currentBid.getAmount()) {
                                a.setBid(userBid);
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Bid Placed");
                                alert.setHeaderText("Your bid was placed successfully!");
                                alert.setContentText(String.format("You placed a bid of $%.2f", userBid.getAmount()));
                                alert.showAndWait();
                            } else {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Bid Too Low");
                                alert.setHeaderText("Your bid is too low");
                                alert.setContentText("The current bid is " + currentBid.getAmount() + ". Please enter a higher bid.");
                                alert.showAndWait();
                            }
                        } catch (NumberFormatException e) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Invalid Bid");
                            alert.setHeaderText("Please enter a valid bid");
                            alert.setContentText("The bid you entered is not valid. Please enter a numeric value.");
                            alert.showAndWait();
                        }
                    });
                });

                userActions.getItems().add(bidOnAuctionItem);
                auctionDisplay.getChildren().add(userActions);
            }
        }

        return auctionDisplay;
    }

    public HBox createEndedAuctionDisplay(Auction a) {
        HBox auctionDisplay = new HBox(10);

        Text itemText = new Text(a.getItemName());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy HH:mm");
        Text endDateText = new Text("\nAuction ended on: " + a.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().format(formatter));

        Text bidHistoryText = new Text(a.generateBidHistoryReport());

        HBox.setHgrow(itemText, Priority.ALWAYS);

        auctionDisplay.getChildren().addAll(itemText, endDateText, bidHistoryText);

        return auctionDisplay;
    }
}
