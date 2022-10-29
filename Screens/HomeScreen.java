package com.smartappsusa.menu.Screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.smartappsusa.menu.Account;
import com.smartappsusa.menu.R;

public class HomeScreen extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen);
        Account.isAdmin = Account.email.equals(Account.admin);

        if(getIntent().getExtras() != null){
            if(getIntent().getExtras().containsKey("Location")) Account.multiselect = 1;
            if(getIntent().getExtras().containsKey("Item")) Account.multiselect = 3;
            startActivity(new Intent(getApplicationContext(), MultiScreen.class));
        }

        if(Account.isAdmin){
            ((TextView) findViewById(R.id.display_name)).setText(new String("Welcome Administrator!"));
        }
        else {
            ((TextView) findViewById(R.id.display_name)).setText(new String("Welcome " + Account.name + "!"));
            ((Button) findViewById(R.id.first_button)).setText(new String("View Menu"));
            ((Button) findViewById(R.id.second_button)).setText(new String("View Cart"));
            ((Button) findViewById(R.id.account_settings_button)).setText(new String("Account Settings"));
        }
        findViewById(R.id.first_button).setOnClickListener(this);
        findViewById(R.id.second_button).setOnClickListener(this);
        findViewById(R.id.account_settings_button).setOnClickListener(this);
        findViewById(R.id.view_orders_button).setOnClickListener(this);
        findViewById(R.id.logout).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.first_button){
            if(Account.isAdmin) Account.multiselect = 1;
            else Account.multiselect = 2;
            startActivity(new Intent(getApplicationContext(), MultiScreen.class));
        }
        else if(v.getId() == R.id.second_button){
            if(Account.isAdmin) Account.multiselect = 3;
            else Account.multiselect = 4;
            startActivity(new Intent(getApplicationContext(), MultiScreen.class));
        }
        else if(v.getId() == R.id.view_orders_button){
            if(Account.isAdmin) Account.multiselect = 5;
            else Account.multiselect = 6;
            startActivity(new Intent(getApplicationContext(), MultiScreen.class));
        }
        else if(v.getId() == R.id.account_settings_button){
            startActivity(new Intent(getApplicationContext(), AccountSettings.class));
        }
        else if(v.getId() == R.id.logout){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), GoogleAuthentication.class));
        }
    }
}