package com.smartappsusa.menu.Adapters;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smartappsusa.menu.Objects.Location;
import com.smartappsusa.menu.R;
import com.smartappsusa.menu.Interfaces.locationClickListener;

import java.util.ArrayList;

public class LocationRecyclerAdapter extends RecyclerView.Adapter<LocationRecyclerAdapter.ViewHolder> {
    private final ArrayList<Location> locations;
    private final locationClickListener locationClickListener;
    int selectedPosition = -1;

    public LocationRecyclerAdapter(ArrayList<Location> locations, locationClickListener locationClickListener) {
        this.locations = locations;
        this.locationClickListener = locationClickListener;
    }

    @NonNull @Override
    public LocationRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LocationRecyclerAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.location_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LocationRecyclerAdapter.ViewHolder holder, int position) {
        String location = locations.get(position).getStreetAddress() + ", " + locations.get(position).getCityTown() + ", " + locations.get(position).getState();
        holder.radioButton.setText(location);
        holder.radioButton.setChecked(position == selectedPosition);
        holder.radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton button, boolean isClicked) {
                if (isClicked) {
                    selectedPosition = holder.getAdapterPosition();
                    holder.radioButton.setTypeface(Typeface.DEFAULT_BOLD);
                    locationClickListener.onClick(locations.get(selectedPosition));
                } else holder.radioButton.setTypeface(Typeface.DEFAULT);
            }
        });
    }

    @Override public long getItemId(int position) { return position; }
    @Override public int getItemViewType(int position) { return position; }
    @Override public int getItemCount() { return locations.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final RadioButton radioButton;

        public ViewHolder(@NonNull View view) {
            super(view);
            radioButton = view.findViewById(R.id.choice);
        }
    }
}