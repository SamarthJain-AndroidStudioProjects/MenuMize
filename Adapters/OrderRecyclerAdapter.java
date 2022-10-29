package com.smartappsusa.menu.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smartappsusa.menu.Account;
import com.smartappsusa.menu.Objects.Item;
import com.smartappsusa.menu.R;
import com.smartappsusa.menu.Screens.ItemView;

import java.util.ArrayList;

public class OrderRecyclerAdapter extends RecyclerView.Adapter<OrderRecyclerAdapter.MyViewHolder> {
    private final ArrayList<Item> items;

    public OrderRecyclerAdapter(ArrayList<Item> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public OrderRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderRecyclerAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderRecyclerAdapter.MyViewHolder holder, int position) {
        holder.itemName.setText(new String(items.get(position).getQuantity() + " " + items.get(position).getItemName() + "(s)"));
        holder.itemCost.setText(new String("$" + items.get(position).getQuantity() * Integer.parseInt(items.get(position).getItemCost())));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView itemName;
        private final TextView itemCost;

        public MyViewHolder(@NonNull View view) {
            super(view);
            itemName = view.findViewById(R.id.order_item_name);
            itemCost = view.findViewById(R.id.order_item_cost);
            itemName.setOnClickListener(this);
            itemCost.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.order_item_cost || view.getId() == R.id.order_item_name) {
                Account.currentItem = items.get(getAdapterPosition());
                view.getContext().startActivity(new Intent(view.getContext(), ItemView.class));
            }
        }
    }
}
