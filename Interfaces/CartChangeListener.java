package com.smartappsusa.menu.Interfaces;

import com.smartappsusa.menu.Objects.Item;

public interface CartChangeListener {
    void onItemAdded(Item item);
    void onItemRemoved(Item item);
}
