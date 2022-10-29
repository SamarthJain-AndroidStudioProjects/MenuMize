package com.smartappsusa.menu.Screens;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.smartappsusa.menu.Account;
import com.smartappsusa.menu.MenuMizeCloudDatabase;
import com.smartappsusa.menu.Objects.Customer;
import com.smartappsusa.menu.Objects.Order;
import com.smartappsusa.menu.R;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class GoogleAuthentication extends AppCompatActivity implements MenuMizeCloudDatabase {
    private GoogleSignInClient client;
    private FirebaseAuth authorization;
    private Random random;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.google_sign_in);

        authorization = FirebaseAuth.getInstance();
        random = new Random();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        client = GoogleSignIn.getClient(this, gso);

        findViewById(R.id.sign_in_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                client.signOut();
                Intent signInIntent = client.getSignInIntent();
                startActivityForResult(signInIntent, 4554);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 4554) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try { firebaseAuthWithGoogle(task.getResult(ApiException.class)); }
            catch (ApiException ignored) {}
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        authorization.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) { configure(); }
                else Toast.makeText(getApplicationContext(), "Authentication Failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void configure() {
        Account.userID = FirebaseAuth.getInstance().getUid();
        Account.email = Objects.requireNonNull(GoogleSignIn.getLastSignedInAccount(getApplicationContext())).getEmail();
        Account.name = Objects.requireNonNull(GoogleSignIn.getLastSignedInAccount(getApplicationContext())).getDisplayName();

        if(Account.admin.equals(Account.email)){
            Account.name = "Administrator";
            startActivity(new Intent(getApplicationContext(), HomeScreen.class));
        }
        else {
            getCustomers(new Customers() {
                @Override
                public void get(ArrayList<Customer> customers) {
                    boolean newCustomer = true;
                    for(Customer customer : customers){
                        if(customer.getUserID().equals(Account.userID)){
                            Account.name = customer.getName();
                            newCustomer = false;
                            break;
                        }
                    }
                    if(newCustomer) addCustomer();
                    startActivity(new Intent(getApplicationContext(), HomeScreen.class));
                }
            });
        }
    }

    @Override
    protected void onStop() {
        if(Account.isAdmin) {

            getOrders(new Orders() {
                @Override
                public void get(ArrayList<Order> orders) { Account.currentOrders = orders; }
            });

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    getOrders(new Orders() {
                        @Override
                        public void get(ArrayList<Order> orders) {
                            Order newOrder = null;
                            boolean orderAdded = false;
                            for(Order order : orders){
                                if (!Account.currentOrders.contains(order)) {
                                    orderAdded = true; newOrder = order; break;
                                }
                            }
                            if(orderAdded){
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                    NotificationChannel channel = new NotificationChannel("4554", "Notification", NotificationManager.IMPORTANCE_HIGH);
                                    NotificationManager manager = getSystemService(NotificationManager.class);
                                    manager.createNotificationChannel(channel);
                                }

                                NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "4554");
                                builder.setContentTitle("New order has been placed!");
                                builder.setContentText("A customer has placed an order.");
                                builder.setSmallIcon(R.drawable.ic_launcher_background);
                                builder.setAutoCancel(true);

                                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getApplicationContext());
                                managerCompat.notify(random.nextInt(), builder.build());
                                if(MultiScreen.adapter != null){
                                    Account.currentOrders.add(newOrder);
                                    MultiScreen.adapter.notifyDataSetChanged();
                                }
                            }
                        }
                    });
                    handler.postDelayed(this, 1000);
                }
            }, 0);
        }
        super.onStop();
    }
}