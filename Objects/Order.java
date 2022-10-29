package com.smartappsusa.menu.Objects;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Order {
    private ArrayList<Item> items;
    private int code;
    private String userID;
    private Location location;

    public Order(){}
    public Order(ArrayList<Item> items, Location location, String userID, int code) {
        this.items = items;
        this.userID = userID;
        this.code = code;
        this.location = location;
    }

    public ArrayList<Item> getItems() { return items; }
    public void setItems(ArrayList<Item> items) { this.items = items; }
    public Location getLocation() { return location; }
    public void setLocation(Location location){ this.location = location; }
    public String getUserID() { return userID; }
    public void setUserID(String userID) { this.userID = userID; }
    public int getCode() { return code; }
    public void setCode(int code) { this.code = code; }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj instanceof Order) {
            Order order = (Order) obj;
            return this.getCode() == order.getCode();
        }
        return false;
    }
}
