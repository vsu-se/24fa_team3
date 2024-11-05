package com.example.demo;

import java.io.Serializable;
import java.util.Date;

public class Item implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private double price;
    private double sellerCommission;
    private double shippingCost;
    private Date soldDate;

    public Item(String name, double price, double sellerCommission, double shippingCost, Date soldDate) {
        this.name = name;
        this.price = price;
        this.sellerCommission = sellerCommission;
        this.shippingCost = shippingCost;
        this.soldDate = soldDate;
    }

    public Item(String name, double price, double sellerCommission, double shippingCost) {
        this.name = name;
        this.price = price;
        this.sellerCommission = sellerCommission;
        this.shippingCost = shippingCost;
        this.soldDate = null;
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

    public Date getSoldDate() {
        return soldDate;
    }
}
