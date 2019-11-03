package com.openclassrooms.realestatemanager.ui.adapters.second;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.util.Util;
import com.openclassrooms.realestatemanager.DI.DI;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.events.DetailsEvent;
import com.openclassrooms.realestatemanager.model.House;
import com.openclassrooms.realestatemanager.model.User;
import com.openclassrooms.realestatemanager.service.RealEstateManagerAPIService;
import com.openclassrooms.realestatemanager.ui.activities.details.DetailsActivity;
import com.openclassrooms.realestatemanager.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

public class ListFragmentAdapter  extends RecyclerView.Adapter<ListFragmentAdapter.ViewHolder>  {

    private List<House> houses;
    private Context context;
    private RealEstateManagerAPIService service;

    public ListFragmentAdapter(List<House> houses, Context context){
        this.houses = houses;
        this.context = context;
        service = DI.getService();
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
        String valeurString = house.getPrice();
        String valeurBrute = valeurString.replaceAll(",", "").replace(".", "");
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        if (service.getPreferences().getMonetarySystem() == "€" && house.getMonetarySystem() == "$") {
            String resultString = formatter.format(Utils.convertDollarToEuro(Integer.parseInt(valeurBrute)));
            String decimalReplacement = resultString.replaceAll("\\s", ",");
            holder.housePrice.setText("€" + " " + decimalReplacement);
        }
        if (service.getPreferences().getMonetarySystem() == "$" && house.getMonetarySystem() == "€"){
            String resultString = formatter.format(Utils.convertEuroToDollar(Integer.parseInt(valeurBrute)));
            String decimalReplacement = resultString.replaceAll("\\s", ",");
            holder.housePrice.setText("$ " + decimalReplacement);
        }
        if (service.getPreferences().getMonetarySystem() == "€" && house.getMonetarySystem() == "€"){
            holder.housePrice.setText("€" + " " + house.getPrice());
        }
        if (service.getPreferences().getMonetarySystem() == "$" && house.getMonetarySystem() == "$"){
            holder.housePrice.setText("$" + " " + house.getPrice());
        }
        try {
            String url = service.getRealPathFromUri(house.getImages().get(position).getPhotoUrl());
            File imageFile = new File(url);
            holder.houseImage.setImageBitmap(service.decodeSampledBitmapFromResource(null, imageFile, 100, 100));
        } catch (Exception e){

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new DetailsEvent(house));
                Intent intent = new Intent(context, DetailsActivity.class);
                context.startActivity(intent);
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
