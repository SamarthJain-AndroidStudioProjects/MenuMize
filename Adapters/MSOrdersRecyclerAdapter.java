package com.smartappsusa.menu.Adapters;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smartappsusa.menu.Account;
import com.smartappsusa.menu.Objects.Item;
import com.smartappsusa.menu.Objects.Order;
import com.smartappsusa.menu.R;
import com.smartappsusa.menu.Screens.ViewOrder;

import java.util.ArrayList;

public class MSOrdersRecyclerAdapter extends RecyclerView.Adapter<MSOrdersRecyclerAdapter.MyViewHolder> {
    private final ArrayList<Order> orders;

    public MSOrdersRecyclerAdapter(ArrayList<Order> orders) { this.orders = orders; }

    @NonNull
    @Override
    public MSOrdersRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MSOrdersRecyclerAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.multiscreen_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MSOrdersRecyclerAdapter.MyViewHolder holder, int position) {
        holder.orderName.setText(String.valueOf(orders.get(position).getCode()));
        holder.disableEnable.setImageResource(R.drawable.ic_baseline_view_24);
        holder.deleteRegister.setImageResource(R.drawable.ic_baseline_delete_24);
        holder.leftButton.setText(new String("View"));
        holder.rightButton.setText(new String("Delete"));
        double totalCost = 0;
        for (Item item : orders.get(position).getItems()) {
            totalCost += Double.parseDouble(item.getItemCost()) * item.getQuantity();
        }
        holder.status.setText(new String("Total Cost: $" + totalCost));
        holder.status.setTextSize(18);
        holder.status.setTextColor(Color.BLACK);
    }

    @Override public int getItemCount() { return orders.size(); }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView orderName, rightButton, status;
        private final EditText leftButton;
        private final ImageButton disableEnable, deleteRegister;

        public MyViewHolder(@NonNull View view) {
            super(view);
            orderName = view.findViewById(R.id.item_header_name);
            disableEnable = view.findViewById(R.id.item_view_disable_enable_button);
            deleteRegister = view.findViewById(R.id.item_delete_register_button);
            leftButton = view.findViewById(R.id.left_button);
            rightButton = view.findViewById(R.id.right_button);
            status = view.findViewById(R.id.item_status);
            deleteRegister.setOnClickListener(this);
            disableEnable.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.item_view_disable_enable_button) {
                Account.order = orders.get(getAdapterPosition());
                view.getContext().startActivity(new Intent(view.getContext(), ViewOrder.class));
            }
        }
    }
}