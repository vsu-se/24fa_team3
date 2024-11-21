package com.example.demo.models;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.example.demo.controllers.SettingsConfig;

public class SellersReport implements Serializable {
    private static final long serialVersionUID = 1L;

    private static SellersReport instance = new SellersReport();
    private double sellerCommission;
    private List<Item> soldItems;

    private SellersReport() {
        this.sellerCommission = SettingsConfig.getInstance().getSellerCommission();
        this.soldItems = new ArrayList<>();
    }

    public static SellersReport getInstance() {
        return instance;
    }

    public void updateSellerCommission(double sellerCommission) {
        this.sellerCommission = sellerCommission;
    }

    public void addSoldItem(Item item) {
        soldItems.add(item);
    }

    public String generateReport() {
        // Sort items in reverse chronological order
        Collections.sort(soldItems, Comparator.comparing(Item::getSoldDate).reversed());

        double totalWinningBids = 0;
        double totalShippingCosts = 0;
        double totalSellerCommissions = 0;

        StringBuilder report = new StringBuilder();
        report.append("Seller's Report:\n");
        report.append("--------------------------------------------------\n");

        for (Item item : soldItems) {
            double itemSellerCommission = item.getPrice() * sellerCommission / 100;
            totalWinningBids += item.getPrice();
            totalShippingCosts += item.getShippingCost();
            totalSellerCommissions += itemSellerCommission;

            report.append(String.format("Name: %s, Price: %.2f, Seller's Commission: %.2f, Shipping: %.2f\n",
                    item.getName(), item.getPrice(), itemSellerCommission, item.getShippingCost()));
        }

        double totalProfits = totalWinningBids - totalSellerCommissions;

        report.append("--------------------------------------------------\n");
        report.append(String.format("Total Winning Bids: %.2f\n", totalWinningBids));
        report.append(String.format("Total Shipping Costs: %.2f\n", totalShippingCosts));
        report.append(String.format("Total Seller's Commissions: %.2f\n", totalSellerCommissions));
        report.append(String.format("Total Profits: %.2f\n", totalProfits));

        return report.toString();
    }

    public void saveToFile(String filePath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(instance);
        }
    }

    public static void loadFromFile(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            instance = (SellersReport) ois.readObject();
        }
    }
}