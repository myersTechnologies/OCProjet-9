package com.openclassrooms.realestatemanager.ui.fragments.details;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openclassrooms.realestatemanager.DI.DI;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.db.SaveToDatabase;
import com.openclassrooms.realestatemanager.model.House;
import com.openclassrooms.realestatemanager.model.HouseDetails;
import com.openclassrooms.realestatemanager.service.RealEstateManagerAPIService;
import com.openclassrooms.realestatemanager.ui.adapters.details.InfoFragmentAdapter;

import java.util.List;


public class InfoFragment extends Fragment {

    private static InfoFragment infoFragment;
    private RealEstateManagerAPIService service;
    private RecyclerView infoList;
    private LinearLayoutManager layoutManager;
    private InfoFragmentAdapter adapter;
    private HouseDetails details;
    private List<HouseDetails> houseDetailsList;
    private SaveToDatabase database = SaveToDatabase.getInstance(getContext());
    private MediaFragment mediaFragment;

    public static InfoFragment newInstance(){
        infoFragment = new InfoFragment();
        return infoFragment;
    }


    public InfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        service = DI.getService();
        infoList = view.findViewById(R.id.info_list);

        if (service.getHouse() != null) {
            updateAdapter(service.getHouse());
        }

        return view;
    }

    public void updateAdapter(House house){
        houseDetailsList = database.houseDetailsDao().getDetails();
        for (int i = 0; i < houseDetailsList.size(); i++){
            if (houseDetailsList.get(i).getHouseId().equals(house.getId())) {
                details = houseDetailsList.get(i);
            }
        }
        adapter = new InfoFragmentAdapter(house, details, getContext());
        layoutManager = new LinearLayoutManager(getContext());
        infoList.setLayoutManager(layoutManager);
        infoList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }



}
