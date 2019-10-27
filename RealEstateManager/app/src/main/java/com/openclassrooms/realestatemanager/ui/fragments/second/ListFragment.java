package com.openclassrooms.realestatemanager.ui.fragments.second;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.openclassrooms.realestatemanager.DI.DI;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.events.DetailsEvent;
import com.openclassrooms.realestatemanager.model.House;
import com.openclassrooms.realestatemanager.model.Photo;
import com.openclassrooms.realestatemanager.service.RealEstateManagerAPIService;
import com.openclassrooms.realestatemanager.ui.adapters.second.ListFragmentAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {


    private RecyclerView housesList;
    private ListFragmentAdapter adapter;
    private LinearLayoutManager layoutManager;
    private List<House> houses;
    private RealEstateManagerAPIService service;
    private static ListFragment listFragment;

    public static ListFragment newInstance(){
        if (listFragment == null) {
            listFragment = new ListFragment();
        }
        return listFragment;
    }

    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        housesList = view.findViewById(R.id.houses_list_second_f);
        layoutManager = new LinearLayoutManager(view.getContext());
        service = DI.getService();

        houses = new ArrayList<>();
        Photo photo = new Photo(R.drawable.main_image, "Facade");
        List<Photo> img = new ArrayList<>();
        img.add(photo);
        House house = new House(img, "Loft", "78a avenue de paris", "Chalon sur saone", "Saone et loire", "France",
                "71100", 78.500999, "hkjkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk" +
                "kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkjkjjdbbdhbbwhbwhbwvx" +
                "jbjbjbsjbsjbsjsbjsbjsbsjbsjsbjsbsbsjbsjbsjdhjdhjsbjbsjb" +
                "knjdjdkjdjkdjdkdjkdjjdknsns,sns,nssnnbd");
        houses.add(house);
        adapter = new ListFragmentAdapter(houses);
        service.setHouse(house);
        initList();
        return view;
    }

    private void initList() {
        housesList.setLayoutManager(layoutManager);
        housesList.setAdapter(adapter);
    }


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void changeFragmentOnClick(DetailsEvent event) {
        service.setHouse(event.house);

    }
}


