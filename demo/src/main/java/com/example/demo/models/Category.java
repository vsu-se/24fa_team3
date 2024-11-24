package com.example.demo.models;

import java.util.ArrayList;

public class Category {

    private String name;
    ArrayList<Item> newItem;

    public Category(String name) {

        this.name = name;
        this.newItem = new ArrayList<>();
    }

    public void addItem(Item item) {
        newItem.add(item);
    }

    public String getName() {
        return name;
    }

    public ArrayList<Item> getNewItem() {
        return newItem;
    }


}
