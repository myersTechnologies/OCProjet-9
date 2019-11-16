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
        layoutManager = new LinearLayoutManager(getContext());

        houseDetailsList = database.houseDetailsDao().getDetails();
        for (HouseDetails houseDetails : houseDetailsList){
            if (houseDetails.getId().equals(service.getHouse().getId())){
                details = houseDetails;
            }
        }

        adapter = new InfoFragmentAdapter(service.getHouse(), details, getContext());
        infoList.setLayoutManager(layoutManager);
        infoList.setAdapter(adapter);

        return view;
    }


}
