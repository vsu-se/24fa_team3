package com.example.demo;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BuyersReport implements Serializable {
    private static final long serialVersionUID = 1L;

    private static BuyersReport instance = new BuyersReport();
    private double buyerPremium;
    private List<Item> boughtItems;

    private BuyersReport() {
        this.buyerPremium = SettingsConfig.getInstance().getBuyerPremium();
        this.boughtItems = new ArrayList<>();
    }

    public static BuyersReport getInstance() {
        return instance;
    }

    public void updateBuyerPremium(double buyerPremium) {
        this.buyerPremium = buyerPremium;
    }

    public void addBoughtItem(Item item) {
        boughtItems.add(item);
    }

    public String generateReport() {
        // Sort items in reverse chronological order
        Collections.sort(boughtItems, Comparator.comparing(Item::getSoldDate).reversed());

        double totalAmountBought = 0;
        double totalBuyerPremiumsPaid = 0;
        double totalShippingCostsPaid = 0;

        StringBuilder report = new StringBuilder();
        report.append("Buyer's Report:\n");
        report.append("--------------------------------------------------\n");

        for (Item item : boughtItems) {
            double itemBuyerPremium = item.getPrice() * buyerPremium / 100;
            totalAmountBought += item.getPrice();
            totalBuyerPremiumsPaid += itemBuyerPremium;
            totalShippingCostsPaid += item.getShippingCost();

            report.append(String.format("Name: %s, Price: %.2f, Buyer's Premium: %.2f, Shipping: %.2f\n",
                    item.getName(), item.getPrice(), itemBuyerPremium, item.getShippingCost()));
        }

        report.append("--------------------------------------------------\n");
        report.append(String.format("Total Amount Bought: %.2f\n", totalAmountBought));
        report.append(String.format("Total Buyer's Premiums Paid: %.2f\n", totalBuyerPremiumsPaid));
        report.append(String.format("Total Shipping Costs Paid: %.2f\n", totalShippingCostsPaid));

        return report.toString();
    }

    public void saveToFile(String filePath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(instance);
        }
    }

    public static void loadFromFile(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            instance = (BuyersReport) ois.readObject();
        }
    }
}
