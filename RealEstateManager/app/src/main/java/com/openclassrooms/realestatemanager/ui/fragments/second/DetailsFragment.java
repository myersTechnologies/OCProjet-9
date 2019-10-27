package com.openclassrooms.realestatemanager.ui.fragments.second;


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
import com.openclassrooms.realestatemanager.service.RealEstateManagerAPIService;
import com.openclassrooms.realestatemanager.ui.adapters.second.DetailsFragmentAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {

    private RecyclerView detailsList;
    private DetailsFragmentAdapter adapter;
    private LinearLayoutManager layoutManager;
    private RealEstateManagerAPIService service;
    private static DetailsFragment detailsFragment;

    public static DetailsFragment newInstance(){
        detailsFragment = new DetailsFragment();
        return detailsFragment;
    }

    public DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        detailsList = view.findViewById(R.id.details_list);
        layoutManager = new LinearLayoutManager(view.getContext());
        service = DI.getService();
        adapter = new DetailsFragmentAdapter(service.getHouse());
        initList();

        return view;
    }

    private void initList(){
        detailsList.setLayoutManager(layoutManager);
        detailsList.setAdapter(adapter);
    }

}
