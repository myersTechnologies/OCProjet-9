package com.openclassrooms.realestatemanager.ui.fragments.second;


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
import com.openclassrooms.realestatemanager.model.House;
import com.openclassrooms.realestatemanager.service.RealEstateManagerAPIService;
import com.openclassrooms.realestatemanager.ui.activities.details.DetailsActivity;
import com.openclassrooms.realestatemanager.ui.adapters.second.ListFragmentAdapter;
import com.openclassrooms.realestatemanager.utils.SearchHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

public class ListFragment extends Fragment {


    private RecyclerView housesList;
    private ListFragmentAdapter adapter;
    private LinearLayoutManager layoutManager;
    private List<House> houses;
    private RealEstateManagerAPIService service;
    private SaveToDatabase database = SaveToDatabase.getInstance(getActivity());

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
        service = DI.getService();

        //check if search model is null if it is just load as default else list searched houses
        if (SearchHelper.getHousesList() == null) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    houses = database.houseDao().getHouses();
                    if (houses != null) {
                        adapter = new ListFragmentAdapter(houses, getActivity());
                        initList();
                    }
                }
            }, 2000);
        } else {
            adapter = new ListFragmentAdapter(SearchHelper.getHouses(), getActivity());
            Toast.makeText(getActivity(), String.valueOf(SearchHelper.getHouses().size()), Toast.LENGTH_SHORT).show();
            initList();
        }

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(housesList.getContext(),
                layoutManager.getOrientation());
        housesList.addItemDecoration(dividerItemDecoration);

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
        Intent intent = new Intent(getContext(), DetailsActivity.class);
        startActivity(intent);
    }
}


