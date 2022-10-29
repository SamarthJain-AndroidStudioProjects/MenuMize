package com.smartappsusa.menu.Screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.smartappsusa.menu.Account;
import com.smartappsusa.menu.MenuMizeCloudDatabase;
import com.smartappsusa.menu.Objects.Item;
import com.smartappsusa.menu.Objects.Location;
import com.smartappsusa.menu.R;

public class Add extends AppCompatActivity implements MenuMizeCloudDatabase {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item_location);

        if(Account.multiselect == 1) {
            ((TextView) findViewById(R.id.add_title)).setText(new String("Add Location"));
            ((EditText) findViewById(R.id.name_street_address)).setHint(new String("Street Address"));
            ((EditText) findViewById(R.id.description_city_town)).setHint(new String("City/Town"));
            ((EditText) findViewById(R.id.cost_state)).setHint(new String("State"));
        }
        else if(Account.multiselect == 3) {
            ((TextView) findViewById(R.id.add_title)).setText(new String("Add Item"));
            ((EditText) findViewById(R.id.name_street_address)).setHint(new String("Item Name"));
            ((EditText) findViewById(R.id.description_city_town)).setHint(new String("Item Description (optional)"));
            ((EditText) findViewById(R.id.cost_state)).setHint(new String("Item Cost"));
        }

        findViewById(R.id.add_item_location_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameStreetAddress = ((EditText) findViewById(R.id.name_street_address)).getText().toString().trim();
                String descriptionCityTown = ((EditText) findViewById(R.id.description_city_town)).getText().toString().trim();
                String costState = ((EditText) findViewById(R.id.cost_state)).getText().toString().trim();

                if (!nameStreetAddress.isEmpty() && !costState.isEmpty()) {
                    ((EditText) findViewById(R.id.name_street_address)).setText("");
                    ((EditText) findViewById(R.id.description_city_town)).setText("");
                    ((EditText) findViewById(R.id.cost_state)).setText("");
                    if (Account.multiselect == 1) {
                        addLocation(new Location(nameStreetAddress, descriptionCityTown, costState));
                        Toast.makeText(getApplicationContext(), "Location added", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), HomeScreen.class).putExtra("Location", 1));
                    }
                    else if (Account.multiselect == 3) {
                        addMenuItem(new Item(nameStreetAddress, descriptionCityTown, costState, -1));
                        Toast.makeText(getApplicationContext(), "Item added", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), HomeScreen.class).putExtra("Item", 3));
                    }
                }
                else Toast.makeText(getApplicationContext(), "Fill all required fields!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}