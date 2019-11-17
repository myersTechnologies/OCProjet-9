package com.openclassrooms.realestatemanager.ui.adapters.details;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.maps.model.LatLng;
import com.openclassrooms.realestatemanager.DI.DI;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.db.SaveToDatabase;
import com.openclassrooms.realestatemanager.model.AdressHouse;
import com.openclassrooms.realestatemanager.model.House;
import com.openclassrooms.realestatemanager.model.HouseDetails;
import com.openclassrooms.realestatemanager.service.RealEstateManagerAPIService;
import com.openclassrooms.realestatemanager.utils.Utils;


import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;


public class InfoFragmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private House house;
    private RealEstateManagerAPIService service;
    private HouseDetails details;
    private SaveToDatabase database;
    private Context context;

    public InfoFragmentAdapter(House house, HouseDetails details, Context context) {
        this.house = house;
        service = DI.getService();
        this.details = details;
        this.context = context;
        database = SaveToDatabase.getInstance(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder viewHolder = null;

        if (DI.getService().activityName() != "Second") {
            if (viewType == 0){
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.info_details_layout,parent,false);
                viewHolder = new ViewHolder(view);
            }
            if (viewType == 1){
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_description, parent, false);
                viewHolder = new DescriptionViewHolder(view);
            }
            if (viewType == 2) {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_location, parent, false);
                viewHolder = new LocationHolder(view);
            }
            if (viewType == 3){
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.details_details_second,parent,false);
                viewHolder = new SecondViewHolder(view);
            }
            if (viewType == 4) {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_static_map, parent, false);
                viewHolder = new MapViewHolder(view);
            }
        } else {
            if (viewType == 0){
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.info_details_layout,parent,false);
                viewHolder = new ViewHolder(view);
            }

            if (viewType == 1){
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.details_details_second,parent,false);
                viewHolder = new SecondViewHolder(view);
            }
        }


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holderView, int position) {
        if (DI.getService().activityName() != "Second") {
            if (holderView.getItemViewType() == 0) {
                ViewHolder holder = (ViewHolder) holderView;
                if (details != null) {
                    holder.surfaceText.setText(Utils.getMeasureWithMeasureSystem(house, details));
                    holder.roomsText.setText(String.valueOf(details.getRoomsNumber()));
                    holder.bathroomsText.setText(String.valueOf(details.getBathroomsNumber()));
                    holder.bedroomsText.setText(String.valueOf(details.getBedroomsNumber()));
                }
            }

            if (holderView.getItemViewType() == 1) {
                DescriptionViewHolder descriptionViewHolder = (DescriptionViewHolder) holderView;
                LinearLayoutManager layoutManager = new LinearLayoutManager(descriptionViewHolder.itemView.getContext());
                DescriptionAdapter adapter = new DescriptionAdapter(details);
                descriptionViewHolder.descriptionRv.setLayoutManager(layoutManager);
                descriptionViewHolder.descriptionRv.setAdapter(adapter);

            }

            if (holderView.getItemViewType() == 2) {
                LocationHolder locationHolder = (LocationHolder) holderView;
                for (int i = 0; i < database.adressDao().getAdresses().size(); i++) {
                    AdressHouse adressHouse = database.adressDao().getAdresses().get(i);
                    if (adressHouse.getHouseId().equals(String.valueOf(house.getId()))) {
                        AdressHouse findedHouse = adressHouse;
                        locationHolder.textView.setText(findedHouse.getAdress() + "\n" + findedHouse.getCity() + "\n" + findedHouse.getState() +
                                "\n" + findedHouse.getZipCode() + "\n" + findedHouse.getCountry());
                        if (locationHolder.titleImg.getVisibility() == View.GONE){
                            locationHolder.titleImg.setVisibility(View.VISIBLE);
                            locationHolder.titleText.setVisibility(View.VISIBLE);
                        }

                    }
                }
            }

            if (holderView.getItemViewType() == 3) {
                SecondViewHolder holder = (SecondViewHolder) holderView;

                String valeurString = house.getPrice();
                String valeurBrute = valeurString.replaceAll(",", "");
                DecimalFormat formatter = new DecimalFormat("###,###,###");
                holder.priceTitle.setText(Utils.getPriceWithMonetarySystem(valeurBrute, house, formatter));

                for (int i = 0; i < service.getUsers().size(); i++) {
                    if (service.getUsers().get(i).getUserId().equals(house.getAgentId())) {
                        Glide.with(holder.itemView.getContext()).load(service.getUsers().get(i).getPhotoUri())
                                .apply(RequestOptions.circleCropTransform()).into(holder.agentAvatar);
                        holder.agentName.setText(service.getUsers().get(i).getName());
                    }
                }

                if (house.isAvailable()) {
                    holder.isAvailableImg.setImageResource(R.drawable.for_sale);
                } else {
                    holder.isAvailableImg.setImageResource(R.drawable.sold);
                }

            }

            if (holderView.getItemViewType() == 4) {
                MapViewHolder mapViewHolder = (MapViewHolder) holderView;
                for (int j = 0; j < database.adressDao().getAdresses().size(); j++) {
                    if (database.adressDao().getAdresses().get(j).getHouseId().equals(house.getId())) {
                        Context context = holderView.itemView.getContext();
                        AdressHouse adressHouse = database.adressDao().getAdresses().get(j);
                        LatLng latLng = getLocationFromAddress(context, adressHouse.getAdress() + "," + adressHouse.getCity());
                        String lat = String.valueOf(latLng.latitude);
                        String lng = String.valueOf(latLng.longitude);
                        String url = "http://maps.google.com/maps/api/staticmap?center="
                                + lat + "," + lng +
                                "&zoom=16&size=400x400&maptype=roadmap&markers=color:blue%7Clabel:H%7C" + lat + "," + lng +
                                "&sensor=false&key=AIzaSyDEBMyDO9BpymrK3TCry1vCHdRlvmkIGxo";
                        Glide.with(holderView.itemView.getContext()).load(url).into(mapViewHolder.imageView);
                    }
                }
            }
        } else {
            if (holderView.getItemViewType() == 0) {
                ViewHolder holder = (ViewHolder) holderView;
                if (details != null) {
                    holder.surfaceText.setText(Utils.getMeasureWithMeasureSystem(house, details));
                    holder.roomsText.setText(String.valueOf(details.getRoomsNumber()));
                    holder.bathroomsText.setText(String.valueOf(details.getBathroomsNumber()));
                    holder.bedroomsText.setText(String.valueOf(details.getBedroomsNumber()));
                }
            }
            if (holderView.getItemViewType() == 1) {
                SecondViewHolder holder = (SecondViewHolder) holderView;

                String valeurString = house.getPrice();
                String valeurBrute = valeurString.replaceAll(",", "");
                DecimalFormat formatter = new DecimalFormat("###,###,###");
                holder.priceTitle.setText(Utils.getPriceWithMonetarySystem(valeurBrute, house, formatter));

                for (int i = 0; i < service.getUsers().size(); i++) {
                    if (service.getUsers().get(i).getUserId().equals(house.getAgentId())) {
                        Glide.with(holder.itemView.getContext()).load(service.getUsers().get(i).getPhotoUri())
                                .apply(RequestOptions.circleCropTransform()).into(holder.agentAvatar);
                        holder.agentName.setText(service.getUsers().get(i).getName());
                    }
                }

                if (house.isAvailable()) {
                    holder.isAvailableImg.setImageResource(R.drawable.for_sale);
                } else {
                    holder.isAvailableImg.setImageResource(R.drawable.sold);
                }

            }

        }

    }

    @Override
    public int getItemViewType(int position) {
        switch (position){
            case 0:
                return 0;
            case 1:
                return 1;
            case 2 :
                return 2;
            case 3:
                return 3;
            case 4:
                return 4;
        }
        return position;
    }

    @Override
    public int getItemCount() {
        if (DI.getService().activityName() != "Second") {
            return 5;
        } else {
            return 2;
        }
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

    static class ViewHolder extends RecyclerView.ViewHolder  {

        private TextView surfaceText;
        private TextView roomsText;
        private TextView bathroomsText;
        private TextView bedroomsText;


        private ViewHolder(View itemView) {
            super(itemView);

            surfaceText = itemView.findViewById(R.id.surface_text);
            roomsText = itemView.findViewById(R.id.rooms_text);
            bathroomsText = itemView.findViewById(R.id.bathrooms_text);
            bedroomsText = itemView.findViewById(R.id.bedrooms_text);

        }
    }

    static class SecondViewHolder extends RecyclerView.ViewHolder{

        private TextView priceTitle;
        private ImageView agentAvatar;
        private TextView agentName;
        private ImageView isAvailableImg;

        public SecondViewHolder(View itemView) {
            super(itemView);
            priceTitle = itemView.findViewById(R.id.details_price_title);
            agentAvatar = itemView.findViewById(R.id.agent_details_avatar);
            agentName = itemView.findViewById(R.id.agent_details_name);
            isAvailableImg = itemView.findViewById(R.id.is_available_img);
        }
    }

    static class MapViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        public MapViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.static_map_image);
        }
    }

    static class LocationHolder extends RecyclerView.ViewHolder{
        private TextView textView, titleText;
        private ImageView titleImg;
        public LocationHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.location_text);
            titleText = itemView.findViewById(R.id.location_title);
            titleImg = itemView.findViewById(R.id.location_img);
        }
    }

    static class DescriptionViewHolder extends RecyclerView.ViewHolder{

        private RecyclerView descriptionRv;
        public DescriptionViewHolder(@NonNull View itemView) {
            super(itemView);

            descriptionRv = itemView.findViewById(R.id.description_rv);
        }
    }

}
