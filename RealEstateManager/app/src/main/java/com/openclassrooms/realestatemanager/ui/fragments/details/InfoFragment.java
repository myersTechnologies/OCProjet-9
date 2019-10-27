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
import com.openclassrooms.realestatemanager.service.RealEstateManagerAPIService;
import com.openclassrooms.realestatemanager.ui.adapters.details.InfoFragmentAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFragment extends Fragment {

    private static InfoFragment infoFragment;
    private RealEstateManagerAPIService service;
    private RecyclerView infoList;
    private LinearLayoutManager layoutManager;
    private InfoFragmentAdapter adapter;

    public static InfoFragment newInstance(){
        if (infoFragment == null){
            infoFragment = new InfoFragment();
        }
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
        adapter = new InfoFragmentAdapter(service.getHouse());
        infoList.setLayoutManager(layoutManager);
        infoList.setAdapter(adapter);


        return view;
    }

}
