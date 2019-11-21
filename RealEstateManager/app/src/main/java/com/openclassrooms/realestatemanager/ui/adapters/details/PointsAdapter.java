package com.openclassrooms.realestatemanager.ui.adapters.details;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.model.House;

import java.util.ArrayList;
import java.util.List;

public class PointsAdapter extends RecyclerView.Adapter<PointsAdapter.ViewHolder> {

    private List<String> points = new ArrayList<>();
    private House house;


    public PointsAdapter(House house) {
        this.house = house;
        getPoints();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.points_image_layout,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        String point = points.get(position);
        int icon = R.drawable.other;
        if (point.equals("Transports")){
            icon = R.drawable.transports;
        }
        if (point.equals("Diversion")){
            icon = R.drawable.parc_amusant;
        }
        if (point.equals("School")){
            icon = R.drawable.education;
        }
        if (point.equals("Establishment")){
            icon = R.drawable.store;
        }
        if (point.equals("Health")){
            icon = R.drawable.health;
        }
        if(point.equals("Utils")){
            icon = R.drawable.utils;
        }
        if (point.equals("Politic")){
            icon = R.drawable.political;
        }
        if (point.equals("Religion")){
            icon = R.drawable.pray;
        }
        if (point.equals("Food")){
            icon = R.drawable.food;
        }
        if (point.equals("Hotel")){
            icon = R.drawable.hotel;
        }
        if (point.equals("Court")){
            icon = R.drawable.court;
        }
        if (point.equals("Market")){
            icon = R.drawable.cart;
        }
        if (point.equals("Bank")){
            icon = R.drawable.money;
        }
        if (point.equals("Police")){
            icon = R.drawable.police;
        }
        if (point.equals("Fire Station")){
            icon = R.drawable.fire;
        }
        if (point.equals("Gym")){
            icon = R.drawable.gym;
        }
        if (point.equals("Gas Station")){
            icon = R.drawable.gas;
        }
        if (point.equals("Agency")){
            icon = R.drawable.logo;
        }
        if (point.equals("Stadium")){
            icon = R.drawable.stadium;
        }
        if (point.equals("Parking")){
            icon = R.drawable.parking;
        }
        if (point.equals("Post Office")){
            icon = R.drawable.post_offce;
        }
        if (point.equals("Car Workshop")){
            icon = R.drawable.car;
        }

        viewHolder.imageView.setImageResource(icon);
        viewHolder.textView.setText(point);


    }

    @Override
    public int getItemCount() {
        return points.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.points_image);
            textView = itemView.findViewById(R.id.points_text);
        }
    }

    //get google maps points of interest
    public void getPoints(){
        String[] point = house.getPointsOfInterest().split(",");
        for (int i = 0; i < point.length; i++){
            points.add(point[i]);
        }
    }

}
