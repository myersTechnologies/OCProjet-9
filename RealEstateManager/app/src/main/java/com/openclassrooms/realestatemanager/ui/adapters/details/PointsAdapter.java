package com.openclassrooms.realestatemanager.ui.adapters.details;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.gms.maps.model.LatLng;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.db.SaveToDatabase;
import com.openclassrooms.realestatemanager.model.AdressHouse;
import com.openclassrooms.realestatemanager.model.House;
import com.openclassrooms.realestatemanager.utils.places.GetPointsString;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PointsAdapter extends RecyclerView.Adapter<PointsAdapter.ViewHolder> {

    private List<String> points = new ArrayList<>();
    private Context context;
    private House house;


    public PointsAdapter(House house, Context context) {
        this.house = house;
        this.context = context;
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
        if ( point.equals("transport")){
            icon = R.drawable.transports;
        }
        if (point.equals("amusement")){
            icon = R.drawable.parc_amusant;
        }
        if (point.equals("education")){
            icon = R.drawable.education;
        }
        if (point.equals("establissement")){
            icon = R.drawable.store;
        }
        if (point.equals("health")){
            icon = R.drawable.health;
        }
        if(point.equals("utils")){
            icon = R.drawable.utils;
        }
        if (point.equals("political")){
            icon = R.drawable.political;
        }
        if (point.equals("pray")){
            icon = R.drawable.pray;
        }
        if (point.equals("food")){
            icon = R.drawable.food;
        }
        if (point.equals("hotel")){
            icon = R.drawable.hotel;
        }
        if (point.equals("court")){
            icon = R.drawable.court;
        }
        if (point.equals("cart")){
            icon = R.drawable.cart;
        }
        if (point.equals("money")){
            icon = R.drawable.money;
        }
        if (point.equals("police")){
            icon = R.drawable.police;
        }
        if (point.equals("fire_station")){
            icon = R.drawable.fire;
        }
        if (point.equals("gym")){
            icon = R.drawable.gym;
        }
        if (point.equals("gas_station")){
            icon = R.drawable.gas;
        }
        if (point.equals("real_estate_agency")){
            icon = R.drawable.logo;
        }
        if (point.equals("stadium")){
            icon = R.drawable.stadium;
        }
        if (point.equals("parking")){
            icon = R.drawable.parking;
        }
        if (point.equals("post_office")){
            icon = R.drawable.post_offce;
        }

        viewHolder.imageView.setImageResource(icon);


    }

    @Override
    public int getItemCount() {
        return points.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.points_image);
        }
    }

    //get google maps points of interest
    public void getPoints(){
        AdressHouse address = SaveToDatabase.getInstance(context).adressDao().getAdressWithHouseId(house.getId());
        LatLng current = getLocationFromAddress(context, address.getAdress() + "," + address.getCity());
        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" +
                current.latitude  + "," + current.longitude +
                "&radius=100&type=point_of_interst&key=AIzaSyBE7FhkDrMMk12zVVn_HR1IlcGZoKc3-oQ";
        Object dataTransfer[] = new Object[4];
        dataTransfer[0] = url;
        dataTransfer[1] = context;
        dataTransfer[2] = points;
        dataTransfer[3] = PointsAdapter.this;
        GetPointsString getNearbyPlacesData = new GetPointsString();
        getNearbyPlacesData.execute(dataTransfer);
    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude());

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return p1;
    }

}
