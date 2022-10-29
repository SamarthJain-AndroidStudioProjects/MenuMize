package com.smartappsusa.menu.Screens;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.smartappsusa.menu.Account;
import com.smartappsusa.menu.Adapters.LocationRecyclerAdapter;
import com.smartappsusa.menu.Interfaces.locationClickListener;
import com.smartappsusa.menu.MenuMizeCloudDatabase;
import com.smartappsusa.menu.Objects.Location;
import com.smartappsusa.menu.R;

import java.util.ArrayList;
import java.util.Random;

public class PlaceOrder extends AppCompatActivity implements MenuMizeCloudDatabase {
    private LocationRecyclerAdapter adapter;
    private Random random;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_order);
        random = new Random();

        getEnabledLocations(new EnabledLocations() {
            @Override
            public void get(ArrayList<Location> locations) {
                RecyclerView recyclerView = findViewById(R.id.place_order_location_rv);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setItemAnimator(new DefaultItemAnimator());

                locationClickListener locationClickListener = new locationClickListener() {
                    @Override public void onClick(Location location) {
                        recyclerView.post(new Runnable() {
                            @SuppressLint("NotifyDataSetChanged")
                            @Override public void run() { adapter.notifyDataSetChanged(); }
                        });
                        Account.order.setLocation(location);
                    }
                };
                adapter = new LocationRecyclerAdapter(locations, locationClickListener);
                recyclerView.setAdapter(adapter);
            }
        });

        findViewById(R.id.paypal_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addOrder(Account.order);

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel("4554", "Notification", NotificationManager.IMPORTANCE_HIGH);
                    NotificationManager manager = getSystemService(NotificationManager.class);
                    manager.createNotificationChannel(channel);
                }

                NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "4554");
                builder.setContentTitle("You have placed an order!");
                builder.setContentText("View your order in the \"View Orders\" tab.");
                builder.setSmallIcon(R.drawable.ic_launcher_background);
                builder.setAutoCancel(true);

                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getApplicationContext());
                managerCompat.notify(random.nextInt(), builder.build());
                deleteCart();
                startActivity(new Intent(getApplicationContext(), HomeScreen.class));
            }
        });
    }
}