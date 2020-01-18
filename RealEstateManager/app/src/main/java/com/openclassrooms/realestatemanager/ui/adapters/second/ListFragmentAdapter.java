package com.openclassrooms.realestatemanager.ui.adapters.second;

import android.content.Context;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.model.LatLng;
import com.openclassrooms.realestatemanager.DI.DI;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.db.SaveToDatabase;
import com.openclassrooms.realestatemanager.events.DetailsEvent;
import com.openclassrooms.realestatemanager.firebase.FirebaseHelper;
import com.openclassrooms.realestatemanager.model.AdressHouse;
import com.openclassrooms.realestatemanager.model.House;
import com.openclassrooms.realestatemanager.model.HouseDetails;
import com.openclassrooms.realestatemanager.model.Photo;
import com.openclassrooms.realestatemanager.service.RealEstateManagerAPIService;
import com.openclassrooms.realestatemanager.utils.Utils;
import com.openclassrooms.realestatemanager.utils.places.GetPointsString;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ListFragmentAdapter  extends RecyclerView.Adapter<ListFragmentAdapter.ViewHolder>  {

    private List<House> houses;
    private RealEstateManagerAPIService service;
    private House house;
    private SaveToDatabase database;
    private List<View> holders = new ArrayList<>();
    private Context context;

    public ListFragmentAdapter(List<House> houses, Context context){
        this.houses = houses;
        service = DI.getService();
        database = SaveToDatabase.getInstance(context);
        this.context = context;
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
        holders.add(holder.itemView);

        AdressHouse adressHouse = database.adressDao().getAdressWithHouseId(house.getId());
        holder.houseAdress.setText(adressHouse.getCity());

        String valeurString = house.getPrice();
        String valeurBrute = valeurString.replaceAll(",", "");
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        holder.housePrice.setText(Utils.getPriceWithMonetarySystem(valeurBrute, house, formatter));

        List<Photo> photoList = new ArrayList<>();
       if (!Utils.isInternetAvailable(holder.itemView.getContext())) {
           if (database.photoDao().getPhotos() != null) {
               for (int i = 0; i < database.photoDao().getPhotos().size(); i++) {
                   Photo photo = database.photoDao().getPhotos().get(i);
                   if (photo.getHouseId().equals(String.valueOf(house.getId()))) {
                       photoList.add(photo);
                   }
               }
           }
       }else {
           FirebaseHelper helper = DI.getFirebaseDatabase();
           while (helper.getPhotos().size() < 0){}
           if (helper.getPhotos() != null) {
               for (int i = 0; i < helper.getPhotos().size(); i++) {
                   Photo photo = helper.getPhotos().get(i);
                   if (photo.getHouseId().equals(String.valueOf(house.getId()))) {
                       photoList.add(photo);
                   }
               }
           }
       }


        if (photoList.size() > 0) {
            Glide.with(holder.itemView.getContext()).load(photoList.get(0).getPhotoUrl()).into(holder.houseImage);
        }

        if (house.getPointsOfInterest() == null){
            checkPointsOfInterest(adressHouse, house, photoList, database.houseDetailsDao().getDetailsWithHouseId(house.getId()));
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new DetailsEvent(houses.get(position)));
                for (View views : holders){
                    views.setBackgroundColor(Color.WHITE);
                }
                holder.itemView.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.colorAccent));

            }
        });

    }


    public void checkPointsOfInterest(AdressHouse adress, House house, List<Photo> photos, HouseDetails details ){

        if (Utils.isInternetAvailable(context)) {
            LatLng current = getLocationFromAddress(context, adress.getAdress() + "," + adress.getCity());
            try {
                String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" +
                        current.latitude + "," + current.longitude +
                        "&radius=100&type=point_of_interst&key=AIzaSyBE7FhkDrMMk12zVVn_HR1IlcGZoKc3-oQ";
                Object dataTransfer[] = new Object[7];
                dataTransfer[0] = url;
                dataTransfer[1] = house;
                dataTransfer[2] = DI.getService();
                dataTransfer[3] = DI.getService().getActivity();
                dataTransfer[4] = details;
                dataTransfer[5] = adress;
                dataTransfer[6] = photos;
                GetPointsString getNearbyPlacesData = new GetPointsString();
                getNearbyPlacesData.execute(dataTransfer);
            }catch (Exception e){}
        }
    }

    public static LatLng getLocationFromAddress(Context context, String strAddress) {

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
