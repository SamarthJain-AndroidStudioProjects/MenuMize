package com.smartappsusa.menu.Screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.smartappsusa.menu.Account;
import com.smartappsusa.menu.Adapters.MSLocationsRecyclerAdapter;
import com.smartappsusa.menu.Adapters.MSMenuRecyclerAdapter;
import com.smartappsusa.menu.Adapters.MSOrdersRecyclerAdapter;
import com.smartappsusa.menu.Interfaces.MenuChangeListener;
import com.smartappsusa.menu.Interfaces.CartChangeListener;
import com.smartappsusa.menu.Interfaces.CostChangeListener;
import com.smartappsusa.menu.Interfaces.LocationChangeListener;
import com.smartappsusa.menu.MenuMizeCloudDatabase;
import com.smartappsusa.menu.Objects.Item;
import com.smartappsusa.menu.Objects.Location;
import com.smartappsusa.menu.Objects.Order;
import com.smartappsusa.menu.R;

import java.util.ArrayList;

public class MultiScreen extends AppCompatActivity implements MenuMizeCloudDatabase {
    private double totalCost;
    public static MSOrdersRecyclerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multiscreen);
        ((TextView) findViewById(R.id.total_cost)).setText(new String(""));

        if(Account.multiselect == 1) {
            ((TextView) findViewById(R.id.edit_header)).setText(new String("Edit Locations"));
            ((Button) findViewById(R.id.multiscreen_bottom_button)).setText(new String("Add Location"));
            findViewById(R.id.multiscreen_bottom_button).setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View view) { startActivity(new Intent(getApplicationContext(), Add.class)); }
            });
            getEnabledLocations(new EnabledLocations() {
                @Override
                public void get(ArrayList<Location> locations) {
                    getDisabledLocations(new DisabledLocations() {
                        @Override
                        public void get(ArrayList<Location> disabledLocations) {
                            ArrayList<Location> allLocations = new ArrayList<>();
                            for(Location location : locations) location.setStreetAddress("Enabled: " + location.getStreetAddress());
                            for(Location location : disabledLocations) location.setStreetAddress("Disabled: " + location.getStreetAddress());
                            allLocations.addAll(locations); allLocations.addAll(disabledLocations);

                            RecyclerView recyclerView = findViewById(R.id.recycler_view);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            MSLocationsRecyclerAdapter adapter =  new MSLocationsRecyclerAdapter(allLocations);
                            recyclerView.setAdapter(adapter);
                            adapter.setLocationChangeListener(new LocationChangeListener(){
                                @Override public void onLocationDisabled(Location location) {
                                    int index = allLocations.indexOf(location);
                                    allLocations.remove(location);
                                    location.setStreetAddress("Disabled: " + location.getStreetAddress().replace("Enabled: ", ""));
                                    allLocations.add(index, location);
                                }
                                @Override public void onLocationEnabled(Location location) {
                                    int index = allLocations.indexOf(location);
                                    allLocations.remove(location);
                                    location.setStreetAddress("Enabled: " + location.getStreetAddress().replace("Disabled: ", ""));
                                    allLocations.add(index, location);
                                }
                            });
                        }
                    });
                }
            });
        }
        else if(Account.multiselect == 2){
            findViewById(R.id.multiscreen_bottom_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed(); Account.multiselect = 4;
                    startActivity(new Intent(getApplicationContext(), MultiScreen.class));
                }
            });

            ((TextView) findViewById(R.id.edit_header)).setText(new String("View Menu"));
            ((Button) findViewById(R.id.multiscreen_bottom_button)).setText(new String("View Cart"));

            getEnabledItems(new EnabledItems() {
                @Override
                public void get(ArrayList<Item> items) {
                    getCartItems(new CartItems() {
                        @Override
                        public void get(ArrayList<Item> cartItems) {
                            ArrayList<Item> finalItems = new ArrayList<>();
                            for(Item item : cartItems){
                                item.setItemCost("Cart: " + item.getItemCost());
                                finalItems.add(item);
                            }
                            for(Item item : items){
                                item.setItemCost("Menu: " + item.getItemCost());
                                if(!finalItems.contains(item)){ finalItems.add(item); }
                            }

                            RecyclerView recyclerView = findViewById(R.id.recycler_view);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            MSMenuRecyclerAdapter adapter =  new MSMenuRecyclerAdapter(finalItems);
                            recyclerView.setAdapter(adapter);
                            adapter.setCartListener(new CartChangeListener() {
                                @Override
                                public void onItemAdded(Item item) {
                                    int index = finalItems.indexOf(item);
                                    finalItems.remove(item);
                                    item.setItemCost("Cart: " + item.getItemCost().replace("Menu: ", ""));
                                    finalItems.add(index, item);
                                }

                                @Override
                                public void onItemRemoved(Item item) {
                                    int index = finalItems.indexOf(item);
                                    finalItems.remove(item);
                                    item.setItemCost("Menu: " + item.getItemCost().replace("Cart: ", ""));
                                    finalItems.add(index, item);
                                }
                            });
                        }
                    });
                }
            });
        }
        else if(Account.multiselect == 3){
            findViewById(R.id.multiscreen_bottom_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), Add.class));
                }
            });
            getEnabledItems(new EnabledItems() {
                @Override
                public void get(ArrayList<Item> items) {
                    getDisabledItems(new DisabledItems() {
                        @Override
                        public void get(ArrayList<Item> disabledItems) {
                            ArrayList<Item> allItems = new ArrayList<>();
                            for(Item item : items) item.setItemName("Enabled: " + item.getItemName());
                            for(Item item : disabledItems) item.setItemName("Disabled: " + item.getItemName());
                            allItems.addAll(items); allItems.addAll(disabledItems);

                            RecyclerView recyclerView = findViewById(R.id.recycler_view);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            MSMenuRecyclerAdapter adapter = new MSMenuRecyclerAdapter(allItems);
                            recyclerView.setAdapter(adapter);
                            adapter.setMenuChangeListener(new MenuChangeListener(){
                                @Override
                                public void onItemDisabled(Item item){
                                    int index = allItems.indexOf(item);
                                    allItems.remove(item);
                                    item.setItemName("Disabled: " + item.getItemName().replace("Enabled: ", ""));
                                    allItems.add(index, item);
                                }
                                @Override
                                public void onItemEnabled(Item item) {
                                    int index = allItems.indexOf(item);
                                    allItems.remove(item);
                                    item.setItemName("Enabled: " + item.getItemName().replace("Disabled: ", ""));
                                    allItems.add(index, item);
                                }
                            });
                        }
                    });
                }
            });
        }
        else if(Account.multiselect == 4){
            ((TextView) findViewById(R.id.edit_header)).setText(new String("View Cart"));
            ((Button) findViewById(R.id.multiscreen_bottom_button)).setText(new String("Place Order"));
            ((TextView) findViewById(R.id.total_cost)).setText(new String("Total Cost: $0.0"));

            getCartItems(new CartItems() {
                @Override
                public void get(ArrayList<Item> cartItems) {
                    totalCost = 0;
                    for(Item item : cartItems) {
                        item.setItemDescription("Edit: " + item.getItemDescription());
                        totalCost += (Double.parseDouble(item.getItemCost()) * item.getQuantity());
                    }
                    ((TextView) findViewById(R.id.total_cost)).setText(new String("Total Cost: $" + totalCost));

                    RecyclerView recyclerView = findViewById(R.id.recycler_view);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    MSMenuRecyclerAdapter adapter =  new MSMenuRecyclerAdapter(cartItems);
                    recyclerView.setAdapter(adapter);
                    adapter.setCostListener(new CostChangeListener(){
                        @Override
                        public void onItemDeleted(Item item){
                            totalCost -= (Double.parseDouble(item.getItemCost()) * item.getQuantity());
                            ((TextView) findViewById(R.id.total_cost)).setText(new String("Total Cost: $" + totalCost));
                        }

                        @Override
                        public void onQuantityChanged(Item oldItem, int newQuantity) {
                            totalCost -= (Double.parseDouble(oldItem.getItemCost()) * oldItem.getQuantity());
                            changeQuantity(oldItem, newQuantity);
                            totalCost += (Double.parseDouble(oldItem.getItemCost()) * newQuantity);
                            ((TextView) findViewById(R.id.total_cost)).setText(new String("Total Cost: $" + totalCost));
                        }
                    });
                    findViewById(R.id.multiscreen_bottom_button).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            for(Item item : cartItems){
                                item.setItemDescription(item.getItemDescription().replace("Edit: ",""));
                            }
                            Account.order = new Order(); Account.order.setItems(cartItems);
                            startActivity(new Intent(getApplicationContext(), PlaceOrder.class));
                        }
                    });
                }
            });
        }
        else if(Account.multiselect == 5 || Account.multiselect == 6){
            ((TextView) findViewById(R.id.edit_header)).setText(new String("View Orders"));
            ((Button) findViewById(R.id.multiscreen_bottom_button)).setText(new String("Home"));
            findViewById(R.id.multiscreen_bottom_button).setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View view) { onBackPressed(); }
            });
            RecyclerView recyclerView = findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recyclerView.setItemAnimator(new DefaultItemAnimator());

                    if(Account.multiselect == 5) {
                        adapter = new MSOrdersRecyclerAdapter(Account.currentOrders);
                        recyclerView.setAdapter(adapter);
                    }

                    if(Account.multiselect == 6){
                        getOrders(new Orders() {
                            @Override
                            public void get(ArrayList<Order> orders) {
                                ArrayList<Order> myOrders = new ArrayList<>();
                                for(Order order : orders){
                                    if(order.getUserID().equals(Account.userID)){ myOrders.add(order); }
                                }
                                recyclerView.setAdapter(new MSOrdersRecyclerAdapter(myOrders));
                            }
                        });
                    }
        }
    }
}
//M=1 EditLocation, M=2 View Cart, M=3 Edit Menu, M=4 View Menu,
//M=5 View Orders (Admin), M=6 View Orders (Customer)

//TODO: onBackPressed replacements