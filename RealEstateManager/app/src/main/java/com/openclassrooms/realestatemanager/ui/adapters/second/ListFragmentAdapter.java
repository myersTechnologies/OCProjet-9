package com.openclassrooms.realestatemanager.ui.adapters.second;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.events.DetailsEvent;
import com.openclassrooms.realestatemanager.model.House;
import com.openclassrooms.realestatemanager.ui.details.DetailsActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class ListFragmentAdapter  extends RecyclerView.Adapter<ListFragmentAdapter.ViewHolder>  {

    private List<House> houses;
    private Context context;

    public ListFragmentAdapter(List<House> houses, Context context){
        this.houses = houses;
        this.context = context;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final House house = houses.get(position);
        holder.houseName.setText(house.getName());
        holder.houseAdress.setText(house.getCity());
        holder.housePrice.setText("$" + " " + String.valueOf(house.getPrice()));
        holder.houseImage.setImageResource(house.getImages().get(0).getPhotoUrl());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new DetailsEvent(house));
                Intent intent = new Intent(context, DetailsActivity.class);
                context.startActivity(intent);
                view.setBackgroundColor(Color.parseColor("#A2C8E9"));
                holder.itemView.setBackgroundColor(Color.parseColor("#A2C8E9"));
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                view.setBackgroundColor(Color.parseColor("#A2C8E9"));
                return false;
            }
        });

        holder.itemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    view.setBackgroundColor(Color.parseColor("#A2C8E9"));
                } else {

                }

                if (motionEvent.getAction() == MotionEvent.ACTION_UP || motionEvent.getAction() == MotionEvent.ACTION_CANCEL)
                {
                    view.setBackgroundColor(Color.TRANSPARENT);
                }
                return false;
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
