package com.example.demo.controllers;

import java.io.*;
import java.time.LocalDateTime;

import com.example.demo.models.BuyersReport;
import com.example.demo.models.SellersReport;

public class SettingsConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    private static SettingsConfig instance = new SettingsConfig();
    private double sellerCommission;
    private double buyerPremium;
    private LocalDateTime currentTime;

    private SettingsConfig() {
        // Initialize default values if necessary
    }

    public LocalDateTime getCurrentTime() {
        return currentTime;
    }

    public static SettingsConfig getInstance() {
        return instance;
    }

    public void setSellerCommission(double sellerCommission) {
        this.sellerCommission = sellerCommission;
        SellersReport.getInstance().updateSellerCommission(sellerCommission);
    }

    public double getSellerCommission() {
        return sellerCommission;
    }

    public void setBuyerPremium(double buyerPremium) {
        this.buyerPremium = buyerPremium;
        BuyersReport.getInstance().updateBuyerPremium(buyerPremium);
    }

    public double getBuyerPremium() {
        return buyerPremium;
    }

    public void saveToFile(String filePath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(instance);
        }
    }

    public static void loadFromFile(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            instance = (SettingsConfig) ois.readObject();
        }
    }









}