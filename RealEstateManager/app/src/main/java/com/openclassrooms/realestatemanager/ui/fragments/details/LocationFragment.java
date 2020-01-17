package com.openclassrooms.realestatemanager.ui.fragments.details;


import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.openclassrooms.realestatemanager.DI.DI;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.db.SaveToDatabase;
import com.openclassrooms.realestatemanager.model.AdressHouse;
import com.openclassrooms.realestatemanager.model.House;
import com.openclassrooms.realestatemanager.service.RealEstateManagerAPIService;


/**
 * A simple {@link Fragment} subclass.
 */
public class LocationFragment extends Fragment {

    private TextView textView, title;
    private ImageView titleImg;
    private SaveToDatabase database = SaveToDatabase.getInstance(getContext());
    private RealEstateManagerAPIService service = DI.getService();

    public LocationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setRetainInstance(false);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_location, container, false);
        title = view.findViewById(R.id.location_title);
        titleImg = view.findViewById(R.id.location_img);
        textView = view.findViewById(R.id.location_text);
        if (service.getHouse() != null){
            upDateLocation(service.getHouse());
        }
        return view;
    }

    public void upDateLocation(House house) {
        if (house != null) {
            AdressHouse findedHouse = database.adressDao().getAdressWithHouseId(house.getId());
            title.setText("Location");
            textView.setText(findedHouse.getAdress() + "\n" + findedHouse.getCity() + "\n" + findedHouse.getState() +
                    "\n" + findedHouse.getZipCode() + "\n" + findedHouse.getCountry());
            if (title.getVisibility() == View.GONE) {
                titleImg.setVisibility(View.VISIBLE);
                title.setVisibility(View.VISIBLE);
            }
        } else {
            textView.setText("");
            title.setVisibility(View.GONE);
            titleImg.setVisibility(View.GONE);
        }

    }
}
