package com.ace.san.db;

import com.google.firebase.database.IgnoreExtraProperties;

public class Item {
    private String name;
    private String category;
    private String status;
    private String place;
    private String quantity;

    public Item(String name, String status, String place, String category, String quantity){
        this.name = name;
        this.category = category;
        this.status = status;
        this.place = place;
        this.quantity = quantity;

    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getStatus() {
        return status;
    }

    public String getPlace() {
        return place;
    }

    public String getQuantity() {
        return quantity; }
}
