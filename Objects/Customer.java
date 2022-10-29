package com.smartappsusa.menu.Objects;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Customer {
    private String userID;
    private String name;
    private String email;
    private ArrayList<Item> cart;

    public Customer(){}
    public Customer(String userID, String name, String email, ArrayList<Item> cart) {
        setUserID(userID);
        setName(name);
        setEmail(email);
        setCart(cart);
    }

    public String getUserID() { return userID; }
    public void setUserID(String userID) { this.userID = userID; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public ArrayList<Item> getCart(){ return cart; }
    public void setCart(ArrayList<Item> cart){ this.cart = cart; }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj instanceof Customer){
            Customer customer = (Customer) obj;
            return this.getUserID().equals(customer.getUserID());
        }
        return false;
    }
}