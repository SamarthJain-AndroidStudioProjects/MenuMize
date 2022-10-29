package com.smartappsusa.menu.Interfaces;

import com.smartappsusa.menu.Objects.Item;

public interface CostChangeListener {
    void onItemDeleted(Item item);
    void onQuantityChanged(Item oldItem, int newQuantity);
}
