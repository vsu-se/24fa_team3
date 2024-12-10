package com.example.demo.controllers;

import com.example.demo.models.Auction;
import com.example.demo.models.Bid;
import com.example.demo.models.Category;
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
    }

    public static AuctionTabController getInstance() {
        return instance;
    }

    public void setAuctionTab(AuctionsTab auctionTab) {
        this.auctionTab = auctionTab;
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

        if (a.isOwnedByUser()) {
            MenuButton userActions = new MenuButton("Actions");
            MenuItem deleteItem = new MenuItem("Delete Auction");
            MenuItem endAuctionItem = new MenuItem("End Auction");

            deleteItem.setOnAction(event -> {
                AuctionsTab.getInstance().updateCategoryList();
                //a.deleteAuction();
               // auctionTab.getAuctionsListView().getItems().remove(auctionDisplay);
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
            }
            else {
                userActions.getItems().addAll(deleteItem, endAuctionItem);
            }
            auctionDisplay.getChildren().add(userActions);
        }
        else {
            MenuButton userActions = new MenuButton("Actions");
            MenuItem deleteItem = new MenuItem("Delete Auction");
            MenuItem endAuctionItem = new MenuItem("End Auction");
            MenuItem bidOnAuctionItem = new MenuItem("Place Bid");

            deleteItem.setOnAction(event -> {
                a.deleteAuction();
                if (auctionTab != null) {
                    auctionTab.getAuctionsListView().getItems().remove(auctionDisplay);
                }            
            });

            endAuctionItem.setOnAction(event -> {
                a.endAuction();
                if (auctionTab != null) {
                    auctionTab.getAuctionsListView().getItems().remove(auctionDisplay);
                }
            });

            bidOnAuctionItem.setOnAction(actionEvent -> {
                // Create a new window for bidding
                Stage newWindow = new Stage();
                newWindow.setTitle("Place Your Bid");

                // Create a layout for the new window (StackPane, VBox, or HBox)
                VBox vbox = new VBox(10);
                vbox.setPadding(new Insets(20));

                // Create a label and text field for the user to enter their bid
                Label label = new Label("Enter your bid:");
                TextField bidField = new TextField();
                bidField.setPromptText("Your bid");

                // Add a button to confirm the bid
                Button submitBidButton = new Button("Place Bid");

                // Add all elements to the layout
                vbox.getChildren().addAll(label, bidField, submitBidButton);

                // Create the scene and set it for the new window
                Scene newWindowScene = new Scene(vbox, 300, 200);
                newWindow.setScene(newWindowScene);

                // Show the new window
                newWindow.show();

                // Set the action for submitting the bid
                submitBidButton.setOnAction(submitEvent -> {
                    // Get the user's bid from the text field
                    String newBidText = bidField.getText();
                    Bid userBid;

                    try {
                        // Try to parse the bid to a double
                        userBid = new Bid(Double.parseDouble(newBidText));

                        // Check if the bid is higher than the current bid
                        Bid currentBid = a.getCurrentBid(); // Assuming the auction has a getCurrentBid() method

                        if (userBid.getAmount() > currentBid.getAmount()) {
                            // If the bid is higher, set the new bid
                            a.setBid(userBid);
                            // Optionally, display a success message
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Bid Placed");
                            alert.setHeaderText("Your bid was placed successfully!");
                            alert.setContentText(String.format("You placed a bid of $%.2f", userBid.getAmount()));
                            alert.showAndWait();
                        } else {
                            // If the bid is not higher, show an error
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Bid Too Low");
                            alert.setHeaderText("Your bid is too low");
                            alert.setContentText("The current bid is " + currentBid.getAmount() + ". Please enter a higher bid.");
                            alert.showAndWait();
                        }
                    } catch (NumberFormatException e) {
                        // Handle the case where the input is not a valid number
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Invalid Bid");
                        alert.setHeaderText("Please enter a valid bid");
                        alert.setContentText("The bid you entered is not valid. Please enter a numeric value.");
                        alert.showAndWait();
                    }
                });
            });


            userActions.getItems().addAll(deleteItem, endAuctionItem, bidOnAuctionItem);
            auctionDisplay.getChildren().add(userActions);
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
