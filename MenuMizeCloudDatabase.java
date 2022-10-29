package com.smartappsusa.menu;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.smartappsusa.menu.Objects.Customer;
import com.smartappsusa.menu.Objects.Item;
import com.smartappsusa.menu.Objects.Location;
import com.smartappsusa.menu.Objects.Order;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public interface MenuMizeCloudDatabase {

    default void addCustomer() {
        FirebaseDatabase.getInstance().getReference("Customers").child(Account.userID).setValue(new Customer(Account.userID, Account.name, Account.email, new ArrayList<>()));
    }
    default void getCustomers(Customers customers){
        FirebaseDatabase.getInstance().getReference("Customers").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ArrayList<Customer> customersList = new ArrayList<>();
                    for (DataSnapshot child : dataSnapshot.getChildren()) customersList.add(child.getValue(Customer.class));
                    customers.get(customersList);
                }
            }
            @Override public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    default int generateVerificationCode(ArrayList<Order> orders){
        Random random = new Random();
        ArrayList<Integer> codes = new ArrayList<>();
        for(int i = 100000; i <= 999999; i++){ codes.add(i); }
        for(Order order : orders){ codes.remove(Integer.valueOf(order.getCode())); }
        return codes.get(random.nextInt(codes.size()));
    }

    default void addMenuItem(Item item) {
        getEnabledItems(new EnabledItems() {
            @Override
            public void get(ArrayList<Item> items) {
                items.add(item);
                FirebaseDatabase.getInstance().getReference("Menu").child("Enabled Items").removeValue();
                FirebaseDatabase.getInstance().getReference("Menu").child("Enabled Items").setValue(items);
            }
        });
    }
    default void enableItem(Item item){
        getDisabledItems(new DisabledItems() {
            @Override
            public void get(ArrayList<Item> disabledItems) {
                disabledItems.remove(item);
                FirebaseDatabase.getInstance().getReference("Menu").child("Disabled Items").removeValue();
                FirebaseDatabase.getInstance().getReference("Menu").child("Disabled Items").setValue(disabledItems);
            }
        });
        getEnabledItems(new EnabledItems() {
            @Override
            public void get(ArrayList<Item> enabledItems) {
                enabledItems.add(item);
                FirebaseDatabase.getInstance().getReference("Menu").child("Enabled Items").removeValue();
                FirebaseDatabase.getInstance().getReference("Menu").child("Enabled Items").setValue(enabledItems);
            }
        });
    }
    default void disableItem(Item item) {
        getEnabledItems(new EnabledItems() {
            @Override
            public void get(ArrayList<Item> enabledItems) {
                enabledItems.remove(item);
                FirebaseDatabase.getInstance().getReference("Menu").child("Enabled Items").removeValue();
                FirebaseDatabase.getInstance().getReference("Menu").child("Enabled Items").setValue(enabledItems);
            }
        });
        getDisabledItems(new DisabledItems() {
            @Override
            public void get(ArrayList<Item> disabledItems) {
                disabledItems.add(item);
                FirebaseDatabase.getInstance().getReference("Menu").child("Disabled Items").removeValue();
                FirebaseDatabase.getInstance().getReference("Menu").child("Disabled Items").setValue(disabledItems);
            }
        });
    }
    default void getEnabledItems(EnabledItems items){
        FirebaseDatabase.getInstance().getReference("Menu").child("Enabled Items").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Item> itemList;
                if (dataSnapshot.exists()) {
                    itemList = new ArrayList<>(Objects.requireNonNull(dataSnapshot.getValue(new GenericTypeIndicator<ArrayList<Item>>() {})));
                }
                else itemList = new ArrayList<>();
                items.get(itemList);
            }
            @Override public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }
    default void getDisabledItems(DisabledItems items){
        FirebaseDatabase.getInstance().getReference("Menu").child("Disabled Items").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Item> itemList;
                if (dataSnapshot.exists()) {
                    itemList = new ArrayList<>(Objects.requireNonNull(dataSnapshot.getValue(new GenericTypeIndicator<ArrayList<Item>>() {})));
                }
                else itemList = new ArrayList<>();
                items.get(itemList);
            }
            @Override public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }
    default void deleteMenuItem(Item item){
        getEnabledItems(new EnabledItems() {
            @Override
            public void get(ArrayList<Item> items) {
                items.remove(item);
                FirebaseDatabase.getInstance().getReference("Menu").child("Enabled Items").removeValue();
                FirebaseDatabase.getInstance().getReference("Menu").child("Enabled Items").setValue(items);
            }
        });
        getDisabledItems(new DisabledItems() {
            @Override
            public void get(ArrayList<Item> disabledItems) {
                disabledItems.remove(item);
                FirebaseDatabase.getInstance().getReference("Menu").child("Disabled Items").removeValue();
                FirebaseDatabase.getInstance().getReference("Menu").child("Disabled Items").setValue(disabledItems);
            }
        });
    }

    default void addLocation(Location location) {
        getEnabledLocations(new EnabledLocations() {
            @Override
            public void get(ArrayList<Location> locations) {
                locations.add(location);
                FirebaseDatabase.getInstance().getReference("Locations").child("Enabled Locations").removeValue();
                FirebaseDatabase.getInstance().getReference("Locations").child("Enabled Locations").setValue(locations);
            }
        });
    }
    default void enableLocation(Location location){
        getDisabledLocations(new DisabledLocations() {
            @Override
            public void get(ArrayList<Location> disabledLocations) {
                disabledLocations.remove(location);
                FirebaseDatabase.getInstance().getReference("Locations").child("Disabled Locations").removeValue();
                FirebaseDatabase.getInstance().getReference("Locations").child("Disabled Locations").setValue(disabledLocations);
            }
        });
        getEnabledLocations(new EnabledLocations() {
            @Override
            public void get(ArrayList<Location> enabledLocations) {
                enabledLocations.add(location);
                FirebaseDatabase.getInstance().getReference("Locations").child("Enabled Locations").removeValue();
                FirebaseDatabase.getInstance().getReference("Locations").child("Enabled Locations").setValue(enabledLocations);
            }
        });
    }
    default void disableLocation(Location location) {
        getEnabledLocations(new EnabledLocations() {
            @Override
            public void get(ArrayList<Location> enabledLocations) {
                enabledLocations.remove(location);
                FirebaseDatabase.getInstance().getReference("Locations").child("Enabled Locations").removeValue();
                FirebaseDatabase.getInstance().getReference("Locations").child("Enabled Locations").setValue(enabledLocations);
            }
        });
        getDisabledLocations(new DisabledLocations() {
            @Override
            public void get(ArrayList<Location> disabledLocations) {
                disabledLocations.add(location);
                FirebaseDatabase.getInstance().getReference("Locations").child("Disabled Locations").removeValue();
                FirebaseDatabase.getInstance().getReference("Locations").child("Disabled Locations").setValue(disabledLocations);
            }
        });
    }
    default void getEnabledLocations(EnabledLocations locations){
        FirebaseDatabase.getInstance().getReference("Locations").child("Enabled Locations").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Location> locationList;
                if (dataSnapshot.exists()) {
                    locationList = new ArrayList<>(Objects.requireNonNull(dataSnapshot.getValue(new GenericTypeIndicator<ArrayList<Location>>() {})));
                }
                else locationList = new ArrayList<>();
                locations.get(locationList);
            }
            @Override public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }
    default void getDisabledLocations(DisabledLocations locations){
        FirebaseDatabase.getInstance().getReference("Locations").child("Disabled Locations").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Location> locationList;
                if (dataSnapshot.exists()) {
                    locationList = new ArrayList<>(Objects.requireNonNull(dataSnapshot.getValue(new GenericTypeIndicator<ArrayList<Location>>() {})));
                }
                else locationList = new ArrayList<>();
                locations.get(locationList);
            }
            @Override public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }
    default void deleteLocation(Location location){
        getEnabledLocations(new EnabledLocations() {
            @Override
            public void get(ArrayList<Location> locations) {
                locations.remove(location);
                FirebaseDatabase.getInstance().getReference("Locations").child("Enabled Locations").removeValue();
                FirebaseDatabase.getInstance().getReference("Locations").child("Enabled Locations").setValue(locations);
            }
        });
        getDisabledLocations(new DisabledLocations() {
            @Override
            public void get(ArrayList<Location> disabledLocations) {
                disabledLocations.remove(location);
                FirebaseDatabase.getInstance().getReference("Locations").child("Disabled Locations").removeValue();
                FirebaseDatabase.getInstance().getReference("Locations").child("Disabled Locations").setValue(disabledLocations);
            }
        });
    }

    default void addOrder(Order order){
        getOrders(new Orders() {
            @Override
            public void get(ArrayList<Order> orders) {
                Order newOrder = new Order(order.getItems(), order.getLocation(), Account.userID, generateVerificationCode(orders));
                orders.add(newOrder);
                FirebaseDatabase.getInstance().getReference("Orders").removeValue();
                FirebaseDatabase.getInstance().getReference("Orders").setValue(orders);
            }
        });
    }
    default void getOrders(Orders orders){
        FirebaseDatabase.getInstance().getReference("Orders").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Order> orderList;
                if (dataSnapshot.exists()) {
                    orderList = new ArrayList<>(Objects.requireNonNull(dataSnapshot.getValue(new GenericTypeIndicator<ArrayList<Order>>() {})));
                }
                else orderList = new ArrayList<>();
                orders.get(orderList);
            }
            @Override public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    default void addCartItem(Item item){
        getCartItems(new CartItems() {
            @Override
            public void get(ArrayList<Item> cartItems) {
                cartItems.add(item);
                FirebaseDatabase.getInstance().getReference("Customers").child(Account.userID).child("Cart").removeValue();
                FirebaseDatabase.getInstance().getReference("Customers").child(Account.userID).child("Cart").setValue(cartItems);
            }
        });
    }
    default void getCartItems(CartItems cartItems){
        FirebaseDatabase.getInstance().getReference("Customers").child(Account.userID).child("Cart").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Item> items;
                if (dataSnapshot.exists()) {
                    items = new ArrayList<>(Objects.requireNonNull(dataSnapshot.getValue(new GenericTypeIndicator<ArrayList<Item>>() {})));
                }
                else items = new ArrayList<>();
                cartItems.get(items);
            }
            @Override public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }
    default void removeCartItem(Item item){
        getCartItems(new CartItems() {
            @Override
            public void get(ArrayList<Item> cartItems) {
                cartItems.remove(item);
                FirebaseDatabase.getInstance().getReference("Customers").child(Account.userID).child("Cart").removeValue();
                FirebaseDatabase.getInstance().getReference("Customers").child(Account.userID).child("Cart").setValue(cartItems);
            }
        });
    }
    default void changeQuantity(Item oldItem, int newQuantity){
        getCartItems(new CartItems() {
            @Override
            public void get(ArrayList<Item> cartItems) {
                Item newItem = new Item(oldItem.getItemName(), oldItem.getItemDescription(), oldItem.getItemCost(), newQuantity);
                int index = cartItems.indexOf(oldItem);
                cartItems.remove(oldItem);
                cartItems.add(index, newItem);
                FirebaseDatabase.getInstance().getReference("Customers").child(Account.userID).child("Cart").removeValue();
                FirebaseDatabase.getInstance().getReference("Customers").child(Account.userID).child("Cart").setValue(cartItems);
            }
        });
    }
    default void changeName(String newName){
        deleteAccount(); Account.name = newName; addCustomer();
    }
    default void deleteCart(){
        FirebaseDatabase.getInstance().getReference("Customers").child(Account.userID).child("Cart").removeValue();
    }
    default void deleteAccount(){
        FirebaseDatabase.getInstance().getReference("Customers").child(Account.userID).removeValue();
    }

    interface Customers { void get(ArrayList<Customer> customers); }
    interface EnabledLocations { void get(ArrayList<Location> locations); }
    interface DisabledLocations { void get(ArrayList<Location> locations); }
    interface EnabledItems { void get(ArrayList<Item> items); }
    interface DisabledItems { void get(ArrayList<Item> disabledItems); }
    interface CartItems { void get(ArrayList<Item> cartItems); }
    interface Orders { void get(ArrayList<Order> orders); }
}