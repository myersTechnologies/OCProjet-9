package com.openclassrooms.realestatemanager.ui.fragments.second;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;
import com.openclassrooms.realestatemanager.DI.DI;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.db.SaveToDatabase;
import com.openclassrooms.realestatemanager.events.DetailsEvent;
import com.openclassrooms.realestatemanager.firebase.FirebaseHelper;
import com.openclassrooms.realestatemanager.model.House;
import com.openclassrooms.realestatemanager.service.RealEstateManagerAPIService;
import com.openclassrooms.realestatemanager.ui.activities.details.DetailsActivity;
import com.openclassrooms.realestatemanager.ui.activities.search.SearchActivity;
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

import java.util.List;

public class ListFragment extends Fragment {


    private RecyclerView housesList;
    private static ListFragment listFragment;
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
    private ProgressDialog  dialog;
    private DatabaseUtil databaseUtil;
    private View view;
    private PullRefreshLayout layout;

    public ListFragment() {
        // Required empty public constructor
    }

    public static ListFragment getInstance(){
        if (listFragment == null){
            listFragment = new ListFragment();
        }
        return listFragment;
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
        view = inflater.inflate(R.layout.fragment_list, container, false);
        housesList = view.findViewById(R.id.houses_list_second_f);
        database = SaveToDatabase.getInstance(getActivity());
        service = DI.getService();
        layout = view.findViewById(R.id.refresh_layout);
        setRefreshLayout();

        this.configureAndShowMediaFragment();

        //check if search model is null if it is just load as default else list searched houses
        if (SearchHelper.getHousesList() == null) {
            updateToNull();
            service.setHouse(null);
            setList();
        } else {
            updateToNull();
            layoutManager = new LinearLayoutManager(view.getContext());
            adapter = new ListFragmentAdapter(SearchHelper.getHouses(), getActivity());
            initList();
        }

        setHasOptionsMenu(true);
        return view;
    }

    private void updateToNull(){
        if (mediaFragment!= null && mediaFragment.isVisible()) {
            if (service.getHouse() != null) {
                mediaFragment.updateAdapter(null);
                mapFragment.upDateMap(null);
                locationFragment.upDateLocation(null);
                descriptionFragment.upDateDescription(null);
                infoFragment.updateAdapter(null);
            }
        }
    }

    private void setRefreshLayout(){
        layout.setRefreshStyle(PullRefreshLayout.STYLE_CIRCLES);
        layout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                layout.setRefreshing(true);
                setList();
                updateToNull();
                service.setHouse(null);
                if (SearchHelper.getHousesList() != null) {
                    SearchHelper.setNull();
                }
            }
        });
    }

    private void setList(){

        houses = database.houseDao().getHouses();
        if (DI.getFirebaseDatabase().getHouses() == null) {
            dialog = new ProgressDialog(getActivity());
            checkFirebase(dialog);
        } else {
            checkFirebase(null);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.search:
                updateToNull();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void checkFirebase(ProgressDialog dialog){
        if (dialog != null) {
            dialog.setCanceledOnTouchOutside(false);
            dialog.setTitle("Loading data");
            dialog.setMessage("Please wait...");
            dialog.show();
        }

        //Loads all list from firebase to sql database
        databaseUtil = new DatabaseUtil(DI.getFirebaseDatabase(), getActivity(), adapter, housesList, dialog, layout);
        databaseUtil.execute();
    }


    private void initList() {
        housesList.setLayoutManager(layoutManager);
        housesList.setAdapter(adapter);
        updateToNull();
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
        if (databaseUtil != null) {
            if (databaseUtil.getStatus() == AsyncTask.Status.RUNNING || databaseUtil.getStatus() == AsyncTask.Status.PENDING) {
                databaseUtil.cancel(true);
            }
        }
        if (dialog != null && dialog.isShowing()){
            dialog.dismiss();
        }
    }


    @Subscribe
    public void changeFragmentOnClick(final DetailsEvent event) {
        service.setHouse(event.house);
        if (mapFragment == null && getActivity().findViewById(R.id.fragment_container_media) != null){
            this.configureAndShowDetailsFragment();
            this.configureAndShowStaticMap();
            this.configureAndShowLocationFragment();
            this.configureAndShowDescriptionFragment();
        }
        if (mediaFragment!= null && mediaFragment.isVisible()){
            mediaFragment.updateAdapter(event.house);
            locationFragment.upDateLocation(event.house);
            descriptionFragment.upDateDescription(event.house);
            infoFragment.updateAdapter(event.house);
            configureAndShowDetailsFragment();
            configureAndShowStaticMap();
        } else {
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


