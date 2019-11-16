package com.openclassrooms.realestatemanager.ui.adapters.second;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.openclassrooms.realestatemanager.DI.DI;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.db.SaveToDatabase;
import com.openclassrooms.realestatemanager.events.DetailsEvent;
import com.openclassrooms.realestatemanager.model.AdressHouse;
import com.openclassrooms.realestatemanager.model.House;
import com.openclassrooms.realestatemanager.model.Photo;
import com.openclassrooms.realestatemanager.service.RealEstateManagerAPIService;
import com.openclassrooms.realestatemanager.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ListFragmentAdapter  extends RecyclerView.Adapter<ListFragmentAdapter.ViewHolder>  {

    private List<House> houses;
    private RealEstateManagerAPIService service;
    private House house;
    private SaveToDatabase database;

    public ListFragmentAdapter(List<House> houses, Context context){
        this.houses = houses;
        service = DI.getService();
        database = SaveToDatabase.getInstance(context);
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        house = houses.get(position);
        holder.houseName.setText(house.getName());

        for (int i = 0; i < database.adressDao().getAdresses().size(); i++){
            AdressHouse adressHouse = database.adressDao().getAdresses().get(i);
            if (adressHouse.getHouseId().equals(String.valueOf(house.getId()))){
                holder.houseAdress.setText(adressHouse.getCity());
            }
        }
        String valeurString = house.getPrice();
        String valeurBrute = valeurString.replaceAll(",", "");
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        holder.housePrice.setText(Utils.getPriceWithMonetarySystem(valeurBrute, house, formatter));

        List<Photo> photoList = new ArrayList<>();
        if (database.photoDao().getPhotos() != null) {
            for (int i = 0; i < database.photoDao().getPhotos().size(); i++) {
                Photo photo = database.photoDao().getPhotos().get(i);
                if (photo.getHouseId().equals(String.valueOf(house.getId()))) {
                    photoList.add(photo);
                }
            }
        }

        if (photoList.size() > 0) {
            Glide.with(holder.itemView.getContext()).load(photoList.get(0).getPhotoUrl()).into(holder.houseImage);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new DetailsEvent(houses.get(position)));
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
