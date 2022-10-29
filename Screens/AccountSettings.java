package com.smartappsusa.menu.Screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.smartappsusa.menu.Account;
import com.smartappsusa.menu.MenuMizeCloudDatabase;
import com.smartappsusa.menu.R;

public class AccountSettings extends AppCompatActivity implements MenuMizeCloudDatabase, View.OnClickListener{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_settings);
        ((TextView) findViewById(R.id.current_name)).setText(new String(Account.name));
        findViewById(R.id.change_name_btn).setOnClickListener(this);
        findViewById(R.id.delete_account_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String name = ((EditText) findViewById(R.id.change_name)).getText().toString().trim();

        if(view.getId() == R.id.change_name_btn){
            if(!name.isEmpty()) {
                changeName(name);
                Toast.makeText(getApplicationContext(), "Account Name Changed!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), HomeScreen.class));
                ((EditText) findViewById(R.id.change_name)).setText("");
            }
            else Toast.makeText(getApplicationContext(), "Enter name to be changed!", Toast.LENGTH_SHORT).show();
        }
        else if(view.getId() == R.id.delete_account_btn){
                deleteAccount();
                Toast.makeText(getApplicationContext(), "Account Deleted!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), GoogleAuthentication.class));
        }
    }
}