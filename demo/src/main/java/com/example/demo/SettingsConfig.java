package com.example.demo;

public class SettingsConfig {

    private static SettingsConfig instance = new SettingsConfig();

    private SettingsConfig() {}

    public static SettingsConfig getInstance() {
        return instance;
    }

    private double sellerCommission = 0;
    private double buyerPremium = 0;

    public double getSellerCommission() {
        return sellerCommission;
    }

    public void setSellerCommission(double commission) {
        this.sellerCommission = commission;
    }

    public double getBuyerPremium() {
        return buyerPremium;
    }

    public void setBuyerPremium(double buyerPremium) {
        this.buyerPremium = buyerPremium;
    }
}