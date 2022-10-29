package com.smartappsusa.menu.Screens;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.smartappsusa.menu.Account;
import com.smartappsusa.menu.Objects.Item;
import com.smartappsusa.menu.R;

public class ItemView extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item);

        Item item = Account.currentItem;
        ((TextView) findViewById(R.id.item_view_name)).setText(
                new String(((TextView) findViewById(R.id.item_view_name)).getText() + " " + item.getItemName()));
        ((TextView) findViewById(R.id.item_view_description)).setText(
                new String(((TextView) findViewById(R.id.item_view_description)).getText() + " " + item.getItemDescription()));
        ((TextView) findViewById(R.id.item_view_cost)).setText(
                new String(((TextView) findViewById(R.id.item_view_cost)).getText() + " $" + item.getItemCost()));
    }
}
