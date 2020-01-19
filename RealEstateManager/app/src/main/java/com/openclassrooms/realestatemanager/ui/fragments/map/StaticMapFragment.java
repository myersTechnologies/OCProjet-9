package com.openclassrooms.realestatemanager.ui.fragments.map;


import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.google.android.gms.maps.model.LatLng;
import com.openclassrooms.realestatemanager.DI.DI;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.db.SaveToDatabase;
import com.openclassrooms.realestatemanager.model.AdressHouse;
import com.openclassrooms.realestatemanager.model.House;
import com.openclassrooms.realestatemanager.service.RealEstateManagerAPIService;
import com.openclassrooms.realestatemanager.utils.Utils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

public class StaticMapFragment extends Fragment {

    private SaveToDatabase database = SaveToDatabase.getInstance(getContext());
    private RealEstateManagerAPIService service = DI.getService();
    private ImageView imageView;

    public StaticMapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_static_map, container, false);
        setRetainInstance(false);
        imageView = view.findViewById(R.id.static_map_image);

        if (service.getHouse() != null) {
            setImage(service.getHouse());
        }

        return view;
    }

    private void setImage(House house){
        if (house != null) {
            if (Utils.isInternetAvailable(getContext())) {
                try {
                    AdressHouse adressHouse = database.adressDao().getAdressWithHouseId(house.getId());
                    LatLng latLng = getLocationFromAddress(getActivity(), adressHouse.getAdress() + "," + adressHouse.getCity());
                    String lat = String.valueOf(latLng.latitude);
                    String lng = String.valueOf(latLng.longitude);
                    String url = "http://maps.google.com/maps/api/staticmap?center="
                            + lat + "," + lng +
                            "&zoom=16&size=400x400&maptype=roadmap&markers=color:blue%7Clabel:H%7C" + lat + "," + lng +
                            "&sensor=false&key=AIzaSyDEBMyDO9BpymrK3TCry1vCHdRlvmkIGxo";
                    Picasso.get().load(url).into(imageView);
                } catch(NullPointerException e){}
            }
        } else {
            imageView.setImageResource(0);
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

    public void upDateMap(House house){
        setImage(house);
    }


}
