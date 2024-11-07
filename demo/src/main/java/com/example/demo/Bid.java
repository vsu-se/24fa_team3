package com.example.demo;

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
