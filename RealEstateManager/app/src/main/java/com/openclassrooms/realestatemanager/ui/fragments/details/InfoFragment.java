package com.openclassrooms.realestatemanager.ui.fragments.details;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.openclassrooms.realestatemanager.DI.DI;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.firebase.FirebaseHelper;
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
    private FirebaseHelper helper = DI.getFirebaseDatabase();

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
        service.setUsers(helper.getUsersFromFireBase());
        infoList = view.findViewById(R.id.info_list);
        layoutManager = new LinearLayoutManager(getContext());
        adapter = new InfoFragmentAdapter(service.getHouse());

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                infoList.setLayoutManager(layoutManager);
                infoList.setAdapter(adapter);
            }
        }, 1000);


        return view;
    }

}
