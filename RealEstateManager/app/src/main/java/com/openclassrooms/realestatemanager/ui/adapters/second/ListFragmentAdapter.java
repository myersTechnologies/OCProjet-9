package com.openclassrooms.realestatemanager.ui.adapters.second;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.events.DetailsEvent;
import com.openclassrooms.realestatemanager.model.House;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class ListFragmentAdapter  extends RecyclerView.Adapter<ListFragmentAdapter.ViewHolder>  {

    List<House> houses;

    public ListFragmentAdapter(List<House> houses){
        this.houses = houses;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final House house = houses.get(position);
        holder.houseName.setText(house.getName());
        holder.houseAdress.setText(house.getCity());
        holder.housePrice.setText("$" + " " + String.valueOf(house.getPrice()));
        holder.houseImage.setImageResource(house.getImages().get(0).getPhotoUrl());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new DetailsEvent(house));
            }
        });

    }

    @Override
    public int getItemCount() {
        return houses.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder  {

        private ImageView houseImage;
        private TextView houseName;
        private TextView houseAdress;
        private TextView housePrice;

        private ViewHolder(View itemView) {
            super(itemView);

            houseImage = itemView.findViewById(R.id.house_img);
            houseName = itemView.findViewById(R.id.house_title);
            housePrice = itemView.findViewById(R.id.house_price);
            houseAdress = itemView.findViewById(R.id.house_location_city);

        }
    }

}
