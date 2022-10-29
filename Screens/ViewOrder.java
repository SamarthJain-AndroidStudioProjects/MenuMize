package com.smartappsusa.menu.Screens;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.smartappsusa.menu.Account;
import com.smartappsusa.menu.Adapters.OrderRecyclerAdapter;
import com.smartappsusa.menu.R;

public class ViewOrder extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_order);

        ((TextView) findViewById(R.id.view_order_header)).setText(String.valueOf(Account.order.getCode()));
        String location = Account.order.getLocation().getStreetAddress() + ", " + Account.order.getLocation().getCityTown() + ", " + Account.order.getLocation().getState();
        ((TextView) findViewById(R.id.pickup_location)).setText(location);

        RecyclerView recyclerView = findViewById(R.id.view_order_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new OrderRecyclerAdapter(Account.order.getItems()));
    }
}

