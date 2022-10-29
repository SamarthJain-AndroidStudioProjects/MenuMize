package com.smartappsusa.menu.Adapters;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smartappsusa.menu.MenuMizeCloudDatabase;
import com.smartappsusa.menu.Objects.Location;
import com.smartappsusa.menu.R;
import com.smartappsusa.menu.Interfaces.LocationChangeListener;

import java.util.ArrayList;

public class MSLocationsRecyclerAdapter extends RecyclerView.Adapter<MSLocationsRecyclerAdapter.MyViewHolder> {
    private final ArrayList<Location> locations;
    private LocationChangeListener locationChangeListener;

    public void setLocationChangeListener(LocationChangeListener locationChangeListener) { this.locationChangeListener = locationChangeListener; }

    public MSLocationsRecyclerAdapter(ArrayList<Location> locations) { this.locations = locations; }

    @NonNull
    @Override
    public MSLocationsRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MSLocationsRecyclerAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.multiscreen_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MSLocationsRecyclerAdapter.MyViewHolder holder, int position) {
        if (locations.get(position).getStreetAddress().startsWith("Disabled: ")) {
            holder.disableEnable.setImageResource(R.drawable.ic_baseline_enable_24);
            holder.leftButton.setText(new String("Enable"));
            holder.status.setText(new String("Location Disabled"));
            holder.status.setTextColor(Color.RED);
            holder.itemName.setText(locations.get(position).getStreetAddress().replace("Disabled: ", ""));
        }
        else {
            holder.itemName.setText(locations.get(position).getStreetAddress().replace("Enabled: ", ""));
            holder.status.setText(new String("Location Enabled"));
            holder.disableEnable.setImageResource(R.drawable.ic_baseline_disable_24);
            holder.leftButton.setText(new String("Disable"));
            holder.status.setTextColor(Color.parseColor("#00A603"));
        }
    }

    @Override public int getItemCount() { return locations.size(); }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, MenuMizeCloudDatabase {
        private final TextView itemName, status;
        private final EditText leftButton;
        private final ImageButton disableEnable;

        public MyViewHolder(@NonNull View view) {
            super(view);
            itemName = view.findViewById(R.id.item_header_name);
            disableEnable = view.findViewById(R.id.item_view_disable_enable_button);
            leftButton = view.findViewById(R.id.left_button);
            status = view.findViewById(R.id.item_status);
            view.findViewById(R.id.item_delete_register_button).setOnClickListener(this);
            disableEnable.setOnClickListener(this);
        }

        @Override @SuppressLint("NotifyDataSetChanged")
        public void onClick(View v) {
            Location oldLocation = locations.get(getAdapterPosition());
            Location newLocation = new Location(oldLocation.getStreetAddress().replace("Enabled: ", "").replace("Disabled: ", ""), oldLocation.getCityTown(), oldLocation.getState());
            if (v.getId() == R.id.item_view_disable_enable_button) {
                if (leftButton.getText().toString().trim().equals("Disable")) {
                    disableLocation(newLocation);
                    locationChangeListener.onLocationDisabled(locations.get(getAdapterPosition()));
                }
                else {
                    enableLocation(newLocation);
                    locationChangeListener.onLocationEnabled(locations.get(getAdapterPosition()));
                }
                notifyDataSetChanged();
            }
            else if (v.getId() == R.id.item_delete_register_button) {
                deleteLocation(newLocation);
                Toast.makeText(v.getContext(), "Location Deleted", Toast.LENGTH_SHORT).show();
                locations.remove(getAdapterPosition());
                notifyItemRemoved(getAdapterPosition());
                notifyItemRangeChanged(getAdapterPosition(), getItemCount());
            }
        }
    }
}
