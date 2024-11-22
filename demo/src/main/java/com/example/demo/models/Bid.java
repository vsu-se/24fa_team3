package com.example.demo.models;

import java.io.Serializable;
import java.util.Date;

public class Bid implements Serializable {
    private static final long serialVersionUID = 1L;

    private double amount;
    private Date dateTime;
    private String userName;

    public Bid(double amount, Date dateTime, String userName) {
        this.amount = amount;
        this.dateTime = dateTime;
        this.userName = userName;
    }

    public Bid(double amount) {
        this.amount = amount;
        this.dateTime = new Date();
        this.userName = "Auction Owner";
    }

    public double getAmount() {
        return amount;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public String getUserName() {
        return userName;
    }
}
