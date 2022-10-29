package com.smartappsusa.menu.Interfaces;

import com.smartappsusa.menu.Objects.Location;

public interface LocationChangeListener {
    void onLocationDisabled(Location location);
    void onLocationEnabled(Location location);
}
