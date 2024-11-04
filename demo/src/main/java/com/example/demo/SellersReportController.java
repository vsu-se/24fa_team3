package com.example.demo;

import java.util.ArrayList;
import java.util.List;

class Item {
    private String name;
    private double price;
    private double sellerCommission;
    private double shippingCost;

    public Item(String name, double price, double sellerCommission, double shippingCost) {
        this.name = name;
        this.price = price;
        this.sellerCommission = sellerCommission;
        this.shippingCost = shippingCost;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public double getSellerCommission() {
        return sellerCommission;
    }

    public double getShippingCost() {
        return shippingCost;
    }
}

public class SellersReportController {
    private List<Item> items = new ArrayList<>();

    public void addItem(String name, double price, double sellerCommission, double shippingCost) {
        items.add(new Item(name, price, sellerCommission, shippingCost));
    }

    public String generateReport() {
        double totalSellerCommissions = 0;
        double totalProfits = 0;
        double totalWinningBids = 0;
        double totalShippingCosts = 0;

        StringBuilder report = new StringBuilder();
        report.append("Seller's Report:\n");
        report.append("--------------------------------------------------\n");
        report.append(String.format("%-20s %-10s %-15s %-15s\n", "Name", "Price", "Seller's Commission", "Shipping Cost"));

        for (Item item : items) {
            report.append(String.format("%-20s $%-9.2f $%-14.2f $%-14.2f\n", item.getName(), item.getPrice(), item.getSellerCommission(), item.getShippingCost()));
            totalWinningBids += item.getPrice();
            totalShippingCosts += item.getShippingCost();
            totalSellerCommissions += item.getSellerCommission();
            totalProfits += (item.getPrice() - item.getSellerCommission());
        }

        report.append("--------------------------------------------------\n");
        report.append(String.format("Total Winning Bids: $%.2f\n", totalWinningBids));
        report.append(String.format("Total Shipping Costs: $%.2f\n", totalShippingCosts));
        report.append(String.format("Total Seller's Commissions: $%.2f\n", totalSellerCommissions));
        report.append(String.format("Total Profits: $%.2f\n", totalProfits));

        return report.toString();
    }

    // main used for testing in terminal
    public static void main(String[] args) {
        SellersReportController report = new SellersReportController();
        report.addItem("Item 1", 100.00, 10.00, 5.00);
        report.addItem("Item 2", 200.00, 20.00, 10.00);
        report.addItem("Item 3", 150.00, 15.00, 7.50);

        System.out.println(report.generateReport());
    }
}
