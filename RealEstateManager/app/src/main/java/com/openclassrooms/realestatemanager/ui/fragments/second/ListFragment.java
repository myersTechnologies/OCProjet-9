package com.openclassrooms.realestatemanager.ui.fragments.second;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.openclassrooms.realestatemanager.DI.DI;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.db.SaveToDatabase;
import com.openclassrooms.realestatemanager.events.DetailsEvent;
import com.openclassrooms.realestatemanager.firebase.FirebaseHelper;
import com.openclassrooms.realestatemanager.model.House;
import com.openclassrooms.realestatemanager.service.RealEstateManagerAPIService;
import com.openclassrooms.realestatemanager.ui.activities.details.DetailsActivity;
import com.openclassrooms.realestatemanager.ui.fragments.details.DescriptionFragment;
import com.openclassrooms.realestatemanager.ui.fragments.details.LocationFragment;
import com.openclassrooms.realestatemanager.ui.adapters.second.ListFragmentAdapter;
import com.openclassrooms.realestatemanager.ui.fragments.details.InfoFragment;
import com.openclassrooms.realestatemanager.ui.fragments.details.MediaFragment;
import com.openclassrooms.realestatemanager.ui.fragments.map.StaticMapFragment;
import com.openclassrooms.realestatemanager.utils.SearchHelper;
import com.openclassrooms.realestatemanager.utils.database.DatabaseUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListFragment extends Fragment {


    private RecyclerView housesList;
    private ListFragmentAdapter adapter;
    private LinearLayoutManager layoutManager;
    private List<House> houses;
    private RealEstateManagerAPIService service;
    private SaveToDatabase database;
    private MediaFragment mediaFragment;
    private InfoFragment infoFragment;
    private StaticMapFragment mapFragment;
    private LocationFragment locationFragment;
    private DescriptionFragment descriptionFragment;

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter != null){
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        housesList = view.findViewById(R.id.houses_list_second_f);
        layoutManager = new LinearLayoutManager(view.getContext());
        database = SaveToDatabase.getInstance(getActivity());
        service = DI.getService();

        this.configureAndShowMediaFragment();
        this.configureAndShowDetailsFragment();
        this.configureAndShowStaticMap();
        this.configureAndShowLocationFragment();
        this.configureAndShowDescriptionFragment();

        //check if search model is null if it is just load as default else list searched houses
        if (SearchHelper.getHousesList() == null) {
            houses = database.houseDao().getHouses();
            if (DI.getFirebaseDatabase().getHouses() != null) {
                List<House> commons = new ArrayList<>(DI.getFirebaseDatabase().getHouses());
                commons.retainAll(houses);
                if (houses.size() == commons.size()) {
                    adapter = new ListFragmentAdapter(houses, getActivity());
                    initList();
                } else {
                    adapter = new ListFragmentAdapter(houses, getActivity());
                    checkFirebase();
                }
            } else {
                adapter = new ListFragmentAdapter(houses, getActivity());
                checkFirebase();
            }
        } else {
            adapter = new ListFragmentAdapter(SearchHelper.getHouses(), getActivity());
            initList();
        }

        return view;
    }

    private void checkFirebase(){
        FirebaseHelper helper = DI.getFirebaseDatabase();

        //Loads all list from firebase to sql database
        Object dataTransfer[] = new Object[6];
        dataTransfer[0] = service;
        dataTransfer[1] = getContext();
        dataTransfer[2] = DI.getFirebaseDatabase();
            dataTransfer[3] = adapter;
            dataTransfer[4] = database;
            dataTransfer[5] = housesList;
            DatabaseUtil databaseUtil = new DatabaseUtil(helper);
            databaseUtil.execute(dataTransfer);

        initList();
    }


    private void initList() {
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(housesList.getContext(),
                layoutManager.getOrientation());
        housesList.addItemDecoration(dividerItemDecoration);
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
        if (mediaFragment!= null && mediaFragment.isVisible()){
            mediaFragment.updateAdapter(event.house);
            configureAndShowDetailsFragment();
            mapFragment.upDateMap(event.house);
            locationFragment.upDateLocation(event.house);
            descriptionFragment.upDateDescription(event.house);
        }else {
            Intent intent = new Intent(getContext(), DetailsActivity.class);
            startActivity(intent);
        }
    }

    protected void configureAndShowStaticMap(){
        mapFragment = (StaticMapFragment)  getActivity().getSupportFragmentManager().findFragmentById(R.id.static_map_fragment);
        if (mapFragment == null && getActivity().findViewById(R.id.fragment_container_map) != null){
            mapFragment = new StaticMapFragment();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_map, mapFragment).commit();
        }
    }


    protected void configureAndShowMediaFragment() {
        mediaFragment = (MediaFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_media);
        if (mediaFragment == null && getActivity().findViewById(R.id.fragment_container_media) != null){
            mediaFragment = new MediaFragment();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_media, mediaFragment).commit();
        }
    }


    protected void configureAndShowDetailsFragment() {
        infoFragment = (InfoFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_info);
        if (infoFragment == null && getActivity().findViewById(R.id.fragment_container_details) != null){
            infoFragment = new InfoFragment();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_details, infoFragment).commit();
        }
    }

    protected void configureAndShowLocationFragment() {
        locationFragment = (LocationFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.location_fragment);
        if (locationFragment == null && getActivity().findViewById(R.id.fragment_container_location) != null){
            locationFragment = new LocationFragment();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_location, locationFragment).commit();
        }
    }

    protected void configureAndShowDescriptionFragment() {
        descriptionFragment = (DescriptionFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.description_fragment);
        if (descriptionFragment == null && getActivity().findViewById(R.id.fragment_container_description) != null){
            descriptionFragment = new DescriptionFragment();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_description, descriptionFragment).commit();
        }
    }

}


