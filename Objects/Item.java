package com.smartappsusa.menu.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Item {
    private String itemName;
    private String itemDescription;
    private String itemCost;
    private int quantity;

    public Item(){}
    public Item(String itemName, String itemDescription, String itemCost, int quantity) {
        setItemName(itemName);
        setItemDescription(itemDescription);
        setItemCost(itemCost);
        setQuantity(quantity);
    }

    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }
    public String getItemDescription() { return itemDescription; }
    public void setItemDescription(String itemDescription) { this.itemDescription = itemDescription; }
    public String getItemCost() { return itemCost; }
    public void setItemCost(String itemCost) { this.itemCost = itemCost; }
    public void setQuantity(int quantity){ this.quantity = quantity; }
    public int getQuantity(){ return quantity; }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj instanceof Item) {
            Item item = (Item) obj;
            return this.getItemName().equals(item.getItemName());
        }
        return false;
    }

    @Override @NonNull public String toString(){
        return this.getItemName() + "C: " + this.getItemCost() + this.getItemDescription() + "Q: " + this.getQuantity();
    }
}