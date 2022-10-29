package com.smartappsusa.menu.Objects;

import androidx.annotation.Nullable;

public class Location {
    private String streetAddress;
    private String cityTown;
    private String state;

    public Location(){}
    public Location(String streetAddress, String cityTown, String state) {
        setStreetAddress(streetAddress);
        setCityTown(cityTown);
        setState(state);
    }

    public String getStreetAddress() { return streetAddress; }
    public void setStreetAddress(String streetAddress) { this.streetAddress = streetAddress; }
    public String getCityTown() { return cityTown; }
    public void setCityTown(String cityTown) { this.cityTown = cityTown; }
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj instanceof Location) {
            Location location = (Location) obj;
            return this.getStreetAddress().equals(location.getStreetAddress());
        }
        return false;
    }
}
