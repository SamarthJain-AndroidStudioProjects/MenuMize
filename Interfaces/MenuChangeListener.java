package com.smartappsusa.menu.Interfaces;

import com.smartappsusa.menu.Objects.Item;

public interface MenuChangeListener {
    void onItemDisabled(Item item);
    void onItemEnabled(Item item);
}
