package com.smartappsusa.menu.Adapters;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smartappsusa.menu.Account;
import com.smartappsusa.menu.Interfaces.CartChangeListener;
import com.smartappsusa.menu.Interfaces.CostChangeListener;
import com.smartappsusa.menu.Interfaces.MenuChangeListener;
import com.smartappsusa.menu.MenuMizeCloudDatabase;
import com.smartappsusa.menu.Objects.Item;
import com.smartappsusa.menu.R;
import com.smartappsusa.menu.Screens.ItemView;

import java.util.ArrayList;

public class MSMenuRecyclerAdapter extends RecyclerView.Adapter<MSMenuRecyclerAdapter.MyViewHolder> {
    private final ArrayList<Item> items;
    private MenuChangeListener menuListener;
    private CartChangeListener cartListener;
    private CostChangeListener costListener;

    public void setMenuChangeListener(MenuChangeListener menuListener) { this.menuListener = menuListener; }
    public void setCartListener(CartChangeListener cartListener) { this.cartListener = cartListener; }
    public void setCostListener(CostChangeListener costListener) { this.costListener = costListener; }

    public MSMenuRecyclerAdapter(ArrayList<Item> items) { this.items = items; }

    @NonNull @Override
    public MSMenuRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MSMenuRecyclerAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.multiscreen_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MSMenuRecyclerAdapter.MyViewHolder holder, int position) {
        if (Account.multiselect == 2) {
            holder.disableEnable.setImageResource(R.drawable.ic_baseline_view_24);
            holder.leftButton.setText(new String("View"));
            holder.status.setTextColor(Color.BLACK);
            if (items.get(position).getItemCost().startsWith("Menu: ")) {
                holder.addRemove.setImageResource(R.drawable.ic_baseline_addtocart_24);
                holder.rightButton.setText(new String("Add"));
                holder.status.setText(new String("$" + items.get(position).getItemCost().replace("Menu: ", "").replace("Cart: ", "")));
                holder.itemName.setText(new String(items.get(position).getItemName()));
            }
            else {
                holder.addRemove.setImageResource(R.drawable.ic_baseline_remove_shopping_cart_24);
                holder.rightButton.setText(new String("Remove"));
                holder.status.setText(new String("Added To Cart"));
                holder.itemName.setText(new String(items.get(position).getItemName() + " ($" + items.get(position).getItemCost().replace("Menu: ", "").replace("Cart: ", "") + ")"));
            }
        }
        else if (Account.multiselect == 3) {
            holder.addRemove.setImageResource(R.drawable.ic_baseline_delete_24);
            holder.rightButton.setText(new String("Delete"));
            if (items.get(position).getItemName().startsWith("Disabled: ")) {
                holder.disableEnable.setImageResource(R.drawable.ic_baseline_enable_24);
                holder.leftButton.setText(new String("Enable"));
                holder.status.setText(new String("Item Disabled"));
                holder.status.setTextColor(Color.RED);
            }
            else {
                holder.disableEnable.setImageResource(R.drawable.ic_baseline_disable_24);
                holder.leftButton.setText(new String("Disable"));
                holder.status.setText(new String("Item Enabled"));
                holder.status.setTextColor(Color.parseColor("#00A603"));
            }
            holder.itemName.setText(items.get(position).getItemName().replace("Enabled: ", "").replace("Disabled: ", ""));
        }
        else if (Account.multiselect == 4) {
            holder.addRemove.setImageResource(R.drawable.ic_baseline_remove_shopping_cart_24);
            holder.rightButton.setText(new String("Remove"));
            holder.status.setText(new String("Cumulative Cost: $" + (Double.parseDouble(items.get(position).getItemCost()) * items.get(position).getQuantity())));
            holder.status.setTextSize(18);
            holder.status.setTextColor(Color.BLACK);
            holder.itemName.setText(new String(items.get(position).getItemName() + " ($" + items.get(position).getItemCost() + " per item)"));

            if(items.get(position).getItemDescription().startsWith("Edit: ")){
                holder.disableEnable.setImageResource(R.drawable.ic_baseline_edit_24);
                holder.leftButton.setFocusable(false);
                holder.leftButton.setText(new String("Quantity: " + items.get(position).getQuantity()));
            }
            else if(items.get(position).getItemDescription().startsWith("Save: ")){
                holder.disableEnable.setImageResource(R.drawable.ic_baseline_enable_24);
                holder.leftButton.setFocusableInTouchMode(true);
                holder.leftButton.setText(new String("Quantity: "));
                holder.leftButton.requestFocus();
                holder.leftButton.addTextChangedListener(new TextWatcher() {
                    @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
                    @Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if(!holder.leftButton.getText().toString().startsWith("Quantity: ")){
                            holder.leftButton.setText(new String("Quantity: "));
                        }
                        else if(!holder.leftButton.getText().toString().equals("Quantity: ")){
                            int quantity = Integer.parseInt(charSequence.toString().replace("Quantity: ",""));
                            if(quantity > 10){
                                holder.leftButton.setText(new String("Quantity: 10"));
                            }
                            else if(quantity < 1){
                                holder.leftButton.setText(new String("Quantity: 1"));
                            }
                        }
                        holder.leftButton.setSelection(holder.leftButton.length());
                    }
                    @Override public void afterTextChanged(Editable editable) {}
                });
            }
        }
    }

    @Override public int getItemCount() { return items.size(); }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, MenuMizeCloudDatabase {
        private final TextView itemName, rightButton, status;
        private final EditText leftButton;
        private final ImageButton disableEnable, addRemove;

        public MyViewHolder(@NonNull View view) {
            super(view);
            itemName = view.findViewById(R.id.item_header_name);
            disableEnable = view.findViewById(R.id.item_view_disable_enable_button);
            addRemove = view.findViewById(R.id.item_delete_register_button);
            leftButton = view.findViewById(R.id.left_button);
            rightButton = view.findViewById(R.id.right_button);
            status = view.findViewById(R.id.item_status);
            addRemove.setOnClickListener(this);
            disableEnable.setOnClickListener(this);
        }

        @Override @SuppressLint("NotifyDataSetChanged")
        public void onClick(View v) {
            Item item = new Item(items.get(getAdapterPosition()).getItemName().replace("Enabled: ", "").replace("Disabled: ", ""), items.get(getAdapterPosition()).getItemDescription(), items.get(getAdapterPosition()).getItemCost(), items.get(getAdapterPosition()).getQuantity());
            Item cartItem = new Item(items.get(getAdapterPosition()).getItemName(), items.get(getAdapterPosition()).getItemDescription(), items.get(getAdapterPosition()).getItemCost().replace("Cart: ", "").replace("Menu: ", ""), items.get(getAdapterPosition()).getQuantity());

            if (Account.multiselect == 2) {
                if (v.getId() == R.id.item_view_disable_enable_button) {
                    Account.currentItem = items.get(getAdapterPosition());
                    v.getContext().startActivity(new Intent(v.getContext(), ItemView.class));
                }
                else if (v.getId() == R.id.item_delete_register_button) {
                    if(rightButton.getText().toString().trim().equals("Add")) {
                        cartItem.setQuantity(1);
                        addCartItem(cartItem);
                        cartListener.onItemAdded(items.get(getAdapterPosition()));
                    }
                    else {
                        removeCartItem(items.get(getAdapterPosition()));
                        Toast.makeText(v.getContext(), "Removed From Cart", Toast.LENGTH_SHORT).show();
                        cartListener.onItemRemoved(items.get(getAdapterPosition()));
                    }
                    notifyDataSetChanged();
                }
            }
            else if (Account.multiselect == 3) {
                if (v.getId() == R.id.item_view_disable_enable_button) {
                    if (leftButton.getText().toString().trim().equals("Disable")) {
                        disableItem(item);
                        menuListener.onItemDisabled(items.get(getAdapterPosition()));
                    }
                    else {
                        enableItem(item);
                        menuListener.onItemEnabled(items.get(getAdapterPosition()));
                    }
                    notifyDataSetChanged();
                }
                else if (v.getId() == R.id.item_delete_register_button) {
                    deleteMenuItem(item);
                    Toast.makeText(v.getContext(), "Item Deleted", Toast.LENGTH_SHORT).show();
                    items.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(getAdapterPosition(), getItemCount());
                }
            }
            else if (Account.multiselect == 4) {
                if (v.getId() == R.id.item_view_disable_enable_button) {
                    if(items.get(getAdapterPosition()).getItemDescription().startsWith("Edit: ")){
                        boolean one = true;
                        for(Item i : items){
                            if (i.getItemDescription().startsWith("Save: ")) { one = false; break; }
                        }
                        if(one) items.get(getAdapterPosition()).setItemDescription("Save: " + items.get(getAdapterPosition()).getItemDescription().replace("Edit: ","").replace("Save: ",""));
                        else Toast.makeText(v.getContext(), "Enter Quantity!", Toast.LENGTH_SHORT).show();
                    }
                    else if(items.get(getAdapterPosition()).getItemDescription().startsWith("Save: ")){
                        disableEnable.setImageResource(R.drawable.ic_baseline_edit_24);
                        item.setItemDescription(item.getItemDescription().replace("Save: ",""));
                        int newQuantity;
                        if(leftButton.getText().toString().equals("Quantity: ")){
                            leftButton.setText(new String("Quantity: 1"));
                            newQuantity = 1;
                        }
                        else newQuantity = Integer.parseInt(leftButton.getText().toString().replace("Quantity: ",""));
                        items.get(getAdapterPosition()).setQuantity(newQuantity);
                        costListener.onQuantityChanged(item, newQuantity);
                        items.get(getAdapterPosition()).setItemDescription("Edit: " + items.get(getAdapterPosition()).getItemDescription().replace("Save: ","").replace("Edit: ",""));
                    }
                    notifyDataSetChanged();
                }
                else if (v.getId() == R.id.item_delete_register_button) {
                    costListener.onItemDeleted(items.get(getAdapterPosition()));
                    removeCartItem(items.get(getAdapterPosition()));
                    items.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(getAdapterPosition(), getItemCount());
                    Toast.makeText(v.getContext(), "Item Removed", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}